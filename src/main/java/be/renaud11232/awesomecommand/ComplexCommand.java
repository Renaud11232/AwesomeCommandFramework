package be.renaud11232.awesomecommand;

import be.renaud11232.awesomecommand.annotation.*;
import org.bukkit.command.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class ComplexCommand extends Command {

    private final Class<?> commandClass;
    private final AwesomeCommand awesomeCommand;

    private final Map<String, ComplexCommand> subCommands;

    public ComplexCommand(Class<?> commandClass) {
        this(commandClass, Util.getCommandAnnotation(commandClass));
    }

    private ComplexCommand(Class<?> commandClass, AwesomeCommand awesomeCommand) {
        super(
                awesomeCommand.name(),
                awesomeCommand.description(),
                awesomeCommand.usage().isEmpty() ? "/<command>" : awesomeCommand.usage(),
                Arrays.asList(awesomeCommand.aliases())
        );
        if (!awesomeCommand.permission().isEmpty()) {
            setPermission(awesomeCommand.permission());
        }
        if (!awesomeCommand.permissionMessage().isEmpty()) {
            setPermissionMessage(awesomeCommand.permissionMessage());
        }
        this.commandClass = commandClass;
        this.awesomeCommand = awesomeCommand;
        this.subCommands = new HashMap<>();
        initSubCommands();
    }

    private void initSubCommands() {
        for (Class<?> subCommandClass : awesomeCommand.subCommands()) {
            ComplexCommand subCommand = new ComplexCommand(subCommandClass);
            subCommands.put(subCommand.getName(), subCommand);
            subCommand.getAliases()
                    .stream()
                    .filter(alias -> !subCommands.containsKey(alias))
                    .forEach(alias -> subCommands.put(alias, subCommand));
        }
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (testPermission(sender)) {
            if (args.length == 0 || !subCommands.containsKey(args[0])) {
                AwesomeCommandExecutor commandExecutor = getCommandExecutorInstance(sender, alias, args);
                if ((commandExecutor == null || !commandExecutor.execute()) && !awesomeCommand.usage().isEmpty()) {
                    Arrays.stream(awesomeCommand.usage().replace("<command>", alias).split("\r?\n"))
                            .forEach(sender::sendMessage);
                }
            } else {
                return subCommands.get(args[0]).execute(sender, alias + " " + args[0], Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length > 0 && testPermissionSilent(sender)) {
            if (subCommands.containsKey(args[0])) {
                return subCommands.get(args[0]).tabComplete(sender, alias + " " + args[0], Arrays.copyOfRange(args, 1, args.length));
            } else {
                Set<String> completionSet = new HashSet<>();
                if (args.length == 1) {
                    subCommands.keySet()
                            .stream()
                            .filter(subCommand -> subCommand.startsWith(args[0]))
                            .forEach(completionSet::add);
                }
                AwesomeTabCompleter tabCompleter = getTabCompleterInstance(sender, alias, args);
                if (tabCompleter != null) {
                    List<String> otherCompletions = tabCompleter.tabComplete();
                    if (otherCompletions == null) {
                        completionSet.addAll(super.tabComplete(sender, alias, args));
                    } else {
                        completionSet.addAll(otherCompletions);
                    }
                } else {
                    completionSet.addAll(super.tabComplete(sender, alias, args));
                }
                List<String> completionList = new LinkedList<>(completionSet);
                completionList.sort(String.CASE_INSENSITIVE_ORDER);
                return completionList;
            }
        } else {
            return Collections.emptyList();
        }
    }

    private AwesomeCommandExecutor getCommandExecutorInstance(CommandSender sender, String alias, String[] args) {
        if (AwesomeCommandExecutor.class.isAssignableFrom(commandClass)) {
            try {
                Constructor<?> constructor = commandClass.getConstructor();
                AwesomeCommandExecutor commandExecutor = (AwesomeCommandExecutor) constructor.newInstance();
                populateInstance(commandExecutor, sender, alias, args);
                return commandExecutor;
            } catch (Throwable t) {
                throw new CommandException("Unable to create AwesomeCommandExecutor instance :", t);
            }
        }
        return null;
    }

    private AwesomeTabCompleter getTabCompleterInstance(CommandSender sender, String alias, String[] args) {
        if (AwesomeTabCompleter.class.isAssignableFrom(commandClass)) {
            try {
                Constructor<?> constructor = commandClass.getConstructor();
                AwesomeTabCompleter tabCompleter = (AwesomeTabCompleter) constructor.newInstance();
                populateInstance(tabCompleter, sender, alias, args);
                return tabCompleter;
            } catch (Throwable t) {
                throw new CommandException("Unable to create AwesomeTabCompleter instance :", t);
            }
        }
        return null;
    }

    private <T> void populateInstance(T instance, CommandSender sender, String alias, String[] args) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            try {
                if (field.getAnnotation(CommandSenderParameter.class) != null) {
                    field.setAccessible(true);
                    field.set(instance, sender);
                } else if (field.getAnnotation(AliasParameter.class) != null) {
                    field.setAccessible(true);
                    field.set(instance, alias);
                } else if (field.getAnnotation(Arguments.class) != null) {
                    field.setAccessible(true);
                    field.set(instance, args);
                } else if (field.getAnnotation(CommandParameter.class) != null) {
                    field.setAccessible(true);
                    field.set(instance, this);
                }
            } catch (IllegalAccessException e) {
                throw new CommandException("Unable to populate field " + field.getName() + " for class " + instance.getClass().getName(), e);
            }
        }
    }
}

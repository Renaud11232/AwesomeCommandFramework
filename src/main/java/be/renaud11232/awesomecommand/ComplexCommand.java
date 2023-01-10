package be.renaud11232.awesomecommand;

import be.renaud11232.awesomecommand.annotation.command.AwesomeCommand;
import be.renaud11232.awesomecommand.parser.CommandParserException;
import be.renaud11232.awesomecommand.parser.InvalidCommandUsageException;
import be.renaud11232.awesomecommand.util.AnnotationUtil;
import be.renaud11232.awesomecommand.parser.CommandParser;
import org.bukkit.command.*;

import java.util.*;

/**
 * The {@link ComplexCommand} class defines a command that is based on a command specification class.
 */
public class ComplexCommand extends Command {

    private final Class<?> commandClass;
    private final AwesomeCommand awesomeCommand;
    private final Map<String, ComplexCommand> subCommands;

    /**
     * Constructs a new {@link ComplexCommand}
     *
     * @param commandClass the command specification class. This class must be annotated with {@link AwesomeCommand}
     * @throws NullPointerException if the given command {@link Class} does not have the {@link AwesomeCommand} annotation
     */
    public ComplexCommand(Class<?> commandClass) throws NullPointerException {
        this(commandClass, AnnotationUtil.getCommandAnnotation(commandClass));
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

    /**
     * {@inheritDoc}
     *
     * @throws CommandParserException if the {@link Class} provided while constructing this {@link ComplexCommand} is improperly configured
     * @see Command#execute(CommandSender, String, String[])
     */
    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) throws CommandParserException {
        if (testPermission(sender)) {
            if (args.length == 0 || !subCommands.containsKey(args[0])) {
                AwesomeCommandExecutor commandExecutor = null;
                try {
                    commandExecutor = new CommandParser(commandClass).forCommand(this).labelled(alias).sentFrom(sender).with(args).getCommandExecutor();
                } catch (InvalidCommandUsageException ignored) {
                }
                if ((commandExecutor == null || !commandExecutor.execute()) && !awesomeCommand.usage().isEmpty()) {
                    Arrays.stream(awesomeCommand.usage().replace("<command>", alias).split("\r?\n"))
                            .forEach(sender::sendMessage);
                }
            } else {
                subCommands.get(args[0]).execute(sender, alias + " " + args[0], Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @throws CommandParserException if the {@link Class} provided while constructing this {@link ComplexCommand} is improperly configured
     * @see Command#tabComplete(CommandSender, String, String[])
     */
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws CommandParserException {
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
                AwesomeTabCompleter tabCompleter = null;
                try {
                    tabCompleter = new CommandParser(commandClass).forCommand(this).labelled(alias).sentFrom(sender).with(args).getTabCompleter();
                } catch (InvalidCommandUsageException ignored) {
                }
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
}

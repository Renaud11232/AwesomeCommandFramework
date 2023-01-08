package be.renaud11232.awesomecommand.parser;

import be.renaud11232.awesomecommand.AwesomeCommandExecutor;
import be.renaud11232.awesomecommand.AwesomeTabCompleter;
import be.renaud11232.awesomecommand.annotation.args.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link CommandParser} class allows to transform a command specification to {@link AwesomeTabCompleter} and {@link AwesomeCommandExecutor}
 * which can then be executed
 */
public class CommandParser {

    private CommandSender commandSender;
    private String alias;
    private String[] arguments;
    private final Class<?> commandSpecification;
    private Command command;

    /**
     * Constructs a new {@link CommandParser} for a given command specification
     *
     * @param commandSpec the command specification class that will be instantiated and populated.
     */
    public CommandParser(Class<?> commandSpec) {
        this.commandSpecification = commandSpec;
    }

    /**
     * Sets the reference to the {@link Command}
     *
     * @param command the linked {@link Command}
     * @return this {@link CommandParser} for method chaining
     */
    public CommandParser forCommand(Command command) {
        this.command = command;
        return this;
    }

    /**
     * Sets the reference to the {@link CommandSender}
     *
     * @param commandSender the linked {@link CommandSender}
     * @return this {@link CommandParser} for method chaining
     */
    public CommandParser sentFrom(CommandSender commandSender) {
        this.commandSender = commandSender;
        return this;
    }

    /**
     * Sets the label of the parsed command
     *
     * @param alias the label
     * @return this {@link CommandParser} for method chaining
     */
    public CommandParser labelled(String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * Sets the arguments of the parsed command
     *
     * @param arguments the arguments
     * @return this {@link CommandParser} for method chaining
     */
    public CommandParser with(String[] arguments) {
        this.arguments = arguments;
        return this;
    }

    /**
     * Gets a {@link AwesomeCommandExecutor} instance that was parsed
     *
     * @return a parsed {@link AwesomeCommandExecutor} instance, with all annotated fields properly set. If the given command specification does not implement the {@link AwesomeCommandExecutor} interface, a null is returned.
     */
    public AwesomeCommandExecutor getCommandExecutor() {
        if (!AwesomeCommandExecutor.class.isAssignableFrom(commandSpecification)) {
            return null;
        }
        try {
            Constructor<?> constructor = commandSpecification.getConstructor();
            AwesomeCommandExecutor commandExecutor = (AwesomeCommandExecutor) constructor.newInstance();
            populateInstance(commandExecutor);
            return commandExecutor;
        } catch (Throwable t) {
            throw new CommandParserException("Unable to create AwesomeCommandExecutor instance :", t);
        }
    }

    /**
     * Gets a {@link AwesomeTabCompleter} instance that was parsed
     *
     * @return a parsed {@link AwesomeTabCompleter} instance, with all annotated fields properly set. If the given command specification does not implement the {@link AwesomeTabCompleter} interface, a null is returned.
     */
    public AwesomeTabCompleter getTabCompleter() {
        if (!AwesomeTabCompleter.class.isAssignableFrom(commandSpecification)) {
            return null;
        }
        try {
            Constructor<?> constructor = commandSpecification.getConstructor();
            AwesomeTabCompleter tabCompleter = (AwesomeTabCompleter) constructor.newInstance();
            populateInstance(tabCompleter);
            return tabCompleter;
        } catch (Throwable t) {
            throw new CommandParserException("Unable to create AwesomeTabCompleter instance :", t);
        }
    }

    private <T> void populateInstance(T instance) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            ensureOnlyOneFieldAnnotationOf(field, CommandSenderParameter.class, FullAliasParameter.class, Arguments.class, CommandParameter.class, AliasParameter.class);
            if (field.getAnnotation(CommandSenderParameter.class) != null) {
                setField(instance, field, commandSender);
            } else if (field.getAnnotation(AliasParameter.class) != null) {
                String[] aliases = alias.split(" ");
                setField(instance, field, aliases[aliases.length - 1]);
            } else if (field.getAnnotation(FullAliasParameter.class) != null) {
                setField(instance, field, alias.split(" "));
            } else if (field.getAnnotation(Arguments.class) != null) {
                setField(instance, field, arguments);
            } else if (field.getAnnotation(CommandParameter.class) != null) {
                setField(instance, field, command);
            }
        }
    }

    private void setField(Object instance, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new CommandParserException("Unable to populate field " + field.getName() + " for class " + instance.getClass().getName(), e);
        }
    }

    @SafeVarargs
    private final void ensureOnlyOneFieldAnnotationOf(Field field, Class<? extends Annotation>... annotations) {
        Set<Class<? extends Annotation>> annotationSet = new HashSet<>();
        Collections.addAll(annotationSet, annotations);
        Class<? extends Annotation> firstFoundAnnotation = null;
        for (Class<? extends Annotation> annotation : annotationSet) {
            if (field.isAnnotationPresent(annotation)) {
                if (firstFoundAnnotation != null) {
                    throw new CommandParserException("Incompatible annotations " + firstFoundAnnotation.getName() + " and " + annotation.getName() + " were found on field " + field.getName() + " of class " + field.getDeclaringClass().getName());
                } else {
                    firstFoundAnnotation = annotation;
                }
            }
        }
    }

}

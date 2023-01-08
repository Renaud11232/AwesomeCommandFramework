package be.renaud11232.awesomecommand.parser;

import be.renaud11232.awesomecommand.AwesomeCommandExecutor;
import be.renaud11232.awesomecommand.AwesomeTabCompleter;
import be.renaud11232.awesomecommand.annotation.args.*;
import be.renaud11232.awesomecommand.adapter.ArgumentValueAdapter;
import be.renaud11232.awesomecommand.adapter.UnsupportedTypeAdapterException;
import be.renaud11232.awesomecommand.util.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The {@link CommandParser} class allows to transform a command specification to {@link AwesomeTabCompleter} and {@link AwesomeCommandExecutor}
 * which can then be executed
 */
public class CommandParser {

    private CommandSender commandSender;
    private String[] aliases;
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
        this.aliases = alias.split(" ");
        return this;
    }

    /**
     * Sets the arguments of the parsed command
     *
     * @param arguments the arguments
     * @return this {@link CommandParser} for method chaining
     */
    public CommandParser with(String... arguments) {
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
            throw new CommandParserException("Unable to create AwesomeCommandExecutor instance", t);
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
        List<Field> positionalArguments = new ArrayList<>();
        for (Field field : instance.getClass().getDeclaredFields()) {
            ensureOnlyOneFieldAnnotationOf(field, CommandSenderParameter.class, FullAliasParameter.class, Arguments.class, CommandParameter.class, AliasParameter.class, PositionalArgument.class);
            if (field.isAnnotationPresent(CommandSenderParameter.class)) {
                setField(instance, field, commandSender);
            } else if (field.isAnnotationPresent(AliasParameter.class)) {
                setField(instance, field, aliases[aliases.length - 1]);
            } else if (field.isAnnotationPresent(FullAliasParameter.class)) {
                setFullAliasParameterField(instance, field);
            } else if (field.isAnnotationPresent(Arguments.class)) {
                setArgumentsField(instance, field);
            } else if (field.isAnnotationPresent(PositionalArgument.class)) {
                positionalArguments.add(field);
            } else if (field.isAnnotationPresent(CommandParameter.class)) {
                setField(instance, field, command);
            }
            setPositionalArguments(positionalArguments, instance);
        }
    }

    private <T> void setPositionalArguments(List<Field> positionalArguments, T instance) {
        positionalArguments.sort(Comparator.comparingInt(field -> field.getAnnotation(PositionalArgument.class).position()));
        ensureOptionalPositionalArgumentsPlacement(positionalArguments);
        positionalArguments.forEach(positionalArgument -> {
            PositionalArgument annotation = positionalArgument.getAnnotation(PositionalArgument.class);
            try {
                setField(positionalArgument, instance, arguments[annotation.position()], annotation.adapter());
            } catch (ArrayIndexOutOfBoundsException e) {
                if (annotation.required()) {
                    throw new InvalidCommandUsageException("Unable to populate field " + positionalArgument.getName() + " of class " + instance.getClass().getName() + ". Not enough arguments were provided", e);
                } else if (!Constants.NO_VALUE.equals(annotation.defaultValue())) {
                    setField(positionalArgument, instance, annotation.defaultValue(), annotation.adapter());
                }
            }
        });
    }

    private <T> void setField(Field field, T instance, String value, Class<? extends ArgumentValueAdapter> adapterType) {
        if (field.getType().isAssignableFrom(String.class)) {
            setField(instance, field, value);
        } else {
            ArgumentValueAdapter adapter;
            try {
                Constructor<? extends ArgumentValueAdapter> constructor = adapterType.getConstructor();
                constructor.setAccessible(true);
                adapter = constructor.newInstance();
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new CommandParserException("Unable to create instance of " + adapterType.getName(), e);
            }
            try {
                setField(instance, field, adapter.apply(value, field));
            } catch (UnsupportedTypeAdapterException e) {
                throw new CommandParserException("Unable to populate field " + field.getName() + " of class " + instance.getClass().getName(), e);
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandUsageException("Unable to populate field " + field.getName() + " of class " + instance.getClass().getName() + ". Argument value was invalid", e);
            }
        }
    }

    private void ensureOptionalPositionalArgumentsPlacement(List<Field> positionalArguments) {
        Field lastOptionalArgument = null;
        Field previousArgument = null;
        Integer previousArgumentPosition = null;
        for (Field positionalArgument : positionalArguments) {
            PositionalArgument annotation = positionalArgument.getAnnotation(PositionalArgument.class);
            if (previousArgumentPosition != null && previousArgumentPosition == annotation.position()) {
                throw new CommandParserException("Incorrect positional argument position : " + previousArgument.getName() + " and " + positionalArgument.getName() + " are both at position " + previousArgumentPosition + " in class " + positionalArgument.getDeclaringClass().getName());
            }
            if (annotation.required()) {
                if (lastOptionalArgument != null) {
                    throw new CommandParserException("Incorrect positional argument order : Required positional argument " + positionalArgument.getName() + " found after optional positional argument " + lastOptionalArgument.getName() + " in class " + positionalArgument.getDeclaringClass().getName());
                }
            } else {
                lastOptionalArgument = positionalArgument;
            }
            previousArgumentPosition = annotation.position();
            previousArgument = positionalArgument;
        }
    }

    private <T> void setArgumentsField(T instance, Field field) {
        if (field.getType().isArray()) {
            setField(instance, field, arguments);
        } else {
            setField(instance, field, Arrays.asList(arguments));
        }
    }

    private <T> void setFullAliasParameterField(T instance, Field field) {
        if (field.getType().isArray()) {
            setField(instance, field, aliases);
        } else {
            setField(instance, field, Arrays.asList(aliases));
        }
    }

    private void setField(Object instance, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new CommandParserException("Unable to populate field " + field.getName() + " of class " + instance.getClass().getName(), e);
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

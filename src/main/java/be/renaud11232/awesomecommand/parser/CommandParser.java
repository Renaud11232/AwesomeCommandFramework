package be.renaud11232.awesomecommand.parser;

import be.renaud11232.awesomecommand.AwesomeCommandExecutor;
import be.renaud11232.awesomecommand.AwesomeTabCompleter;
import be.renaud11232.awesomecommand.annotation.AliasParameter;
import be.renaud11232.awesomecommand.annotation.Arguments;
import be.renaud11232.awesomecommand.annotation.CommandParameter;
import be.renaud11232.awesomecommand.annotation.CommandSenderParameter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandParser {

    private CommandSender commandSender;
    private String alias;
    private String[] arguments;
    private final Class<?> commandSpecification;
    private final Command command;

    public CommandParser(Class<?> commandSpec, Command command) {
        this.commandSpecification = commandSpec;
        this.command = command;
    }

    public CommandParser sentFrom(CommandSender commandSender) {
        this.commandSender = commandSender;
        return this;
    }

    public CommandParser labelled(String alias) {
        this.alias = alias;
        return this;
    }

    public CommandParser with(String[] arguments) {
        this.arguments = arguments;
        return this;
    }

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
            if (field.getAnnotation(CommandSenderParameter.class) != null) {
                setField(instance, field, commandSender);
            } else if (field.getAnnotation(AliasParameter.class) != null) {
                setField(instance, field, alias);
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

}

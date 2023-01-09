package be.renaud11232.awesomecommand.util;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@link CommandTokenizer} class is a utility class used to split the command arguments similarly to a shell.
 * This means it will interpret backslashes, and quoted strings.
 */
public class CommandTokenizer {

    private enum State {
        NO_TOKEN,
        NORMAL_TOKEN,
        SINGLE_QUOTE,
        DOUBLE_QUOTE
    }

    private final String commandLine;

    /**
     * Constructs a new {@link CommandTokenizer} using the Bukkit command arguments.
     *
     * @param bukkitArgs the arguments already split by Bukkit
     */
    public CommandTokenizer(String[] bukkitArgs) {
        this(String.join(" ", bukkitArgs));
    }

    /**
     * Constructs a new {@link CommandTokenizer} for a given command, provided as {@link String}
     *
     * @param commandLine the command line to tokenize
     */
    public CommandTokenizer(String commandLine) {
        this.commandLine = commandLine;
    }

    /**
     * Produces an array of {@link String} representing the different tokens (arguments) of the command line
     *
     * @return the split command tokens
     * @throws IllegalArgumentException if the provided command is improperly formatted
     */
    public String[] tokenize() throws IllegalArgumentException {
        List<String> result = new LinkedList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean escaped = false;
        State state = State.NO_TOKEN;
        for (int i = 0; i < commandLine.length(); i++) {
            char c = commandLine.charAt(i);
            if (escaped) {
                escaped = false;
                currentToken.append(c);
            } else {
                switch (state) {
                    case SINGLE_QUOTE:
                        if (c == '\'') {
                            state = State.NORMAL_TOKEN;
                        } else {
                            currentToken.append(c);
                        }
                        break;
                    case DOUBLE_QUOTE:
                        if (c == '"') {
                            state = State.NORMAL_TOKEN;
                        } else if (c == '\\') {
                            i++;
                            if (i >= commandLine.length()) {
                                throw new IllegalArgumentException("Unexpected end of input : Missing escaped character");
                            }
                            char next = commandLine.charAt(i);
                            if (next == '"' || next == '\\') {
                                currentToken.append(next);
                            } else {
                                currentToken.append(c).append(next);
                            }
                        } else {
                            currentToken.append(c);
                        }
                        break;
                    case NO_TOKEN:
                    case NORMAL_TOKEN:
                        switch (c) {
                            case '\\':
                                escaped = true;
                                state = State.NORMAL_TOKEN;
                                break;
                            case '\'':
                                state = State.SINGLE_QUOTE;
                                break;
                            case '"':
                                state = State.DOUBLE_QUOTE;
                                break;
                            default:
                                if (!Character.isWhitespace(c)) {
                                    currentToken.append(c);
                                    state = State.NORMAL_TOKEN;
                                } else if (state == State.NORMAL_TOKEN) {
                                    result.add(currentToken.toString());
                                    currentToken.setLength(0);
                                    state = State.NO_TOKEN;
                                }
                        }
                        break;
                }
            }
        }
        if (state == State.SINGLE_QUOTE) {
            throw new IllegalArgumentException("Unexpected end of input : Unclosed single quotes");
        }
        if (state == State.DOUBLE_QUOTE) {
            throw new IllegalArgumentException("Unexpected end of input : Unclosed double quotes");
        }
        if (escaped) {
            currentToken.append('\\');
            result.add(currentToken.toString());
        } else if (state != State.NO_TOKEN) {
            result.add(currentToken.toString());
        }
        return result.toArray(new String[0]);
    }
}

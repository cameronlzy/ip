package seedu.rex.utils;

import seedu.rex.ui.Rex;

/**
 * The {@code Parser} class handles parsing of user input for the Rex chatbot.
 * <p>
 * It provides helper methods to extract command words and arguments
 * from raw user input, as well as specific parsers for complex commands
 * such as {@code deadline} and {@code event}.
 * <p>
 * This class centralizes input parsing logic to keep {@link Rex}
 * focused on command execution.
 */
public class Parser {

    /**
     * Extracts the command word from the user input.
     * <p>
     * The command word is the first token (before the first space).
     * It is converted to lowercase for case-insensitive matching.
     *
     * @param input the full user input string
     * @return the command word in lowercase, or an empty string if input is empty
     */
    public static String getCommand(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        return input.split(" ")[0].toLowerCase();
    }

    /**
     * Extracts the arguments from the user input.
     * <p>
     * The arguments are everything after the first space. If no space is
     * present, this method returns an empty string.
     *
     * @param input the full user input string
     * @return the argument string, or an empty string if none exist
     */
    public static String getArguments(String input) {
        if (input == null) {
            return "";
        }
        int firstSpace = input.indexOf(" ");
        return firstSpace == -1 ? "" : input.substring(firstSpace + 1).trim();
    }

    /**
     * Parses the arguments for a {@code deadline} command.
     * <p>
     * Expected format:
     * <pre>
     *   description /by yyyy-MM-dd[ HHmm]
     * </pre>
     *
     * @param args the argument string after the "deadline" keyword
     * @return a two-element array: [description, deadline string]
     * @throws IllegalArgumentException if description or deadline is missing
     */
    public static String[] parseDeadline(String args) {
        int sep = args.indexOf("/by");
        if (sep == -1) {
            throw new IllegalArgumentException("Usage: deadline <desc> /by <yyyy-MM-dd[ HHmm]>");
        }
        String desc = args.substring(0, sep).trim();
        String by = args.substring(sep + 3).trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new IllegalArgumentException("Deadline requires description and date/time.");
        }
        return new String[]{desc, by};
    }

    /**
     * Parses the arguments for an {@code event} command.
     * <p>
     * Expected format:
     * <pre>
     *   description /from yyyy-MM-dd[ HHmm] /to yyyy-MM-dd[ HHmm]
     * </pre>
     *
     * @param args the argument string after the "event" keyword
     * @return a three-element array: [description, from, to]
     * @throws IllegalArgumentException if description, from, or to is missing
     */
    public static String[] parseEvent(String args) {
        int fromIdx = args.indexOf("/from");
        int toIdx = args.indexOf("/to");
        if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
            throw new IllegalArgumentException("Usage: event <desc> /from <start> /to <end>");
        }

        String desc = args.substring(0, fromIdx).trim();
        String from = args.substring(fromIdx + 5, toIdx).trim();
        String to = args.substring(toIdx + 3).trim();

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException("Event requires description, start, and end date/time.");
        }

        return new String[]{desc, from, to};
    }
}

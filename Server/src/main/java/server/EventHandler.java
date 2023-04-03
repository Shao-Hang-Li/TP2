package server;

/**
 * This functional interface defines the method to handle events in the server.
 * It has only one method, {@code handle}, which receives the command and its arguments as parameters.
 * The implementation of this method will define what action should be taken for a given command and its arguments.
 */
 @FunctionalInterface
 public interface EventHandler {
     /**
     * Handles the given command and its arguments.
     * @param cmd the command to be handled
     * @param arg the arguments for the command
     */
    void handle(String cmd, String arg);
 }
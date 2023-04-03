/**

 The ServerLauncher class is responsible for launching the server application.
 It contains a main method that initializes the server with the specified port number
 and starts it by calling the run method.
 */
package server;

public class ServerLauncher {
    /**
     * The port number that the server will be running on.
     */
    public final static int PORT = 1337;

    /**
     * The main method that launches the server application.
     * It initializes the server with the specified port number and starts it by calling the run method.
     *
     * @param args command line arguments, not used in this application
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package server;

import javafx.util.Pair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the server that listens to client requests, handles them, and sends back responses.
 * The server has two commands: INSCRIRE (register) and CHARGER (load courses).
 * When a client sends a command to the server, the server delegates the handling of the command to registered event handlers.
 * Each event handler implements the logic of how to handle a command.
 * The server runs on a specified port and listens to incoming connections from clients.
 */
public class Server {

    /**
     * The command for registering a new student to a course
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";

    /**
     * The command for loading the list of courses for a given session
     */
    public final static String LOAD_COMMAND = "CHARGER";

    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Constructs a new Server object that listens on a specified port.
     * @param port the port on which the server listens for incoming connections from clients
     * @throws IOException if an I/O error occurs while creating the ServerSocket object
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Adds a new event handler to the server.
     * When the server receives a command from a client, it delegates the handling of the command to all registered event handlers.
     * @param h the event handler to add
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Notifies all registered event handlers of a command received from a client.
     * @param cmd the command received from the client
     * @param arg the argument received with the command
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Starts the server and listens for incoming connections from clients.
     * For each connection, it reads a command from the input stream, delegates the handling of the command to registered event handlers,
     * sends back a response to the client, and disconnects from the client.
     * @throws IOException if an I/O error occurs while reading from or writing to the input/output stream
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public void run() throws IOException, ClassNotFoundException {
        while (true) {
            client = server.accept();
            System.out.println("Connection S: " + client);
            objectInputStream = new ObjectInputStream(client.getInputStream());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            listen();
            disconnect();
            System.out.println("Disconnected");
        }
    }

    /**
     * Listens for a command from the client, delegates the handling of the command to registered event handlers,
     * and sends back a response to the client.
     * @throws IOException if an I/O error occurs while reading from or writing to the input/output stream
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Processes a command line input and returns a Pair of Strings representing the command and its arguments.
     * @param line the input command line
     * @return a Pair of Strings representing the command and its arguments
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Closes the object output stream, object input stream, and client socket.
     * @throws IOException if there is an error closing any of the resources
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Handles events based on the command and its arguments.
     * @param cmd the command string
     * @param arg the argument string
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration(arg);
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Handles the LOAD_COMMAND by reading the text file of courses and sending the list of courses to the client.
     * @param arg the argument string representing the department code of the courses to load
     */
    public void handleLoadCourses(String arg) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/server/data/cours.txt"))) {

            // Read the text file and transform courses into Course objects
            List<String> courses = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");
                if (fields.length == 3 && fields[2].equalsIgnoreCase(arg)) {
                    courses.add(fields[0] + " " + fields[1]  + " " + arg);
                }
            }
            // Write the list of courses to the object stream
            objectOutputStream.writeObject(courses);
        } catch (IOException e) {
            System.err.println("Error while reading the file or writing the object to the stream: " + e.getMessage());
        }
    }

    /**
     * Handles the REGISTER_COMMAND by writing the registration data to a text file and sending a confirmation message to the client.
     * @param args the argument string representing the registration data
     */
    public void handleRegistration(String args) {
        try {
            // Read the object from the input stream
            String[] data = args.split(" ");
            // Write the object to a text file
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/server/data/inscription.txt", true));
            writer.write(data[5] + "\t" + data[4] + "\t" + data[3] + "\t" + data[0] + "\t" + data[1] + "\t" + data[2]);
            writer.newLine();
            writer.close();

            // Send a confirmation message to the client
            objectOutputStream.writeObject("Congratulations! The inscription was a success for "+ data[0] +" in the class " + data[4]);
        } catch (IOException e) {
            try {
                objectOutputStream.writeObject("Error while reading the object, writing to file, or sending to the output stream.");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}


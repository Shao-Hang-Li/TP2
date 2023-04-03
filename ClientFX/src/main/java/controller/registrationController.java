package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static UI.Main.SERVER_ADDRESS;
import static UI.Main.SERVER_PORT;

/**
 * The registrationController class is responsible for handling the communication between the client and the server for
 * registration-related functionality. It establishes a socket connection with the server and sends commands and arguments
 * to retrieve information from the server.
 */

public class registrationController {

    /**
     * Sends a command and an argument to the server and receives a response from the server.
     * @param command The command to be sent to the server.
     * @param arg The argument to be sent along with the command.
     * @return A String response from the server.
     * @throws IOException if an I/O error occurs when creating the socket or sending/receiving data.
     * @throws ClassNotFoundException if the class of a serialized object received from the server cannot be found.
     */
    public String sendCommand(String command, String arg) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        String obj;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        // Send a load request to the server to get the list of courses for the selected session
        objectOutputStream.writeObject(command + " " + arg);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        obj = (String) objectInputStream.readObject();
        // Close the connection
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
        return obj;
    }
}

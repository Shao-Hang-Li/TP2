package controller;

import UI.Main;
import model.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The courseController class is responsible for controlling the client-side operations related to Course management.
 * It establishes a connection to the server, sends a command and arguments to the server, and receives the server's response
 * which contains a list of courses, then converts the received data into Course objects and returns them.
 */
public class courseController {

    /**
     * Sends a command and argument to the server to get the list of courses for a specific session,
     * then converts the received data into Course objects and returns them.
     * @param command The command to be executed on the server-side.
     * @param arg     The argument to be passed to the server-side method.
     * @return An ArrayList of Course objects returned by the server-side method.
     * @throws IOException            If an I/O error occurs when creating the socket or sending/receiving data.
     * @throws ClassNotFoundException If the class of a serialized object received from the server cannot be found.
     */
    public ArrayList<Course> sendCommand(String command, String arg) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(Main.SERVER_ADDRESS, Main.SERVER_PORT);
        List<String> obj;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        // Send a load request to the server to get the list of courses for the selected session
        objectOutputStream.writeObject(command + " " + arg);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        obj = (List<String>) objectInputStream.readObject();
        ArrayList<Course> courses = new ArrayList<>();
        for (String s: obj) {
            String[] data = s.split(" ");
            courses.add(new Course(data[1],data[0],data[2]));
        }
        // Close the connection
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
        return courses;
    }
}

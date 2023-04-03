import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {

    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 1337;
    private final static String REGISTER_COMMAND = "INSCRIRE";
    private final static String LOAD_COMMAND = "CHARGER";

    public void run() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        String firstName;
        String lastName;
        String email;
        String matricula;

        // Display a welcome message
        System.out.println("*** Welcome to the inscription portal of the class ***");

        // Prompt the user to choose a session
        System.out.println("Please choose which session you would like to consult in the list of class:");
        System.out.println("1. Fall");
        System.out.println("2. Winter");
        System.out.println("3. Summer");
        System.out.print("> Choice: ");
        choice = scanner.nextInt();
        String session = "";
        if(choice==1){
            session = "Automne";
        } else if (choice==2) {
            session = "Winter";
        } else if (choice==3) {
            session = "Summer";
        }
        List<String> courses = (List<String>) sendCommand(LOAD_COMMAND,session);

        // Display the list of courses
        System.out.println("The classes that are offered in the chosen semester are:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }

        // Prompt the user to choose a course
        System.out.print("> Choice: \n");
        System.out.println("1. Consult the courses offered for another session\n" +
                "2. Registration for a course");
        System.out.print("> Choice: ");
        choice = Integer.parseInt(scanner.next());
        while ((choice) == 1) {
            System.out.println("Please choose which session you would like to consult in the list of class:");
            System.out.println("1. Automne");
            System.out.println("2. Hiver");
            System.out.println("3. Ete");
            System.out.print("> Choice: ");
            choice = scanner.nextInt();
            session = "";
            if(choice==1){
                session = "Automne";
            } else if (choice==2) {
                session = "Hiver";
            } else if (choice==3) {
                session = "Ete";
            }
            courses = (List<String>) sendCommand(LOAD_COMMAND,session);

            // Display the list of courses
            System.out.println("The classes that are offered in the chosen semester are:");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ". " + courses.get(i));
            }

            // Prompt the user to choose a course
            System.out.print("> Choice: \n");
            System.out.println("1. Consult the courses offered for another session\n" +
                    "2. Registration for a course");
            System.out.print("> Choice: ");
            choice = Integer.parseInt(scanner.next());
        }
        // Prompt the user to enter registration information
        System.out.print("Please enter your first name: ");
        firstName = scanner.next();
        System.out.print("Please enter your last name: ");
        lastName = scanner.next();
        System.out.print("Please enter your email: ");
        email = scanner.next();
        System.out.print("Please enter your matricula: ");
        matricula = scanner.next();
        System.out.print("Please enter the code of the class: ");
        String classCode = scanner.next();

        // Send a registration request to the server with the registration information
        String response = (String) sendCommand(REGISTER_COMMAND , firstName + " " + lastName + " " + email + " " + matricula + " " + classCode + " " + session);
        System.out.println();
        // Display the response
        System.out.println(response);
    }

    public Object sendCommand(String command, String arg) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        Object obj;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        // Send a load request to the server to get the list of courses for the selected session
        objectOutputStream.writeObject(command + " " + arg);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        obj = objectInputStream.readObject();
        // Close the connection
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
        return obj;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.run();
    }
}

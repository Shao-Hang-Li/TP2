package UI;

import View.CourseView;
import View.RegistrationView;
import controller.courseController;
import controller.registrationController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The Main class is responsible for launching the client application.
 * It creates instances of the courseController and registrationController classes,
 * and uses them to create instances of the CourseView and RegistrationView classes.
 * It then adds these views to a BorderPane and displays the resulting scene in a Stage.
 */
public class Main extends Application {

    // Constants for server address and port
    public final static String SERVER_ADDRESS = "localhost";
    public final static int SERVER_PORT = 1337;

    /**
     * The main method launches the application.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * The start method sets up the user interface for the client application.
     * It creates instances of the courseController and registrationController classes,
     * and uses them to create instances of the CourseView and RegistrationView classes.
     * It then adds these views to a BorderPane and displays the resulting scene in a Stage.
     * @param primaryStage the primary stage of the application
     * @throws Exception if an error occurs while setting up the user interface
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create instances of controllers and views
        courseController courseController = new courseController();
        registrationController controller = new registrationController();
        CourseView courseView = new CourseView(courseController);
        RegistrationView registrationView = new RegistrationView(controller, courseView);

        // Add views to BorderPane
        BorderPane pane = new BorderPane();
        pane.setLeft(courseView);
        pane.setRight(registrationView);

        // Set up and show the stage
        primaryStage.setResizable(false);
        primaryStage.setTitle("Client");
        Scene scene = new Scene(pane);
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

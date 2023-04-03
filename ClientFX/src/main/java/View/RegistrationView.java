package View;

import controller.registrationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * This class represents the view for registering to a course.
 */
public class RegistrationView extends BorderPane {
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField matriculeField;
    private TextField classCode;
    private Button submitButton;

    private registrationController controller;

    private CourseView view;

    /**
     * Constructor for RegistrationView.
     *
     * @param controller the registration controller for handling registration requests
     * @param view the course view used to get the selected season
     */
    public RegistrationView(registrationController controller, CourseView view) {
        this.controller = controller;
        initializeUI();
        this.view = view;
    }

    /**
     * Initializes the UI elements for the registration form.
     */
    private void initializeUI() {
        // Set up UI elements
        Label titleLabel = new Label("Formulaire d'inscription");
        titleLabel.setStyle("-fx-font-size: 24px;");

        Label firstNameLabel = new Label("Prenom:");
        firstNameField = new TextField();

        Label lastNameLabel = new Label("Nom:");
        lastNameField = new TextField();

        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        Label matriculeLabel = new Label("Matricule:");
        matriculeField = new TextField();

        Label classCodeLabel = new Label("Class Code:");
        classCode = new TextField();

        GridPane formGrid = new GridPane();
        formGrid.addRow(0, firstNameLabel, firstNameField);
        formGrid.addRow(1, lastNameLabel, lastNameField);
        formGrid.addRow(2, emailLabel, emailField);
        formGrid.addRow(3, matriculeLabel, matriculeField);
        formGrid.addRow(4, classCodeLabel, classCode);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));

        submitButton = new Button("Envoyer");
        submitButton.setOnAction(event -> handleFormSubmit());

        VBox formBox = new VBox();
        formBox.getChildren().addAll(formGrid, submitButton);
        formBox.setSpacing(20);
        formBox.setAlignment(Pos.CENTER);

        Label successLabel = new Label("Inscription réussie!");
        successLabel.setStyle("-fx-font-size: 24px;");


        VBox successBox = new VBox();
        successBox.getChildren().addAll(successLabel);
        successBox.setSpacing(20);
        successBox.setAlignment(Pos.CENTER);

        // Set up layout
        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(titleLabel, formBox);
        mainBox.setSpacing(10);
        mainBox.setPadding(new Insets(20));

        this.setTop(mainBox);

    }

    /**
     * Handles form submission for the registration form.
     */
    private void handleFormSubmit() {
        String message;
        try {
            if (!(firstNameField.getText().equals("") || lastNameField.getText().equals("") || matriculeField.getText().equals("") || emailField.getText().equals("") || classCode.getText().equals(""))) {
                if (Integer.parseInt(matriculeField.getText())>99999 && Integer.parseInt(matriculeField.getText())<=999999) {
                    message = controller.sendCommand("INSCRIRE", firstNameField.getText() + " " + lastNameField.getText() + " " + emailField.getText() + " " + matriculeField.getText() + " " + classCode.getText() + " " + view.getSeasonDropdown().getSelectionModel().getSelectedItem());
                    firstNameField.setText("");
                    lastNameField.setText("");
                    emailField.setText("");
                    matriculeField.setText("");
                    classCode.setText("");
                    // Display success prompt
                    Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
                    successAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR,"Matricula should be 6 digit number.",ButtonType.OK);
                    errorAlert.showAndWait();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR,"Kindly Fill all fields",ButtonType.OK);
                errorAlert.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR,"Please recheck all fields. Matricula should be 6 digit number",ButtonType.OK);
            errorAlert.showAndWait();
        }
    }
}

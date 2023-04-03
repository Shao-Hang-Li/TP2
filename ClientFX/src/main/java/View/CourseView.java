package View;

import controller.courseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Course;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the UI for the course list view, which displays a table of courses
 * and allows the user to filter by season.
 * The UI elements include a table view for displaying the courses, a combo box for selecting the season,
 * and a load button for fetching the courses for the selected season.
 * The course data is retrieved from a course controller.
 */
public class CourseView extends BorderPane {
    private TableView<Course> courseTable; // Table view for displaying courses
    private ComboBox<String> seasonDropdown; // Combo box for selecting season
    private Button loadButton; // Button for loading courses

    private courseController courseController; // Course controller for retrieving course data

    /**
     * Constructs a new CourseView object with the given course controller.
     * @param courseController the course controller to use for retrieving course data
     */
    public CourseView(courseController courseController) {
        this.courseController = courseController;
        initializeUI();
    }

    /**
     * Initializes the UI elements for the course list view.
     */
    private void initializeUI() {
        // Set up UI elements
        Label titleLabel = new Label("Liste des cours");
        titleLabel.setStyle("-fx-font-size: 24px;");
        titleLabel.setAlignment(Pos.CENTER);

        Label seasonLabel = new Label("Saison:");
        seasonDropdown = new ComboBox<>();
        ObservableList<String> seasonOptions = FXCollections.observableArrayList("Hiver", "Automne", "Ete");
        seasonDropdown.setItems(seasonOptions);
        seasonDropdown.getSelectionModel().selectFirst();

        loadButton = new Button("Charger");
        loadButton.setOnAction(event -> handleLoadButton());

        TableColumn<Course, String> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeColumn.setPrefWidth(175);

        TableColumn<Course, String> nameColumn = new TableColumn<>("Cours");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(175);

        courseTable = new TableView<>();
        courseTable.setPrefWidth(350);
        courseTable.setPrefHeight(300);
        courseTable.getColumns().addAll(codeColumn, nameColumn);

        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(titleLabel, courseTable);
        mainBox.setSpacing(10);
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);

        HBox optionsBox = new HBox();
        optionsBox.getChildren().addAll(seasonLabel, seasonDropdown, loadButton);
        optionsBox.setSpacing(10);
        optionsBox.setPadding(new Insets(20));
        optionsBox.setAlignment(Pos.CENTER);

        this.setTop(mainBox);
        this.setBottom(optionsBox);

        // Set the stylesheet for the scene
    }

    /**
     * Handles the click event for the load button by fetching the courses for the selected season
     * from the course controller and displaying them in the course table.
     */
    private void handleLoadButton() {
        try {
            courseTable.getItems().clear();
            ArrayList<Course> courses = courseController.sendCommand("CHARGER", seasonDropdown.getSelectionModel().getSelectedItem());
            for (Course course: courses) {
                courseTable.getItems().add(course);
            }
        } catch (IOException | ClassNotFoundException e
) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter method for seasonDropdown
     * @return A comboBox of Seasons - seasonsDropdown
     */
    public ComboBox<String> getSeasonDropdown() {
        return seasonDropdown;
    }
}

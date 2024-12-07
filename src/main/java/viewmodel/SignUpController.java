package viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserSession;

import java.util.prefs.Preferences;


public class SignUpController {


    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Button signUpBtn;

    @FXML
    private Button goBackBtn;


    public void createNewAccount(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            alert.setContentText("Please fill all the fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
           alert.setContentText("Passwords do not match.");
           return;
        }


        try {

            Preferences userPreferences = Preferences.userRoot().node(this.getClass().getName());
            userPreferences.put("USERNAME", email);
            userPreferences.put("PASSWORD", password);


            UserSession.getInstance(email, password);

            alert.setTitle("Account Created");
            alert.setContentText("Your account has been successfully created and stored.");
            alert.showAndWait();

            goBack(actionEvent);

        } catch (Exception e) {
            e.printStackTrace();
            alert.setTitle("Error");
            alert.setContentText("Error storing account information. Please try again.");
        }
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



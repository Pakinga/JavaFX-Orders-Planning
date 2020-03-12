package plan.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import plan.model.User;
import plan.model.UserDAO;
import plan.utils.Validation;

import java.sql.ResultSet;

public class Controller {
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private Label error;
    @FXML
    private TextField regUser;
    @FXML
    private PasswordField regPassw;
    @FXML
    private PasswordField regConfPassw;
    @FXML
    private TextField regEmail;
    @FXML
    private ComboBox regTeam;
    @FXML
    private CheckBox admin;
    @FXML
    private Label regError;



    ResultSet rsAllEntries;
    ObservableList<ObservableList> data = FXCollections.observableArrayList();

    public void login(ActionEvent event) {
        if (Validation.isValidUsername(username.getText()) && Validation.isValidPassword(password.getText())) {
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.login(username.getText(), password.getText());
            if (msg.contains("Successful")) {
                User user = userDAO.getUser(username.getText());
                error.setText("You made log in");
                //
            } else {
                error.setText(msg);
            }

        } else {
            error.setText("Wrong user name or password!");
        }
        error.setVisible(true);
    }

    public void loadLogin(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 450, 350));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(ActionEvent event) {
        try {
            // we are in controller folder, but our view is not here, so we need to go one step up - ../
            Parent root = FXMLLoader.load(getClass().getResource("../view/register.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void registerLogin(ActionEvent event) {
        boolean isRegistered = true;

        // clear errors on btn pressed
        regError.setText("");
        if (!Validation.isValidUsername(regUser.getText())) {
            regError.setText("Username is incorrect (letters and numbers only, at least 5 char)");
            isRegistered = false;
        } else if (!Validation.isValidPassword(regPassw.getText())) {
            regError.setText("Password is incorrect (letters and numbers only, at least 5 char)");
            isRegistered = false;
        } else if (!regConfPassw.getText().equals(regPassw.getText())) {
            regError.setText("Password doesn't match");
            isRegistered = false;
        } else if (!Validation.isValidEmail(regEmail.getText())) {
            regError.setText("Email is not correct, pattern- name@one.org");
            isRegistered = false;
        } else if (regTeam.getValue() == null) { //(!regTeam.getSelectionModel().isEmpty())
            regError.setText("Team name is not sellected");
            isRegistered = false;
        }

        if (isRegistered) {
            User user = new User(regUser.getText(), regPassw.getText(), regEmail.getText(), (String)regTeam.getValue(), admin.isSelected());
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.register(user);
            if (msg.contains("successfully")) {
                try {
                    loadLogin();
                    // hides current stage (window)
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                regError.setText(msg);
            }
        }
    }

}

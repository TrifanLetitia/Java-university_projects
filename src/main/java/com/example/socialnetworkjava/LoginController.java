package com.example.socialnetworkjava;

import com.example.socialnetworkjava.controller.MessageAlert;
import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.services.FriendshipService;
import com.example.socialnetworkjava.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    public TableView<User> friendsTable;
    public TableColumn<User, String> friendNameColumn;
    public TableColumn<User, String> friendsEmailColumn;
    ObservableList<User> model = FXCollections.observableArrayList();
    ObservableList<User> modelf = FXCollections.observableArrayList();

    public static UserService service;
    public static FriendshipService servicef;
    public Button SignupButton;
    public TextField sName;
    public TextField sEmail;
    public TextField sPass;
    public TextField sAge;
    public Button FriendsButton;
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;
    @FXML
    private Button btn_login;
    private static String email1 = "";
    private static String pass1 = "";

    public static User userL = new User();


    public void setService(UserService service, FriendshipService servicef) {
        LoginController.service = service;
        LoginController.servicef = servicef;
    }

    public void Login(ActionEvent event) throws Exception {
        int n = 0;
        email1 = email.getText();
        pass1 = password.getText();
        for (User user : service.getAll()) {
            if (user.getEmail().equals(email.getText()) && user.getPassword().equals(password.getText())) {
                userL = user;
                n++;
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Username And Password is Corect");
                btn_login.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("views/ProfileView.fxml"));
                Parent root = loader.load();
                ProfileController controller = loader.getController();
                controller.setService(service, servicef);
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
            }
        }
        if (n == 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Invalide Username Or Password");
        }
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setName(sName.getText());
        user.setEmail(sEmail.getText());
        user.setPassword(sPass.getText());
        user.setAge(Integer.valueOf(sAge.getText()));
        User saved = service.addUtilizator(user);
        Parent root = (Parent) actionEvent.getSource();
        root.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/LoginView.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User adaugat cu succes!");

    }


    public void CreateAccount(ActionEvent actionEvent) throws IOException {
        btn_login.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/SING_UPView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("SIGN UP");
    }

    public LoginController() {
    }


    public static String getEmail() {
        return email1;
    }

    public static String getPassword() {
        return pass1;
    }
}

package com.example.socialnetworkjava;

import com.example.socialnetworkjava.domain.Friendship;
import com.example.socialnetworkjava.domain.Tuple;
import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.domain.UserValidator;
import com.example.socialnetworkjava.repository.Repository;
import com.example.socialnetworkjava.repository.dbrepo.FriendshipDbRepository;
import com.example.socialnetworkjava.repository.dbrepo.UserDbRepository;
import com.example.socialnetworkjava.services.FriendshipService;
import com.example.socialnetworkjava.services.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    UserService service = new UserService();
    FriendshipService servicef = new FriendshipService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("Reading data from file");
        String username = "postgres";
        String pasword = "leti5";
        String url = "jdbc:postgresql://localhost:5432/socialnetwork1";
        Repository<Long, User> utilizatorRepository =
                new UserDbRepository(url, username, pasword, new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendshipRepository = new FriendshipDbRepository(url, username, pasword);

        utilizatorRepository.findAll().forEach(x -> System.out.println(x));
        friendshipRepository.findAll().forEach(x-> System.out.println(x));
        service = new UserService(utilizatorRepository);
        servicef = new FriendshipService(friendshipRepository);
        initView(primaryStage);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource("com/example/guiex1/views/UserViewUserView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/LoginView.fxml"));
        Parent userLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(userLayout));
        LoginController controller = fxmlLoader.getController();
        controller.setService(service, servicef);
       // UserController userController = fxmlLoader.getController();
       // userController.setUtilizatorService(service);

    }
}
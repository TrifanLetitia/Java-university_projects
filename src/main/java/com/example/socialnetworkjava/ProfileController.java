package com.example.socialnetworkjava;

import com.example.socialnetworkjava.controller.MessageAlert;
import com.example.socialnetworkjava.domain.Friendship;
import com.example.socialnetworkjava.domain.Tuple;
import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.services.FriendshipService;
import com.example.socialnetworkjava.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.socialnetworkjava.LoginController.*;

public class ProfileController{
    public String email;
    public String password;
    private static User user = new User();
    @FXML
    public TableView<User> friendsTable = new TableView<>();
    @FXML
    public TableColumn<User, String> friendNameColumn = new TableColumn<>();
    @FXML
    public TableColumn<User, String> friendsEmailColumn = new TableColumn<>();
    public TableView<User> usersTable = new TableView<>();
    public TableColumn<User,String> userName = new TableColumn<>();
    public TableColumn<User, String> userEmail = new TableColumn<>();
    public TextField filterField;
    public TableView<User> requestsTable = new TableView<>();
    public TableColumn<User,String> fname = new TableColumn<>();
    public TableColumn<User, String> femail = new TableColumn<>();

    public TableColumn<Friendship, String> fdate = new TableColumn<Friendship, String>();
    public TableView<Friendship> dateTable = new TableView<Friendship>();
    public AnchorPane profileAnchor;

    UserService userService;
    FriendshipService friendshipService;
    ObservableList<User> modelf = FXCollections.observableArrayList();
    ObservableList<User> model = FXCollections.observableArrayList();

    ObservableList<User> modelr = FXCollections.observableArrayList();

    ObservableList<Friendship> dates = FXCollections.observableArrayList();

    public void setService(UserService service, FriendshipService service1){
        this.userService = LoginController.service;
        this.friendshipService = LoginController.servicef;
        for (User user1 : userService.getAll()) {
            if (user1.getEmail().equals(LoginController.getEmail()) && user1.getPassword().equals(LoginController.getPassword())) {
                user = user1;
            }
        }
    }
    public void showFriends(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/ShowFriendsView.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void initialize(){
        initModel();
        friendNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        friendsEmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        friendsTable.setItems(modelf);
        initModel1();
        userName.setCellValueFactory(new PropertyValueFactory<User,String>("Name"));
        userEmail.setCellValueFactory(new PropertyValueFactory<User,String >("Email"));
        usersTable.setItems(model);
        fname.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        femail.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        fdate.setCellValueFactory(new PropertyValueFactory<Friendship, String>("Date"));
       // requestsTable.setItems(modelr);
        List<User> users = new ArrayList<>();
        List<Friendship> dates1 = new ArrayList<>();
        for(Friendship friendship: servicef.getAll()){
            if(friendship.getId().getRight().equals(userL.getId()) && friendship.getStatus().equals("SENT")){
                users.add(service.getOne(friendship.getId().getLeft()));
                dates1.add(friendship);
            }
        }
        modelr.setAll(users);
        dates.setAll(dates1);
        requestsTable.setItems(modelr);
        dateTable.setItems(dates);
    }


    public void showfr(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/friendRequestsView.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void info(ActionEvent actionEvent) {

    }

    public void deactivateAccount(ActionEvent actionEvent) {
    }

    public void addfriends(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/AddFriend.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    private void initModel() {
        User user1 = new User();
        for(User u: service.getAll()){
            if(u.getEmail().equals(LoginController.getEmail()) && u.getPassword().equals(LoginController.getPassword())){
                user1 = u;
            }
        }
        List<User> users = new ArrayList<>();
        for(Friendship f: servicef.getAll()){
            if(f.getId().getLeft().equals(user1.getId()) && f.getStatus().equals("ACCEPTED")){
                users.add(service.getOne(f.getId().getRight()));
            }
            if(f.getId().getRight().equals(user1.getId()) && f.getStatus().equals("ACCEPTED")){
                users.add(service.getOne(f.getId().getLeft()));
            }
        }
        modelf.setAll(users);
    }


    public void deleteFriend(ActionEvent actionEvent) {
        User user1=(User) friendsTable.getSelectionModel().getSelectedItem();
        User user2 = new User();
        for(User u: service.getAll()){
            if(u.getEmail().equals(LoginController.getEmail()) && Objects.equals(u.getPassword(), LoginController.getPassword())){
                user2 = u;
            }
        }
        if (user1!=null) {
            Tuple<Long, Long> id = new Tuple<Long, Long>(user1.getId(), user2.getId());
            modelf.remove(user1);
            servicef.deleteFriend(id);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Prieten sters cu succes!");
        }
        else MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "INFO", "Nu ati selectat niciun user!");
    }

    public void addfriends1(ActionEvent actionEvent) {
        User user1 = (User) usersTable.getSelectionModel().getSelectedItem();
        for(Friendship f: servicef.getAll()){
            Tuple<Long, Long> id = new Tuple<>(userL.getId(), user1.getId());
            if(servicef.inFriendship(id)){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Sunteti deja prieten cu acest utilizator!");
            }
            if(f.getId().getLeft().equals(userL.getId()) && f.getId().getRight().equals(user1.getId()) && f.getStatus().equals("SENT")){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Cererea de prietenia a fost trimisa deja!");
            }
        }
        User user2 = new User();
        if (user1!=null) {
            Friendship f = new Friendship(Date.valueOf(LocalDateTime.now().toLocalDate()), "SENT");
            Tuple<Long, Long> id = new Tuple<>(userL.getId(), user1.getId());
            f.setId(id);
            Friendship friendship = servicef.addFriend(f);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Cerere de prietenie trimisa cu succes!");
        }
        else MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "INFO", "Nu ati selectat niciun user!");
    }

    private void initModel1() {
        Iterable<User> messages = service.getAll();
        List<User> users = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        users.remove(service.getOne(userL.getId()));
        for (User user1 : service.getAll()) {
            Tuple<Long, Long> id = new Tuple<>(userL.getId(), user1.getId());
            if (servicef.inFriendship(id)) {
                users.remove(user1);
            }
        }
        for (Friendship f : servicef.getAll()) {
            for (User u : service.getAll()) {
                if (f.getId().getLeft().equals(userL.getId()) && f.getId().getRight().equals(u.getId()) && f.getStatus().equals("SENT")) {
                    users.remove(u);
                }
                if(f.getId().getLeft().equals(u.getId()) && f.getId().getRight().equals(userL.getId()) && f.getStatus().equals("SENT")){
                    users.remove(u);
                }
            }
        }
        model.setAll(users);
    }

    public void sortList(KeyEvent keyEvent) {
        FilteredList<User> filteredData = new FilteredList<>(usersTable.getItems(), p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else return String.valueOf(myObject.getEmail()).toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(usersTable.comparatorProperty());
        usersTable.setItems(sortedData);
    }

    public void acceptRequest(ActionEvent actionEvent) {
        User user1 = (User) requestsTable.getSelectionModel().getSelectedItem();
        if (user1!=null) {
            Tuple<Long, Long> id = new Tuple<>(user1.getId(), user.getId());
            if(servicef.inFriendship(id)){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Sunteti prieteni deja!");
            }
            Friendship f = servicef.getOne(id);
            f.setStatus("ACCEPTED");
            f.setDate(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
            servicef.update(f);
            initialize();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Cerere de prietenie acceptata cu succes!");
        }
        else MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "INFO", "Nu ati selectat niciun user!");
    }

    public void declineRequest(ActionEvent actionEvent) {
        User user1= (User) requestsTable.getSelectionModel().getSelectedItem();
        if (user1!=null) {
            Tuple<Long, Long> id = new Tuple<>(user1.getId(), user.getId());
            Friendship f = servicef.getOne(id);
            servicef.deleteFriend(id);
            initialize();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Cerere de prietenie respinsa!");
        }
        else MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "INFO", "Nu ati selectat niciun user!");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        profileAnchor.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/LoginView.fxml"));
        Parent userLayout = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(userLayout));
        LoginController controller = loader.getController();
        controller.setService(service, servicef);
        stage.show();
    }
}

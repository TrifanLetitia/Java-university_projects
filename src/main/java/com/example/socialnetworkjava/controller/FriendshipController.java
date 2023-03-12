package com.example.socialnetworkjava.controller;

import com.example.socialnetworkjava.domain.Friendship;
import com.example.socialnetworkjava.domain.MyException;
import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.services.FriendshipService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendshipController {
    FriendshipService service;
    ObservableList<Friendship> model = FXCollections.observableArrayList();
    TableView<Friendship> tableView;
    @FXML
    TableColumn<User,String> tableColumnId1;
    @FXML
    TableColumn<User,Integer> tableColumnId2;

    public void setFriendshipService(FriendshipService service){
        this.service = service;
    }

    private void initModel() {
        Iterable<Friendship> messages = service.getAll();
        List<Friendship> friendships = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(friendships);
    }


    @FXML
    public void initialize() {
        tableColumnId1.setCellValueFactory(new PropertyValueFactory<User, String>("Id_u1"));
        tableColumnId2.setCellValueFactory(new PropertyValueFactory<User, Integer>("Id_u2"));
        tableView.setItems(model);
    }

    public void handleAddFriendship(ActionEvent actionEvent){
        Friendship friendship = new Friendship();
        try {
            Friendship saved = service.addFriend(friendship);
        } catch (MyException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            return;
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Prietenie adaugata cu succes!");
    }

    public void handleDeleteFriendship(ActionEvent actionEvent){
        Friendship friendship=(Friendship) tableView.getSelectionModel().getSelectedItem();
        if (friendship!=null) {
            Optional<Friendship> deleted= service.deleteFriend(friendship.getId());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User sters cu succes!");
            tableView.refresh();
        }
    }
}

package com.example.socialnetworkjava.controller;

import com.example.socialnetworkjava.domain.MyException;
import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.services.UserService;
import com.example.socialnetworkjava.utils.events.UserEntityChangeEvent;
import com.example.socialnetworkjava.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements Observer<UserEntityChangeEvent>{
    UserService service;
    ObservableList<User> model = FXCollections.observableArrayList();
    @FXML
    TableView<User> tableView;

    @FXML
    TableColumn<User,String> tableColumnName;
    @FXML
    TableColumn<User, String> tableColumnEmail;
    @FXML
    TableColumn<User, String> tableColumnPassword;
    @FXML
    TableColumn<User,Integer> tableColumnAge;

    public String name;

    public String email;

    public String password;

    public Integer age;

    @FXML
    Button addButton;

    @FXML
    TextField txt;

    @FXML TextField txt1;

    @FXML TextField txt2;

    @FXML TextField txt3;


    public void setUtilizatorService(UserService service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        tableColumnPassword.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
        tableColumnAge.setCellValueFactory(new PropertyValueFactory<User, Integer>("Age"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<User> messages = service.getAll();
        List<User> users = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
    }

    @Override
    public void update(UserEntityChangeEvent utilizatorEntityChangeEvent) {
        initModel();
    }


    public void handleAddUtilizator(MouseEvent actionEvent) throws Exception {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAge(age);
        try {
            User saved = service.addUtilizator(user);
        } catch (MyException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
            return;
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User adaugat cu succes!");
         if(user == null){
            MessageAlert.showErrorMessage(null, "Failed adding user");
         }
    }


    public void handleDeleteUtilizator(ActionEvent actionEvent) {
        User user=(User) tableView.getSelectionModel().getSelectedItem();
        if (user!=null) {
            User deleted= service.deleteUtilizator(user.getId());
            initialize();
            initModel();
            tableView.refresh();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "User sters cu succes!");
        }
        else MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "INFO", "Nu ati selectat niciun user!");
    }

    public void handleUpdateUtilizator(ActionEvent actionEvent) {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showMessageTaskEditDialog(actionEvent);
        } else MessageAlert.showErrorMessage(null, "NU ati selectat nici un user");
    }

    public void handleRefresh(MouseEvent actionEvent){
        tableView.refresh();
        model.clear();
        initModel();
    }

  public void showMessageTaskEditDialog(ActionEvent messageTask) {
      try {
           // create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("./views/AddView.fxml"));

           AnchorPane root = (AnchorPane) loader.load();
           // Create the dialog Stage.
            Stage dialogStage = new Stage();
          dialogStage.setTitle("Edit Message");
          dialogStage.initModality(Modality.WINDOW_MODAL);
           //dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(root);
            dialogStage.setScene(scene);

           //EditMessageTaskController editMessageViewController = loader.getController();
           // editMessageViewController.setService(service, dialogStage, messageTask);
           dialogStage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
   }


    public void handleShowUtilizatori(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Users");
        stage.setScene(scene);
    }

    public void  setName(KeyEvent mouseEvent) {
        name = txt.getText();
    }

    public void setEmail(KeyEvent mouseEvent) {
        email = txt1.getText();
    }

    public void   setPassword(KeyEvent mouseEvent) {
        password = txt2.getText();
    }

    public void setAge(KeyEvent mouseEvent) {
        age = Integer.valueOf(txt3.getText());
    }

}

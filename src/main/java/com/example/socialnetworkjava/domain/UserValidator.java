package com.example.socialnetworkjava.domain;

import com.example.socialnetworkjava.controller.MessageAlert;
import javafx.scene.control.Alert;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws MyException {
        //TODO: implement method validate
        if(entity.getName() == null)
            throw new MyException("Names cannot be null");
    //    if(entity.getId() <= 0){
          //  throw (new MyException("Invalid id!"));
       // }
        if(entity.getAge() < 13){
            MessageAlert.showMessage(null,Alert.AlertType.INFORMATION, "INFO", "It is not allowed to use the social network by persons under 13!");
            throw(new MyException("It is not allowed to use the social network by persons under 13!"));
        }
    }
}

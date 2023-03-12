package com.example.socialnetworkjava;

import com.example.socialnetworkjava.UI.Menu;
import com.example.socialnetworkjava.domain.Friendship;
import com.example.socialnetworkjava.domain.Tuple;
import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.domain.UserValidator;
import com.example.socialnetworkjava.repository.Repository;
import com.example.socialnetworkjava.repository.dbrepo.FriendshipDbRepository;
import com.example.socialnetworkjava.repository.dbrepo.UserDbRepository;
import com.example.socialnetworkjava.services.FriendshipService;
import com.example.socialnetworkjava.services.UserService;

public class Main {
    public static void main(String[] args) {
        System.out.println("ok");
        System.out.println("Reading data from file");
        String username="postgres";
        String pasword="leti5";
        String url="jdbc:postgresql://localhost:5432/socialnetwork1";
        Repository<Long, User> userFileRepository3 =
                new UserDbRepository(url,username, pasword,  new UserValidator());

        userFileRepository3.findAll().forEach(x-> System.out.println(x));

        UserService service = new UserService(userFileRepository3);

        Repository<Tuple<Long, Long>, Friendship> friendshipRepository = new FriendshipDbRepository(url, username, pasword);

        FriendshipService service1 = new FriendshipService(friendshipRepository);

        Menu.MainMenu(service, service1);
    }
}
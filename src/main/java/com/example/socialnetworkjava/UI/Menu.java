package com.example.socialnetworkjava.UI;

import com.example.socialnetworkjava.domain.Friendship;
import com.example.socialnetworkjava.domain.MyException;
import com.example.socialnetworkjava.domain.Tuple;
import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.services.FriendshipService;
import com.example.socialnetworkjava.services.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {
    public static void MainMenu(UserService users, FriendshipService friendships) {
        Scanner sc = new Scanner(System.in);
        System.out.println("------------WELCOME TO MAP_ToySocialNetwork------------");
        String mainMenu = ("Choose an option from the menu: \n"
                + "1. Add user.\n"
                + "2. Remove user.\n"
                + "3. Add friend.\n"
                + "4. Remove friend.\n"
                + "5. Show all users.\n"
                + "6. Show all frienships.\n"
                /*
                + "7. Add community.\n"
                + "8. Add users to a community.\n"
                + "9. Show number of communities.\n"
                + "10. Show the most sociable community.\n"
                + "11. Show friends of a user.\n"
                 */
                + "7. Exit."
        );
        int op = 0;
        do {
            try {
                System.out.println(mainMenu);
                System.out.println("Give option: ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                sc.next();
            }
            switch (op) {
                case 1:
                    try {
                        System.out.println("-------------ADD USER OPTION-------------\n");
                        System.out.println("Give a name: ");
                        String name;
                        name = sc.next();
                        System.out.println("Give an email: ");
                        String email = sc.next();
                        System.out.println("Give a password: ");
                        String password = sc.next();
                        System.out.println("Give age: ");
                        Integer age = sc.nextInt();
                        User u = new User(name, email, password, age);
                        u.setEmail(email);
                        u.setPassword(password);
                        for (User u1 : users.getAll()) {
                            if (u1.getId() == u.getId()) {
                                throw new MyException("Another user with this id!");
                            }
                        }
                        users.addUtilizator(u);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                        sc.next();
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("-------------REMOVE USER OPTION-------------\n");
                        System.out.println("Give the ID: ");
                        Long id1 = sc.nextLong();
                        users.deleteUtilizator(id1);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                        sc.next();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        //sc.next();
                    }
                    break;
                case 3:
                    try {
                        System.out.println("-------------ADD FRIEND OPTION-------------\n");
                        System.out.println("Give the id of the first user: ");
                        Long ID1 = sc.nextLong();
                        System.out.println("Give the id of the second user: ");
                        Long ID2 = sc.nextLong();
                        if (ID1 == ID2) {
                            throw new MyException("Invalid id!");
                        }
                        int n1 = 0;
                        int n2 = 0;
                        User f1 = new User();
                        for (User u1 : users.getAll()) {
                            if (u1.getId() == ID1) {
                                f1 = u1;
                            }
                        }
                        User f2 = new User();
                        for (User u1 : users.getAll()) {
                            if (u1.getId() == ID2) {
                                f2 = u1;
                            }
                        }
                        Friendship friendship1 = new Friendship();
                        friendships.addFriend(friendship1);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                        sc.next();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        //sc.next();
                    }
                    break;
                case 4:
                    try {
                        System.out.println("-------------REMOVE FRIEND OPTION-------------\n");
                        System.out.println("Give the ids of the users: ");
                        System.out.println("Id 1: ");
                        Long i1 = sc.nextLong();
                        int n = 0;
                        for (User u1 : users.getAll()) {
                            if (u1.getId() == i1) {
                                n++;
                            }
                        }
                        if (n == 0) {
                            throw new MyException("No user found!");
                        }
                        System.out.println("Id 2: ");
                        Long i2 = sc.nextLong();
                        for (User u1 : users.getAll()) {
                            if (u1.getId() == i2) {
                                n++;
                            }
                        }
                        if (n == 0) {
                            throw new MyException("No user found!");
                        }
                        Friendship Friendship1 = new Friendship();
                        Tuple<Long, Long> ids = new Tuple<>(i1, i2);
                        Friendship1.setId(ids);
                        Tuple<Long, Long> ids1 = new Tuple<>(i2, i1);
                        Friendship Friendship2 = new Friendship();
                        Friendship2.setId(ids1);
                        for (Friendship friendship : friendships.getAll()) {
                            if (friendship.equals(Friendship1) || friendship.equals(Friendship2)) {
                                throw new MyException("These users are friends already!");
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                        sc.next();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    for (User u : users.getAll()) {
                        System.out.println(u);
                    }
                    break;
                case 6:
                    for (Friendship friendship : friendships.getAll()) {
                        System.out.println(friendship);
                    }
                    break;
                case 7:
                    System.out.println("Bye, bye!");
                    exit(0);
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
        while (true);
    }
}
package com.example.socialnetworkjava.services;

import com.example.socialnetworkjava.domain.User;
import com.example.socialnetworkjava.repository.Repository;
import com.example.socialnetworkjava.utils.events.ChangeEventType;
import com.example.socialnetworkjava.utils.events.UserEntityChangeEvent;
import com.example.socialnetworkjava.utils.observer.Observable;
import com.example.socialnetworkjava.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements Observable<UserEntityChangeEvent> {
    private Repository<Long, User> repo;
    private List<Observer<UserEntityChangeEvent>> observers = new ArrayList<>();

    public UserService(Repository<Long, User> repo) {
        this.repo = repo;
    }

    public UserService() {

    }


    public User addUtilizator(User user) {
        if (repo.save(user).isEmpty()) {
            UserEntityChangeEvent event = new UserEntityChangeEvent(ChangeEventType.ADD, user);
            notifyObservers(event);
            return null;
        }
        return user;
    }

    public User deleteUtilizator(Long id) {
        Optional<User> user = repo.delete(id);
        if (user.isPresent()) {
            notifyObservers(new UserEntityChangeEvent(ChangeEventType.DELETE, user.get()));
            return user.get();
        }
        return null;
    }

    public Iterable<User> getAll() {
        return repo.findAll();
    }


    @Override
    public void addObserver(Observer<UserEntityChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<UserEntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UserEntityChangeEvent t) {

        observers.stream().forEach(x -> x.update(t));
    }

    public int getSize(){
        return repo.getSize();
    }

    public User getOne(Long id){
        for(User user: getAll()){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }
}
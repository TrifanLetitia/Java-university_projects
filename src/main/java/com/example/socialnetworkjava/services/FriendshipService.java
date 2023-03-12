package com.example.socialnetworkjava.services;

import com.example.socialnetworkjava.domain.Friendship;
import com.example.socialnetworkjava.domain.Tuple;
import com.example.socialnetworkjava.repository.Repository;

import java.util.Objects;
import java.util.Optional;

public class FriendshipService {
    Repository<Tuple<Long, Long>,Friendship> repo;

    public FriendshipService(Repository<Tuple<Long, Long>, Friendship> repo) {
        this.repo = repo;
    }

    public FriendshipService() {

    }

    public Friendship addFriend(Friendship friend){
        repo.save(friend);
        return friend;
    }

    public Optional<Friendship> deleteFriend(Tuple<Long, Long> id){
        Optional<Friendship> friend = repo.delete(id);
        return friend;
    }

    public Iterable<Friendship> getAll() {
        return repo.findAll();
    }

    public int getSize(){
        return repo.getSize();
    }

    public boolean inFriendship(Tuple<Long, Long> id){
        for(Friendship f : getAll()){
            if(Objects.equals(f.getId().getLeft(), id.getLeft()) && Objects.equals(f.getId().getRight(), id.getRight()) && f.getStatus().equals("ACCEPTED")){
                return true;
            }
            if(Objects.equals(f.getId().getRight(), id.getLeft()) && Objects.equals(f.getId().getLeft(), id.getRight()) && f.getStatus().equals("ACCEPTED")){
                return true;
            }
        }
        return false;
    }

    public Friendship getOne(Tuple<Long, Long> id){
        for(Friendship f : getAll()){
            if(f.getId().getLeft() == id.getLeft() && f.getId().getRight() == id.getRight()){
                return f;
            }
        }
        return null;
    }

    public Friendship update(Friendship f){
        repo.update(f);
        return f;
    }
}

package com.example.socialnetworkjava.utils.observer;

import com.example.socialnetworkjava.utils.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}

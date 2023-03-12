package com.example.socialnetworkjava.utils.observer;

import com.example.socialnetworkjava.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
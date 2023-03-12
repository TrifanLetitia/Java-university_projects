package com.example.socialnetworkjava.domain;

public interface Validator<T> {
    void validate(T entity) throws MyException;
}
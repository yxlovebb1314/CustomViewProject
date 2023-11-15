package com.example.retrofittheory.genericity;

public interface Person<T> {

    void setAge(int age);
    void setData(T t);
    T getData();
}

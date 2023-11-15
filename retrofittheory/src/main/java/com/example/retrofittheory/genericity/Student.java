package com.example.retrofittheory.genericity;

public abstract class Student<Data, CN> implements Person<Data> {

    public int age;

    abstract void setName(CN className);

    @Override
    public void setAge(int age) {
        this.age = age;
    }
}

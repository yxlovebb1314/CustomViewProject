package com.example.retrofittheory.genericity;

public class MusicStudent<Data, CN> extends Student<Data, CN> {

    private Data data;
    private CN className;


    @Override
    public void setData(Data data) {

    }

    @Override
    public Data getData() {
        return data;
    }

    @Override
    public void setName(CN className) {

    }

    @Override
    public String toString() {
        return "MusicStudent{" +
                "data=" + data +
                ", className=" + className +
                '}';
    }
}

package com.example.retrofittheory.genericity;

public class SportsStudent<Data, CN> extends Student<Data, CN>{

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
}

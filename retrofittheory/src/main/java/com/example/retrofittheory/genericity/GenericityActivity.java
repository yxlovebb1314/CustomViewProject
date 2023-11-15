package com.example.retrofittheory.genericity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.retrofittheory.R;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericityActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genericity);
        List<MusicStudent> msList = new ArrayList<>();
//        getList(msList);


        Student student = new Student() {
            @Override
            public void setName(Object className) {

            }

            @Override
            public void setData(Object o) {

            }

            @Override
            public Object getData() {
                return null;
            }
        };
        List<? super MusicStudent> msList2 = new ArrayList<Student>();
        List<? extends Student> students = new ArrayList<MusicStudent>();

        List<MusicStudent> msList3 = new ArrayList<>();
        List<SportsStudent> ss = new ArrayList<>();
        getSmallestAge(msList3);
        getSmallestAge(ss);

        List<? super MusicStudent> msList4 = new ArrayList<Student>();
        msList4.add(new MusicStudent());

        MusicStudent ms2 = new MusicStudent();

        List<Person> personList = new ArrayList<>();
        List<Student> studentList2 = new ArrayList<>();
        List<MusicStudent> msList5 = new ArrayList<>();

        addToList(personList, ms2);
        addToList(studentList2, ms2);
        addToList(msList5, ms2);

        TestGenerics<String> testGenerics = new TestGenerics<String>(){};
        try {
            Field fieldT2 = testGenerics.getClass().getField("t");
            Type genericType = fieldT2.getGenericType();
            String typeName = genericType.getTypeName();
            Log.e("TAG", "genericType: " + fieldT2.getGenericType() + ", typeName: " + typeName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        TestGenerics<MusicStudent> tg1 = new TestGenerics<>();
    }

    class TestGenerics<T> {
        public T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    public int getSmallestAge(List<? extends Student> list) {
        int smallestAge = 0;
        for (int i = 0; i < list.size(); i++) {
            int tempAge = list.get(i).age;
            if (i == 0) {
                smallestAge = tempAge;
            } else {
                if (smallestAge > tempAge) {
                    smallestAge = tempAge;
                }
            }
        }
        return smallestAge;
    }

    public void addToList(List<? super MusicStudent> list, MusicStudent s) {
        list.add(s);
    }



}
package com.codingke.vince46_sqlite;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private DatabaseAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAdapter = new DatabaseAdapter(this);
    }

    public void findAllClick(View view) {
        ArrayList<Dog> dogs = dbAdapter.findAll();
        int size = dogs.size();
        for (int i=0;i<size;i++){
            System.out.println(dogs.get(i));
        }
    }

    public void findByIdClick(View view) {
        Dog dog = dbAdapter.findById(1);
        System.out.println(dog);
    }

    public void updateClick(View view) {
        Dog dog = new Dog(1,"wangwang",5);
        dbAdapter.update(dog);
    }

    public void deleteClick(View view) {
        dbAdapter.delete(1);
    }

    public void addClick(View view) {
        Dog dog = new Dog("doge",5);
        dbAdapter.add(dog);

    }


}

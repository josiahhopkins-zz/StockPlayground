package com.example.josiah.stockplayground;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.josiah.stockplayground.group.Group;

import layout.OptionDisplayFragment;

public class StockActivity extends AppCompatActivity implements GroupListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String user = (getIntent().getStringExtra("Username"));
        setContentView(R.layout.activity_stock);

    }

    public String getUsername(){
        return (getIntent().getStringExtra("Username"));
    }

    @Override
    public void onListFragmentInteraction(Group item) {

    }

    public void goToGroups(){
        GroupListFragment groupListFragment = new GroupListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.stock_fragment_container, groupListFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToOptions(){
        OptionDisplayFragment groupListFragment = new OptionDisplayFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.stock_fragment_container, groupListFragment)
                .commit();
    }


}

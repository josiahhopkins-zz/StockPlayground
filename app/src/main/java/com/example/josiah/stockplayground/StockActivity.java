package com.example.josiah.stockplayground;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.josiah.stockplayground.group.Group;

public class StockActivity extends AppCompatActivity implements GroupListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        String user = (getIntent().getStringExtra("Username"));
    }

    @Override
    public void onListFragmentInteraction(Group item) {

    }
}

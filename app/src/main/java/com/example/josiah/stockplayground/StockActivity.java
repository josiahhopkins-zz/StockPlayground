package com.example.josiah.stockplayground;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josiah.stockplayground.group.Group;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import layout.OptionDisplayFragment;

public class StockActivity extends AppCompatActivity implements AddGroupFragment.GroupAddListener, GroupListFragment.OnListFragmentInteractionListener, OptionDisplayFragment.OnFragmentInteractionListener{

    public static final String GROUP_ADD_URL = "http://cssgate.insttech.washington.edu/~josiah3/PHP_Code/PHP%20Code/addGroup.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String user = (getIntent().getStringExtra("Username"));
        setContentView(R.layout.activity_stock);

        goToOptions();
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


    public void goToAddGroup(){
        AddGroupFragment groupListFragment = new AddGroupFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.stock_fragment_container, groupListFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void addGroup(String url) {
        StockActivity.AddUserTask task = new AddUserTask();
        task.execute(url.toString());
        // Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
    }


    private class AddUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to add course, Reason: " + e.getMessage();
                } finally {
                    if (urlConnection != null) urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks
         * to see if
         * there was
         * a problem
         * with the
         * <p>
         * URL(Network) which is when an      *
         * exception is
         * caught.It tries
         * to call
         * the parse
         * Method and
         * checks to
         * see if
         * it was
         * successful.      *
         * If not, it
         * displays the
         * exception.      **
         *
         * @param result
         */

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                Log.v("onPostExecute", result);
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success!!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: " + jsonObject.get("error"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}

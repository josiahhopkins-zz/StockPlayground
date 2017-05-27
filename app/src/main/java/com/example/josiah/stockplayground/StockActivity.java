package com.example.josiah.stockplayground;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.josiah.stockplayground.classes_a_user_has.Group;
import com.example.josiah.stockplayground.classes_a_user_has.Stock;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import layout.OptionDisplayFragment;

/**
 * This is the holder for all of the non login related actions.
 * @author Josiah
 */
public class StockActivity extends AppCompatActivity implements StockListFragment.StockListFragmentInteractionListener, AddGroupFragment.GroupAddListener, GroupListFragment.OnListFragmentInteractionListener, OptionDisplayFragment.OnFragmentInteractionListener{

    public static final String GROUP_ADD_URL = "http://cssgate.insttech.washington.edu/~josiah3/PHP_Code/PHP%20Code/addGroup.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String user = (getIntent().getStringExtra("Username"));
        setContentView(R.layout.activity_stock);

        goToAvailableStocks();
    }


    public String getUsername(){
        return (getIntent().getStringExtra("Username"));
    }

    @Override
    public void onListFragmentInteraction(Group item) {

    }

    /**
     * Goes to the group fragment.
     */
    public void goToGroups(){
        GroupListFragment groupListFragment = new GroupListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.stock_fragment_container, groupListFragment)
                .addToBackStack(null)
                .commit();

    }

    /**
     * Goes to the options fragment.
     */
    public void goToOptions(){
        OptionDisplayFragment groupListFragment = new OptionDisplayFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.stock_fragment_container, groupListFragment)
                .commit();

    }


    /**
     * Goes to the add group fragment
     */
    public void goToAddGroup(){
        AddGroupFragment groupListFragment = new AddGroupFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.stock_fragment_container, groupListFragment)
                .addToBackStack(null)
                .commit();
    }


    /**
     * Adds a new group where the currently logged in user is the owner.
     * @param url: the url with which a group can be created.
     */
    @Override
    public void addGroup(String url) {
        StockActivity.AddUserTask task = new AddUserTask();
        task.execute(url.toString());
        // Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void ChooseStockToLookAt(Stock item) {

    }

    public void goToAvailableStocks(){
        StockListFragment stockListFragment = new StockListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.stock_fragment_container, stockListFragment)
                .commit();
    }

    /**
     * The class which asynchronously adds a user.
     */
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

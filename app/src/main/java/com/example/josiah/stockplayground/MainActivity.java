package com.example.josiah.stockplayground;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity  implements RegisterFragment.UserAddListener, LoginFragment.loginListener, LoginFragment.registerListener{

    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null
        /* || getSupportFragmentManager().findFragmentById(R.id.list) == null
        */
        ) {
            LoginFragment courseFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_page_container, courseFragment)
                    .commit();
        }

    }

    @Override
    public void addUser(String url) {
        AddUserTask task = new AddUserTask();
        task.execute(url.toString());
        // Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
        isLoggedIn = false;
    }

    @Override
    public void login(String url) {
        AddUserTask task = new AddUserTask();
        task.execute(url.toString());
        // Takes you back to the previous fragment by popping the current fragment out.\
        Log.e("Username", getUsernameFromUrl(url));
        if(isLoggedIn) {
            getSupportFragmentManager().popBackStackImmediate();
            Intent i = new Intent(this, StockActivity.class);
            i.putExtra("Username", getUsernameFromUrl(url));
            startActivity(i);
        }
    }

    private String getUsernameFromUrl(String url){
        String lastHalf = url.substring(url.indexOf("username=") + 9);
        return lastHalf.substring(0, lastHalf.indexOf("&password"));
    }


    @Override
    public void goToRegister() {
            Toast.makeText(this, "Register Clicked", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.login_page_container, new RegisterFragment()).addToBackStack(null).commit();
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
                    isLoggedIn = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: " + jsonObject.get("error"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        isLoggedIn = false;
    }
}

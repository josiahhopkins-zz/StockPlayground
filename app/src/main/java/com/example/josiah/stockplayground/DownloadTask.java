package com.example.josiah.stockplayground;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.josiah.stockplayground.classes_a_user_has.Stock;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josiah on 5/25/2017.
 *

public class DownloadTask extends AsyncTask<String, Void, String> {
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
                response = "Unable to download the list of courses, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        // Something wrong with the network or the URL.
        if (result.startsWith("Unable to")) {
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
            return;
        }
        List<Stock> courseList = new ArrayList<Stock>();
        result = Stock.parseStockJSON(result, courseList);
        // Something wrong with the JSON returned.
        if (result != null) {
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
            return;
        }
        // Everything is good, show the list of courses.
        if (!courseList.isEmpty()) {
            mRecyclerView.setAdapter(new MyCourseRecyclerViewAdapter(courseList, mListener));
        }
    }
}
 */
public class DownloadTask{}

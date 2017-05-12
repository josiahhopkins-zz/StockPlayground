package com.example.josiah.stockplayground.group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Josiah on 5/11/2017.
 */

public class Group implements Serializable {
    public static final String NAME = "group_name", PORTFOLIO_VALUE = "portfolio_value", OWNER = "Owner";
    private String name;

    public Group(String name, String owner, int portfolio_value) {
        this.name = name;
        this.owner = owner;
        this.portfolio_value = portfolio_value;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPortfolio_value() {
        return portfolio_value;
    }

    public void setPortfolio_value(int portfolio_value) {
        this.portfolio_value = portfolio_value;
    }

    private String owner;
    private int portfolio_value;

    /**
     * Parses the json string, returns an error message if unsuccessful.  * Returns Group list if success.  * @param GroupJSON  * @return reason or null if successful.
     */
    public static String parseGroupJSON(String GroupJSON, List<Group> GroupList) {
        String reason = null;
        if (GroupJSON != null) {
            try {
                JSONArray arr = new JSONArray(GroupJSON);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Group group = new Group(obj.getString(Group.NAME), obj.getString(Group.OWNER), obj.getInt(Group.PORTFOLIO_VALUE));
                    GroupList.add(group);
                }
            } catch (JSONException e) {
                reason = "Unable to parse data, Reason: " + e.getMessage();
            }
        }
        return reason;
    }
}

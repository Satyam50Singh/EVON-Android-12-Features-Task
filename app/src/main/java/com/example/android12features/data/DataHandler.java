package com.example.android12features.data;

import android.app.Activity;
import android.content.Intent;

import com.example.android12features.model.UserModel;
import com.example.android12features.view.activities.MainActivity;

import java.util.ArrayList;

public class DataHandler {
    // list variable is used for handling data in this app.
    public static ArrayList<UserModel> list = new ArrayList<>();

    // this method is used for adding users in static variable.
    public static void addUser(Activity context, UserModel userModel) {
        list.add(userModel);
        context.startActivity(new Intent(context, MainActivity.class));
        context.finishAffinity();
    }
}

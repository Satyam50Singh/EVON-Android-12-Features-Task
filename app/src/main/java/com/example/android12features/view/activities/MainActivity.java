package com.example.android12features.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android12features.R;
import com.example.android12features.adapter.UsersAdapter;
import com.example.android12features.data.DataHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcvUsersList;
    FloatingActionButton fabAddUser;
    TextView tvNoUsersFound;

    static final int CAMERA_PERMISSION_REQUEST_CODE = 90;
    static final int GALLERY_PERMISSION_REQUEST_CODE = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        getPermissions();
    }

    private void initUI() {
        rcvUsersList = findViewById(R.id.rcv_users_list);
        fabAddUser = findViewById(R.id.fab_add_user);
        tvNoUsersFound = findViewById(R.id.tv_no_users_found);

        fabAddUser.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddUserActivity.class)));

        // setting the visibility of recyclerview.
        if (DataHandler.list.size() > 0) {
            rcvUsersList.setVisibility(View.VISIBLE);
            tvNoUsersFound.setVisibility(View.GONE);
        } else {
            rcvUsersList.setVisibility(View.GONE);
            tvNoUsersFound.setVisibility(View.VISIBLE);
        }
        rcvUsersList.setAdapter(new UsersAdapter(getApplicationContext(), DataHandler.list));
        rcvUsersList.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getPermissions() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Gallery Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
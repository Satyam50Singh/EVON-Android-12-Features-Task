package com.example.android12features.view.activities;

import static com.example.android12features.utils.Utils.getEncoded64ImageStringFromBitmap;
import static com.example.android12features.utils.Utils.showErrorToast;
import static com.example.android12features.utils.Utils.showSuccessToast;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.android12features.R;
import com.example.android12features.model.UserModel;
import com.example.android12features.data.DataHandler;
import com.example.android12features.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserActivity extends AppCompatActivity {

    EditText etUsername, etDesignation;
    TextView tvDOJ;
    CircleImageView civUserImage;
    FloatingActionButton fabAddUserImage;
    Button btnCreateUser;

    String selectedUserImage = "", doj = "";

    static final int REQUEST_OPEN_CAMERA_INTENT = 9898;
    static final int REQUEST_OPEN_GALLERY_INTENT = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initUI();

        getLocationPermission();
    }

    private void initUI() {
        etUsername = findViewById(R.id.et_name);
        etDesignation = findViewById(R.id.et_designation);
        tvDOJ = findViewById(R.id.tv_doj);
        civUserImage = findViewById(R.id.civ_user_image);
        fabAddUserImage = findViewById(R.id.fab_add_user);
        btnCreateUser = findViewById(R.id.btn_create_user);

        fabAddUserImage.setOnClickListener(view -> {
            // open camera or pic image from gallery
            openCamOrGallery();
        });

        tvDOJ.setOnClickListener(view -> {
            // get the current date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            // open calendar for picking date
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    doj = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    tvDOJ.setText(doj);
                }
            }, mYear, mMonth, mDay);

            datePickerDialog.show();
        });


        btnCreateUser.setOnClickListener(view -> {
            if (isValidate()) {
                String username = etUsername.getText().toString();
                String designation = etDesignation.getText().toString();

                UserModel userModel = new UserModel((int) Math.random(), username, designation, selectedUserImage, doj);
                showSuccessToast(this, getString(R.string.user_created_successfully));
                DataHandler.addUser(this, userModel);
            }
        });
    }

    // method to get the location permission
    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(AddUserActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddUserActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddUserActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            // Write you code here if permission already given.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.location_permission_granted, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // dialog box for the opening the files or capturing image through camera.
    private void openCamOrGallery() {
        CharSequence[] items = new CharSequence[]{"Take Photo", "Pick Image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.select_option)
                .setItems(items, (dialogInterface, i) -> {
                    if (items[i] == "Take Photo") {
                        // to take picture from camera
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, REQUEST_OPEN_CAMERA_INTENT);
                    } else if (items[i] == "Pick Image") {
                        // to open Gallery
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, REQUEST_OPEN_GALLERY_INTENT);
                    }
                });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_OPEN_CAMERA_INTENT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        civUserImage.setImageBitmap(photo);
                        selectedUserImage = getEncoded64ImageStringFromBitmap(photo);
                        Log.e("TAG", "onActivityResult: " + selectedUserImage);
                    }
                }
                break;
            case REQUEST_OPEN_GALLERY_INTENT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                            civUserImage.setImageBitmap(photo);
                            selectedUserImage = getEncoded64ImageStringFromBitmap(photo);
                            Log.e("TAG", "onActivityResult: " + selectedUserImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    // validation function for control fields.
    private boolean isValidate() {
        if (selectedUserImage.length() == 0) {
            showErrorToast(this, getString(R.string.please_add_profile_picture));
            return false;
        }

        if (etUsername.getText().length() <= 0) {
            showErrorToast(this, getString(R.string.please_enter_username));
            return false;
        }
        if (etDesignation.getText().length() <= 0) {
            Utils.showErrorToast(this, getString(R.string.please_enter_designation));
            return false;
        }
        return true;
    }
}
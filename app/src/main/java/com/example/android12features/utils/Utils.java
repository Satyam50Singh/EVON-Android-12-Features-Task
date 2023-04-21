package com.example.android12features.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.android12features.R;
import com.example.android12features.view.activities.AddUserActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class Utils {

    // convert bitmap to base64 string
    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    // convert string to bitmap
    public static Bitmap decodedStringToBitmap(String strBase64) {
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static void showErrorToast(Context context, String errMsg) {
        FancyToast.makeText(context, errMsg, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
    }
    public static void showSuccessToast(Context context, String successMsg) {
        FancyToast.makeText(context, successMsg, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
    }
}

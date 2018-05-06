package com.robinpape.easymessage;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Robin on 02.05.2018.
 */

public class EasyMessage {


    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;

    private int titleSize;
    private int messageSize;

    private int titleGravity;
    private int messageGravity;

    private boolean closeIfNotAccepted;

    private boolean includeNegativeButton;



    public EasyMessage() {

        // initialize with default values:
        this.titleSize = 20;
        this.messageSize = 16;
        this.positiveButtonText = "OK";
        this.negativeButtonText = "Cancel";
        this.closeIfNotAccepted = false;

    }


    public void setMessageTitle(String title) {
        this.title = title;
    }

    public void setMessageTitle(int stringId, Context context) {
        this.title = context.getResources().getString(stringId);
    }

    public void setMessageText(String message) {
        this.message = message;
    }

    public void setMessageText(int stringId, Context context) {
        this.message = context.getResources().getString(stringId);
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public void setTitleGravity(int gravity) {
        this.titleGravity = gravity;
    }

    public void setMessageGravity(int gravity) {
        this.messageGravity = gravity;
    }

    public void setCloseIfNotAccepted(boolean closeIfNotAccepted){
        this.closeIfNotAccepted = closeIfNotAccepted;
    }

    public void includeNegativeButton(boolean includeNegativeButton){
        this.includeNegativeButton = includeNegativeButton;
    }

    public void setPositiveButtonText(String text){
        this.positiveButtonText = text;
    }
    public void setNegativeButtonText(String text){
        this.negativeButtonText = text;
    }


    public void show(Context context, String id) {

        getNewFragment(context, id);

    }


    public void showOnFirstStart(Context context, String id) {
        // use this method, if you want to show the message only on the first start of the app

        // get the version code of the running app
        int versionCode = -1;

        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }catch (android.content.pm.PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        // get SharedPreference to check if this message has been shown already

        SharedPreferences prefs = context.getSharedPreferences(
                "com.robinpape.easymessage", Context.MODE_PRIVATE);

        int lastVersionCode = prefs.getInt(id + "_shown", -1);
        int acceptedAtVersion = prefs.getInt(id + "_accepted", -1);


        // if message needs to be accepted in order to continue
        if(this.closeIfNotAccepted) {
            // show only, if it hasn't been accepted yet
            if (acceptedAtVersion == -1) {

                getNewFragment(context, id);

                // set this message to "shown", so that it will not be shown again
                setMessageShownAtVersion(context, id, versionCode);

            }
        }else{ // message does not need to be accepted
            // show only, if it hasn't been shown yet
            if (lastVersionCode == -1) {

                getNewFragment(context, id);

                // set this message to "shown", so that it will not be shown again
                setMessageShownAtVersion(context, id, versionCode);

            }
        }
    }


    public void showAfterVersionChange(Context context, String id) {
        // use this method if you want to show the message after every update of your app (after you changed the versionCode of your app)

        // get the version code of the running app

        int versionCode = -1;

        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }catch (android.content.pm.PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        // get SharedPreference to check if this message has been shown after version change

        SharedPreferences prefs = context.getSharedPreferences(
                "com.robinpape.easymessage", Context.MODE_PRIVATE);

        int lastVersionCode = prefs.getInt(id + "_shown", -1);
        int acceptedAtVersion = prefs.getInt(id + "_accepted", -1);


        if(this.closeIfNotAccepted) {
            // show only, if it hasn't been accepted after version change
            if (acceptedAtVersion < versionCode) {

                getNewFragment(context, id);
                setMessageShownAtVersion(context, id, versionCode);


            }

        }else { // message does not need to be accepted
            // show only, if it hasn't been shown after version change
            if (lastVersionCode < versionCode) {

                getNewFragment(context, id);
                setMessageShownAtVersion(context, id, versionCode);

            }
        }
    }


    private void getNewFragment(Context context, String id){

        Activity callingActivity = (Activity) context;

        FragmentTransaction ft = callingActivity.getFragmentManager().beginTransaction();
        Fragment prev = callingActivity.getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = EasyFragment.newInstance(id, title, message, titleSize, messageSize, titleGravity, messageGravity, includeNegativeButton, closeIfNotAccepted, positiveButtonText, negativeButtonText);

        newFragment.show(ft, "dialog");

    }


    private void setMessageShownAtVersion(Context context, String id, int versionCode){
        SharedPreferences prefs = context.getSharedPreferences(
                "com.robinpape.easymessage", Context.MODE_PRIVATE);

        // save id of the message and the version code in SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(id + "_shown", versionCode);
        editor.apply();
    }

}

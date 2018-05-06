package com.robinpape.easymessage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Robin on 02.05.2018.
 */

public class EasyFragment extends DialogFragment {

    private View v;

    private String id;

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

    public EasyFragment(){

    }


    static EasyFragment newInstance(String id, String title, String message, int titleSize, int messageSize, int titleGravity,
                                    int messageGravity, boolean includeNegativeButton, boolean closeIfNotAccepted,
                                    String positiveButtonText, String negativeButtonText){

        EasyFragment fragment = new EasyFragment();

        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        args.putString("message", message);
        args.putInt("titlesize", titleSize);
        args.putInt("messagesize", messageSize);
        args.putInt("titlegravity", titleGravity);
        args.putInt("messagegravity", messageGravity);
        args.putBoolean("includenegativebutton", includeNegativeButton);
        args.putBoolean("closeifnotaccepted", closeIfNotAccepted);
        args.putString("positivebuttontext", positiveButtonText);
        args.putString("negativebuttontext", negativeButtonText);

        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getString("id") != null) {
            id = getArguments().getString("id");
        }

        if (getArguments().getString("title") != null) {
            title = getArguments().getString("title");
        }

        if (getArguments().getString("message") != null) {
            message = getArguments().getString("message");
        }

            titleSize = getArguments().getInt("titlesize");

            messageSize = getArguments().getInt("messagesize");

            titleGravity = getArguments().getInt("titlegravity");

            messageGravity = getArguments().getInt("messagegravity");

            includeNegativeButton = getArguments().getBoolean("includenegativebutton");

            closeIfNotAccepted = getArguments().getBoolean("closeifnotaccepted");

            positiveButtonText = getArguments().getString("positivebuttontext");

            negativeButtonText = getArguments().getString("negativebuttontext");

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        v = getActivity().getLayoutInflater().inflate(R.layout.fragment_message, null);

        final Context ctx = getActivity();

        TextView titleTv = v.findViewById(R.id.fragment_message_title);
        TextView messageTv = v.findViewById(R.id.fragment_message_text);


        titleTv.setText(title);

        messageTv.setText(message);

        titleTv.setTextSize(titleSize);

        messageTv.setTextSize(messageSize);

        titleTv.setGravity(titleGravity);

        messageTv.setGravity(messageGravity);



        builder.setView(v);

        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // set message to "accepted" and save the version code
                int versionCode = -1;
                try {
                    versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
                }catch (android.content.pm.PackageManager.NameNotFoundException e){
                    e.printStackTrace();
                }

                setMessageAcceptedAtVersion(ctx, id, versionCode);

            }
        });

        if(includeNegativeButton) {
            builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // if the message needs to be accepted, but the user refuses to do so: get back to the homescreen
                    if(closeIfNotAccepted) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ctx.startActivity(intent);
                    }

                }
            });
        }


        // prevent the user from canceling the dialog by tapping the back-button
        if(closeIfNotAccepted) {
            this.setCancelable(false);
        }

        return builder.create();
    }


    private void setMessageAcceptedAtVersion(Context context, String id, int versionCode){
        SharedPreferences prefs = context.getSharedPreferences(
                "com.robinpape.easymessage", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(id + "_accepted", versionCode);
        editor.apply();
    }
}

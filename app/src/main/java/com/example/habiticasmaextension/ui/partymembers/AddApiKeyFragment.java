package com.example.habiticasmaextension.ui.partymembers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.wrapper.OnAddApiTokenOk;

public class AddApiKeyFragment extends DialogFragment {

    private String username;
    private String apiKey;
    private OnAddApiTokenOk onAddApiTokenOk;

    public AddApiKeyFragment() {
        // Required empty public constructor
    }

    public AddApiKeyFragment(String username, String apiKey, final OnAddApiTokenOk onAddApiTokenOk) {
        this.username = username;
        this.apiKey = apiKey;
        this.onAddApiTokenOk = onAddApiTokenOk;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.fragment_add_api_key, null);

        EditText addApiKeyText = dialogView.findViewById(R.id.add_api_key_text);
        addApiKeyText.setText(apiKey);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        builder.setTitle("Add API Key for " + username);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onAddApiTokenOk.getValue(addApiKeyText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
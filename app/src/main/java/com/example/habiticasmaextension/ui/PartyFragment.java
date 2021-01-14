package com.example.habiticasmaextension.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.proxy.HabiticaProxyFactory;

import java.io.IOException;
import java.util.List;

public class PartyFragment extends Fragment {

    private User user;
    private MainActivity mainActivity;

    public PartyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        user = mainActivity.user;

        loadPartyDetails();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_party, container, false);

    }

    private void loadPartyDetails() {
        new AsyncTask<String, Void, List<GroupMember>>() {

            @Override
            protected List<GroupMember> doInBackground(String... strings) {
                try {
                    return  HabiticaProxyFactory.createProxy(strings[0], strings[1]).getGroupMembers(user.partyId);
                } catch (IOException e) {
                    Toast.makeText(mainActivity, "Something went wrong", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<GroupMember> members) {
                if (members != null) {

                }
                else {
                    Toast.makeText(mainActivity, "Something went wrong loading party", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(user.userId, user.apiToken);
    }
}
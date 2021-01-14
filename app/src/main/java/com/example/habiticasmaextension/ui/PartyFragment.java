package com.example.habiticasmaextension.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.proxy.HabiticaProxyFactory;
import com.example.habiticasmaextension.ui.partymembers.PartyMembersAdapter;

import java.io.IOException;
import java.util.List;

public class PartyFragment extends Fragment {

    private User user;
    private MainActivity mainActivity;
    private RecyclerView recyclerViewMembers;

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
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<GroupMember> members) {
                if (members != null && members.size() > 0) {
                    // Create recycler view
                    recyclerViewMembers = getView().findViewById(R.id.recyclerView_members);

                    PartyMembersAdapter adapter = new PartyMembersAdapter(getContext(), members);
                    recyclerViewMembers.setAdapter(adapter);
                    recyclerViewMembers.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                else {
                    Toast.makeText(getActivity(), "Something went wrong loading party", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(user.userId, user.apiToken);
    }
}
package com.example.habiticasmaextension.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.proxy.HabiticaProxyFactory;
import com.example.habiticasmaextension.core.services.PreferencesService;
import com.example.habiticasmaextension.core.services.PreferencesServiceFactory;
import com.example.habiticasmaextension.ui.partymembers.PartyMembersAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PartyFragment extends Fragment {

    private User user;
    private MainActivity mainActivity;
    private RecyclerView recyclerViewMembers;
    private Button retrieveKeysButton;
    private PartyMembersAdapter partyMembersAdapter;

    public PartyFragment() {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve api keys from chat button
        retrieveKeysButton = (Button) getView().findViewById(R.id.retrieve_tokens_from_chat_button);

        retrieveKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveApiKeysFromChat();
            }
        });
    }

    private void retrieveApiKeysFromChat() {
        new AsyncTask<String, Void, Map<String, String>>() {

            @Override
            protected Map<String, String> doInBackground(String... strings) {
                try {
                    return  HabiticaProxyFactory.createProxy(strings[0], strings[1]).getApiKeysFromChat(user.partyId, getContext());
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Map<String, String> keys) {
                String myApiToken = PreferencesServiceFactory.createService().get("apiToken", getContext());

                if (keys != null && !keys.isEmpty()) {
                    for(Map.Entry<String, String> entry : keys.entrySet()) {
                        if (!entry.getValue().equals(myApiToken)){
                            PreferencesServiceFactory.createService().put(entry.getKey(), entry.getValue(), getContext());
                        }
                    }
                    Toast.makeText(getActivity(), "API keys loaded successfully", Toast.LENGTH_LONG).show();
                    partyMembersAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "No additional keys found", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(user.userId, user.apiToken);
    }

    private void loadPartyDetails() {
        new AsyncTask<String, Void, List<GroupMember>>() {

            @Override
            protected List<GroupMember> doInBackground(String... strings) {
                try {
                    return  HabiticaProxyFactory.createProxy(strings[0], strings[1]).getGroupMembers(user.partyId, getContext());
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<GroupMember> members) {
                mainActivity.groupMembers = members;

                if (members != null && members.size() > 0) {
                    // Create recycler view
                    recyclerViewMembers = getView().findViewById(R.id.recyclerView_members);

                    partyMembersAdapter = new PartyMembersAdapter(getContext(), members);
                    recyclerViewMembers.setAdapter(partyMembersAdapter);
                    recyclerViewMembers.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                else {
                    Toast.makeText(getActivity(), "Something went wrong loading party", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(user.userId, user.apiToken);
    }
}
package com.example.habiticasmaextension.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.ThinQuest;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.proxy.HabiticaProxyFactory;
import com.example.habiticasmaextension.ui.partymembers.PartyMembersAdapter;
import com.example.habiticasmaextension.ui.unfinishedquests.UnfinishedQuestsAdapter;

import java.io.IOException;
import java.util.List;

public class UnfinishedQuestsFragment extends Fragment {

    private User user;
    private MainActivity mainActivity;
    private List<ThinQuest> allQuests;
    private RecyclerView recyclerViewUnfinishedQuests;
    private UnfinishedQuestsAdapter unfinishedQuestsAdapter;

    public UnfinishedQuestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        user = mainActivity.user;

        getQuests();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unfinished_quests, container, false);
    }

    private void getQuests() {
        new AsyncTask<String, Void, List<ThinQuest>>() {

            @Override
            protected List<ThinQuest> doInBackground(String... strings) {
                try {
                    return  HabiticaProxyFactory.createProxy(strings[0], strings[1]).getAllQuests();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<ThinQuest> quests) {
                if (quests != null && quests.size() > 0) {
                    allQuests = quests;

                    // Create recycler view
                    recyclerViewUnfinishedQuests = getView().findViewById(R.id.recyclerView_unfinished_quests);

                    unfinishedQuestsAdapter = new UnfinishedQuestsAdapter(getContext(), quests, mainActivity.groupMembers);
                    recyclerViewUnfinishedQuests.setAdapter(unfinishedQuestsAdapter);
                    recyclerViewUnfinishedQuests.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                else {
                    Toast.makeText(getActivity(), "Something went wrong loading quests", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(user.userId, user.apiToken);
    }
}
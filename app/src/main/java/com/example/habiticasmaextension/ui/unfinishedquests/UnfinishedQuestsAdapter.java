package com.example.habiticasmaextension.ui.unfinishedquests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.ThinQuest;
import com.example.habiticasmaextension.ui.partymembers.PartyMembersAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class UnfinishedQuestsAdapter extends RecyclerView.Adapter<UnfinishedQuestsAdapter.UnfinishedQuestViewHolder> {
    private Context context;
    private List<ThinQuest> allQuests;
    private List<ThinQuest> unfinishedQuests;
    private List<GroupMember> members;

    public UnfinishedQuestsAdapter(Context ctx, List<ThinQuest> allQuests, List<GroupMember> members) {
        this.context = ctx;
        this.allQuests = allQuests;
        this.members = members;

        getUnfinishedQuests();
    }

    private void getUnfinishedQuests() {
        unfinishedQuests = new LinkedList<ThinQuest>();
        List<String> finishedQuestsStrings = new LinkedList<String>();

        for (GroupMember member : members){
            if (member.questStrings != null) {
                finishedQuestsStrings.addAll(member.questStrings);
            }
        }

        for (ThinQuest quest : allQuests) {
            if (!finishedQuestsStrings.contains(quest.key)) {
                unfinishedQuests.add(quest);
            }
        }

        Collections.sort(unfinishedQuests, new Comparator<ThinQuest>() {
            @Override
            public int compare(ThinQuest o1, ThinQuest o2) {
                return o1.text.toLowerCase().compareTo(o2.text.toLowerCase());
            }
        });
    }

    @NonNull
    @Override
    public UnfinishedQuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.unfinishedquest, parent, false);

        return new UnfinishedQuestsAdapter.UnfinishedQuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnfinishedQuestViewHolder holder, int position) {
        ThinQuest currentQuest = unfinishedQuests.get(position);

        holder.questNameText.setText(currentQuest.text);
    }

    @Override
    public int getItemCount() {
        return unfinishedQuests.size();
    }

    public class UnfinishedQuestViewHolder extends RecyclerView.ViewHolder{
        TextView questNameText;

        public UnfinishedQuestViewHolder(@NonNull View itemView) {
            super(itemView);

            questNameText = itemView.findViewById(R.id.quest_name_text);
        }
    }
}

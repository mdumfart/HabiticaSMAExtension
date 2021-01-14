package com.example.habiticasmaextension.ui.partymembers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.Stats;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.proxy.HabiticaProxyFactory;
import com.example.habiticasmaextension.core.services.PreferencesServiceFactory;
import com.example.habiticasmaextension.core.wrapper.OnAddApiTokenOk;
import com.example.habiticasmaextension.ui.LoginActivity;
import com.example.habiticasmaextension.ui.MainActivity;

import java.io.IOException;
import java.util.List;


public class PartyMembersAdapter extends RecyclerView.Adapter<PartyMembersAdapter.PartyMemberViewHolder> {

    Context context;
    List<GroupMember> members;

    public PartyMembersAdapter(Context ctx, List<GroupMember> members){
        this.context = ctx;
        this.members = members;
    }

    @NonNull
    @Override
    public PartyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.partymember, parent, false);

        return new PartyMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyMemberViewHolder holder, int position) {
        holder.usernameText.setText(members.get(position).username);

        if (members.get(position).getApiKey() != ""){
            holder.addApiKeyButton.setVisibility(View.INVISIBLE);
            holder.checkApiKeyImage.setVisibility(View.VISIBLE);

            if (members.get(position).stats == null){
                getStats(members.get(position));
            }
        }

        setComponentVisibility(holder, members.get(position));

        holder.addApiKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddApiKeyDialog(position, holder);
            }
        });

        holder.checkApiKeyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!members.get(position).userId.equals(PreferencesServiceFactory.createService().get("userId", context))) {
                    showAddApiKeyDialog(position, holder);
                }
                else {
                    Toast.makeText(((MainActivity) context), "Why would you change your own API key...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getStats(GroupMember member) {
        new AsyncTask<String, Void, User>() {

            @Override
            protected User doInBackground(String... strings) {
                try {
                    return  HabiticaProxyFactory.createProxy(strings[0], strings[1]).getUserDetails();
                } catch (IOException e) {
                    Log.e("Error", "Error settings stats");
                }
                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    member.stats = new Stats(user.stats.hp, user.stats.level, user.stats.klass);
                    notifyDataSetChanged();
                }
                else {
                    Log.e("Error", "Error settings stats");
                }
            }
        }.execute(member.userId, member.getApiKey());
    }

    private void setComponentVisibility(PartyMemberViewHolder holder, GroupMember groupMember) {
        if (groupMember.stats != null && groupMember.getApiKey() != ""){
            holder.classText.setText(groupMember.stats.klass);
            holder.levelText.setText("Level " + groupMember.stats.level);

            holder.classText.setVisibility(View.VISIBLE);
            holder.levelText.setVisibility(View.VISIBLE);

            holder.addApiKeyButton.setVisibility(View.INVISIBLE);
            holder.checkApiKeyImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.classText.setVisibility(View.INVISIBLE);
            holder.levelText.setVisibility(View.INVISIBLE);

            holder.addApiKeyButton.setVisibility(View.VISIBLE);
            holder.checkApiKeyImage.setVisibility(View.INVISIBLE);
        }
    }

    private void showAddApiKeyDialog(int position, PartyMemberViewHolder holder) {
        new AddApiKeyFragment(
                members.get(position).username,
                members.get(position).getApiKey(),
                new OnAddApiTokenOk() {
            @Override
            public void getValue(String value) {
                if (!value.isEmpty()){
                    addApiKeyToMember(members.get(position), value, holder);
                }
                else {
                    holder.addApiKeyButton.setVisibility(View.VISIBLE);
                    holder.checkApiKeyImage.setVisibility(View.INVISIBLE);
                    removeApiKeyFromPreferences(members.get(position));
                }
            }
        }).show(((MainActivity) context).getSupportFragmentManager(), "AddApiToken");
    }

    private void addApiKeyToMember(GroupMember member, String apiKey, PartyMemberViewHolder holder) {
        new AsyncTask<String, Void, User>() {

            @Override
            protected User doInBackground(String... strings) {
                try {
                    return HabiticaProxyFactory.createProxy(strings[0], strings[1]).getUserDetails();
                } catch (IOException e) {
                    Toast.makeText(((MainActivity) context), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    member.setApiKey(apiKey);
                    PreferencesServiceFactory.createService().put(member.userId, apiKey, context);
                    holder.addApiKeyButton.setVisibility(View.INVISIBLE);
                    holder.checkApiKeyImage.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(((MainActivity) context), "Invalid API key", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(member.userId, apiKey);
    }

    private void removeApiKeyFromPreferences(GroupMember member){
        PreferencesServiceFactory.createService().remove(member.userId, context);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class PartyMemberViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        TextView classText;
        TextView levelText;
        Button addApiKeyButton;
        ImageView checkApiKeyImage;

        public PartyMemberViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameText = itemView.findViewById(R.id.member_username_text);
            classText = itemView.findViewById(R.id.member_class_text);
            levelText = itemView.findViewById(R.id.member_level_text);
            addApiKeyButton = itemView.findViewById(R.id.member_add_api_key_button);
            checkApiKeyImage = itemView.findViewById(R.id.member_check_api_key_image);
        }
    }
}

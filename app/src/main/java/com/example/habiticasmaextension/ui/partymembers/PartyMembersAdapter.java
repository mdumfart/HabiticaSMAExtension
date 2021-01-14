package com.example.habiticasmaextension.ui.partymembers;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.wrapper.OnAddApiTokenOk;
import com.example.habiticasmaextension.ui.MainActivity;

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

        if (members.get(position).apiKey != ""){
            holder.addApiKeyButton.setVisibility(View.INVISIBLE);
            holder.checkApiKeyImage.setVisibility(View.VISIBLE);
        }

        holder.addApiKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddApiKeyFragment(members.get(position).username, new OnAddApiTokenOk() {
                    @Override
                    public void getValue(String value) {
                        addApiKeyToMember(value);
                    }
                }).show(((MainActivity) context).getSupportFragmentManager(), "AddApiToken");
            }
        });
    }

    private void addApiKeyToMember(String apiKey) {
        Log.e("key", apiKey);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class PartyMemberViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        Button addApiKeyButton;
        ImageView checkApiKeyImage;

        public PartyMemberViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameText = itemView.findViewById(R.id.member_username_text);
            addApiKeyButton = itemView.findViewById(R.id.member_add_api_key_button);
            checkApiKeyImage = itemView.findViewById(R.id.member_check_api_key_image);
        }
    }
}

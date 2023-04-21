package com.example.android12features.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android12features.R;
import com.example.android12features.model.UserModel;
import com.example.android12features.utils.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

// adapter for binding data with the recyclerview
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    Context context;
    ArrayList<UserModel> userLists;

    // constructor
    public UsersAdapter(Context context, ArrayList<UserModel> userLists) {
        this.context = context;
        this.userLists = userLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user = userLists.get(position);
        holder.tvUsername.setText(user.username);
        holder.tvDesignation.setText(user.designation);
        holder.civProfile.setImageBitmap(Utils.decodedStringToBitmap(user.userImage));
        if(user.dateOfJoining.isEmpty()) {
            holder.tvDOJ.append("-- / -- / ----");
        }else {
            holder.tvDOJ.append(user.dateOfJoining);
        }
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvDesignation, tvDOJ;
        CircleImageView civProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tv_username);
            tvDesignation = itemView.findViewById(R.id.tv_designation);
            tvDOJ = itemView.findViewById(R.id.tv_doj);
            civProfile = itemView.findViewById(R.id.civ_profile_image);
        }
    }
}

package com.job4j.githubuserinformation.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposHolder>{

    @NonNull
    @Override
    public ReposHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReposHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ReposHolder extends RecyclerView.ViewHolder {


        public ReposHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

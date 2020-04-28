package com.job4j.githubuserinformation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job4j.githubuserinformation.R;
import com.job4j.githubuserinformation.models.Repository;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposHolder> {

    private List<Repository> repositories;

    public ReposAdapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @NonNull
    @Override
    public ReposHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.repository_info,parent,false);
        return new ReposHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposHolder holder, int position) {
        Repository repository=this.repositories.get(position);
        TextView name = holder.view.findViewById(R.id.repos_name);
        TextView language = holder.view.findViewById(R.id.repos_language);
        name.setText(repository.getName());
        language.setText(repository.getLanguage());
    }

    @Override
    public int getItemCount() {
        return this.repositories.size();
    }

    public class ReposHolder extends RecyclerView.ViewHolder {

        private View view;

        public ReposHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}
package com.job4j.githubuserinformation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.job4j.githubuserinformation.R;
import com.job4j.githubuserinformation.adapters.ReposAdapter;
import com.job4j.githubuserinformation.models.Repository;
import com.job4j.githubuserinformation.retrofit.JsonApi;
import com.job4j.githubuserinformation.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ReposListFragment extends Fragment {

    private RecyclerView recycler;
    private JsonApi jsonApi;
    private String userName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repos_list_activity, container, false);
        recycler = view.findViewById(R.id.repos_list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        Retrofit retrofit = RetrofitClient.getInstance();
        jsonApi = retrofit.create(JsonApi.class);
        Bundle args = getArguments();
        userName = args.getString("userName");
        Repository rep1=new Repository();
        Repository rep2=new Repository();
        rep1.setName("name");
        rep1.setLanguage("lang");
        rep2.setName("name1");
        rep2.setLanguage("lang1");
        Repository rep3=new Repository();
        rep2.setName("name2");
        rep2.setLanguage("lang2");
        List<Repository> rep=new ArrayList<>();
        rep.add(rep1);
        rep.add(rep2);
        rep.add(rep3);
        recycler.setAdapter(new ReposAdapter(rep));
//        updateUI();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateUI() {
        jsonApi.getRepositories(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Repository>>() {
                    @Override
                    public void onSuccess(List<Repository> repositories) {
                        recycler.setAdapter(new ReposAdapter(repositories));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static ReposListFragment of(String userName) {
        ReposListFragment fragment = new ReposListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        fragment.setArguments(bundle);
        return fragment;
    }
}

package com.job4j.githubuserinformation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.job4j.githubuserinformation.R;
import com.job4j.githubuserinformation.models.User;
import com.job4j.githubuserinformation.retrofit.JsonApi;
import com.job4j.githubuserinformation.retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UserSearchFragment extends Fragment {

    private EditText searchText;
    private JsonApi jsonApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_serch_activity, container, false);
        searchText = view.findViewById(R.id.search_text);
        Button searchButton = view.findViewById(R.id.search_button);
        Retrofit retrofit = RetrofitClient.getInstance();
        jsonApi = retrofit.create(JsonApi.class);
        searchButton.setOnClickListener(this::search);
        return view;
    }

    private void search(View view) {
        String inputText=searchText.getText().toString();
       if(!inputText.equals("")){
           jsonApi.getUser(searchText.getText().toString())
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new DisposableSingleObserver<User>() {
                       @Override
                       public void onSuccess(User user) {
                           Toast.makeText(getContext(), user.getPublicRepos().toString(), Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onError(Throwable e) {
                           Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
       }else {
           Toast.makeText(getContext(), "Enter UserName!", Toast.LENGTH_SHORT).show();
       }
    }
}

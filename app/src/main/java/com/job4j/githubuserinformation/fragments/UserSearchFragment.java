package com.job4j.githubuserinformation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.job4j.githubuserinformation.R;
import com.job4j.githubuserinformation.activities.ReposListActivity;
import com.job4j.githubuserinformation.models.User;
import com.job4j.githubuserinformation.retrofit.JsonApi;
import com.job4j.githubuserinformation.retrofit.RetrofitClient;
import com.job4j.githubuserinformation.room.MyDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UserSearchFragment extends Fragment {

    private AutoCompleteTextView searchText;
    private JsonApi jsonApi;
    private MyDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_serch_activity, container, false);
        Button searchButton = view.findViewById(R.id.search_button);

        Retrofit retrofit = RetrofitClient.getInstance();
        jsonApi = retrofit.create(JsonApi.class);

        db = Room.databaseBuilder(getActivity(),
                MyDatabase.class, "populus-database")
                .allowMainThreadQueries()
                .build();

        searchButton.setOnClickListener(this::search);
        List<String> userNames = getUserNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, userNames);
        searchText = view.findViewById(R.id.search_text);
        searchText.setAdapter(adapter);
        return view;
    }


    private void search(View view) {
        String inputText = searchText.getText().toString();
        final String[] userName = new String[1];
        if (!inputText.equals("")) {
            jsonApi.getUser(searchText.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<User>() {
                        @Override
                        public void onSuccess(User user) {
                            Single.fromCallable(() -> db.getUserDao().getUserName(user.getId()))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableSingleObserver<String>() {
                                        @Override
                                        public void onSuccess(String s) {
                                            userName[0] =s;
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }
                                    });
                            if (!user.getLogin().equals(userName[0])) {
                                Completable.fromRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        db.getUserDao().insertUser(user);
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                            }
                            Intent intent = new Intent(getActivity(), ReposListActivity.class);
                            intent.putExtra("userName", user.getLogin());
                            getActivity().startActivity(intent);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Enter UserName!", Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> getUserNames() {
        List<String> userNames = new ArrayList<>();
        db.getUserDao().getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        for (User user : users) {
                            userNames.add(user.getLogin());
                        }
                    }
                });
        return userNames;
    }
}

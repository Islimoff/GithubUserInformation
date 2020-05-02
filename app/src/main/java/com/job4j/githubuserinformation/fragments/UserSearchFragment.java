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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.job4j.githubuserinformation.R;
import com.job4j.githubuserinformation.activities.ReposListActivity;
import com.job4j.githubuserinformation.models.User;
import com.job4j.githubuserinformation.retrofit.JsonApi;
import com.job4j.githubuserinformation.retrofit.RetrofitClient;
import com.job4j.githubuserinformation.room.DatabaseCallback;
import com.job4j.githubuserinformation.room.RoomDb;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UserSearchFragment extends Fragment implements DatabaseCallback {

    private AutoCompleteTextView searchText;
    private JsonApi jsonApi;
    private RoomDb db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_search_activity, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        Button searchButton = view.findViewById(R.id.search_button);
        searchText = view.findViewById(R.id.search_text);
        Retrofit retrofit = RetrofitClient.getInstance();
        jsonApi = retrofit.create(JsonApi.class);
        db = RoomDb.getDb(getActivity().getApplicationContext());
        db.getUsers(this);
        searchButton.setOnClickListener(this::search);
        return view;
    }


    private void search(View view) {
        String inputText = searchText.getText().toString();
        if (!inputText.equals("")) {
            jsonApi.getUser(searchText.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<User>() {
                        @Override
                        public void onSuccess(User user) {
                            db.addUser(user);
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

    @Override
    public void onUsersLoaded(List<User> users) {
        List<String> userNames = new ArrayList<>();
        for (User user : users) {
            userNames.add(user.getLogin());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, userNames);
        searchText.setAdapter(adapter);
    }

}

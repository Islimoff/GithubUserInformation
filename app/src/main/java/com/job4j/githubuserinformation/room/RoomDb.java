package com.job4j.githubuserinformation.room;

import android.content.Context;

import androidx.room.Room;

import com.job4j.githubuserinformation.models.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RoomDb {

    private Store myDatabase;
    private static RoomDb roomDb;

    private RoomDb(Context context) {
        myDatabase = Room.databaseBuilder(context,
                Store.class, "populus-database")
                .allowMainThreadQueries()
                .build();
    }

    public static RoomDb getDb(Context context) {
        if (roomDb == null) {
            roomDb = new RoomDb(context);
        }
        return roomDb;
    }

    public void addUser(User user) {
        Single.fromCallable(() -> myDatabase.getUserDao().getUserName(user.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Completable.fromRunnable(new Runnable() {
                            @Override
                            public void run() {
                                long id = myDatabase.getUserDao().insertUser(user);
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                    }
                });
    }

    public void getUsers(final DatabaseCallback callback) {
        myDatabase.getUserDao().getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableMaybeObserver<List<User>>() {
                    @Override
                    public void onSuccess(List<User> users) {
                        callback.onUsersLoaded(users);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}

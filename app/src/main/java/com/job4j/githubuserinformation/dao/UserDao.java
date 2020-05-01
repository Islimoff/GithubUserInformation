package com.job4j.githubuserinformation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.job4j.githubuserinformation.models.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user")
    Flowable<List<User>> getAllUsers();

    @Query("SELECT login FROM user WHERE id = :id")
    String getUserName(int id);
}

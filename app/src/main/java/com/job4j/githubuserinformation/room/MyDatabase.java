package com.job4j.githubuserinformation.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.job4j.githubuserinformation.dao.UserDao;
import com.job4j.githubuserinformation.models.User;

    @Database(entities = {User.class}, version = 1)
    public abstract class MyDatabase extends RoomDatabase {

        public abstract UserDao getUserDao();
    }

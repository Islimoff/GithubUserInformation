package com.job4j.githubuserinformation.room;

import com.job4j.githubuserinformation.models.User;

import java.util.List;

public interface DatabaseCallback {

    void onUsersLoaded(List<User> users);
}

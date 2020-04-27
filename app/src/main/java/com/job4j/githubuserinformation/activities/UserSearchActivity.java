package com.job4j.githubuserinformation.activities;

import androidx.fragment.app.Fragment;

import com.job4j.githubuserinformation.fragments.UserSearchFragment;

public class UserSearchActivity extends BaseActivity {
    @Override
    public Fragment loadFragment() {
        return new UserSearchFragment();
    }
}

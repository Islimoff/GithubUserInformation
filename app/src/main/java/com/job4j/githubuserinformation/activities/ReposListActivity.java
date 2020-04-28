package com.job4j.githubuserinformation.activities;

import androidx.fragment.app.Fragment;

import com.job4j.githubuserinformation.fragments.ReposListFragment;

public class ReposListActivity extends BaseActivity {
    @Override
    public Fragment loadFragment() {
        return ReposListFragment.of(getIntent().getStringExtra("userName"));
    }
}

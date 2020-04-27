package com.job4j.githubuserinformation.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.job4j.githubuserinformation.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.content) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.content, loadFragment())
                    .commit();
        }
    }

    public abstract Fragment loadFragment();
}


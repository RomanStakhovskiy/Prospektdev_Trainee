package com.prospektdev.trainee_stakhovskiy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TraineeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ItemFragment();
    }
}

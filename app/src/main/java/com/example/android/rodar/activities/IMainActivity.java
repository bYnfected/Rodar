package com.example.android.rodar.activities;

import android.os.Bundle;

public interface IMainActivity {

    void setToolbarTitle(String fragmentTag);

    void inflateFragment(String fragment, Bundle bundle);
}
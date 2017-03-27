package com.optoma.launcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;
import android.util.Log;

public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        setupUIElements();
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.videos_by_google_banner));

    }
}
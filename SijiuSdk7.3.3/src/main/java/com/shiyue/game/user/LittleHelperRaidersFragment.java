package com.shiyue.game.user;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiyue.game.config.AppConfig;

public class LittleHelperRaidersFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(AppConfig.resourceId(getActivity(),"little_helper_raiders_fragment","layout"),container,false);
        return view;
    }
}

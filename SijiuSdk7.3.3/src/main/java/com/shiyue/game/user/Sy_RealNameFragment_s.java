package com.shiyue.game.user;

import android.app.Fragment;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiyue.game.config.AppConfig;


public class Sy_RealNameFragment_s extends Fragment {
    private TextView showname;
    private TextView showid;
    private View view;
    private Handler handler;
    private String name= "";
    private String id = "";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(AppConfig.resourceId(getActivity(),"sy_real_name_fragment_s","layout"),container,false);



        Sy_RealNameActivity activity = (Sy_RealNameActivity) getActivity();
        handler = activity.real_handler;
        name = activity.getRealname();
        id = activity.getRealid();
        Log.d("sssssssss",name);
        Log.d("bbbbbbbbb",id);
        init();
        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
        showname = (TextView) view.findViewById(AppConfig.resourceId(getActivity(),"show_realname","id"));
        showid = (TextView) view.findViewById(AppConfig.resourceId(getActivity(),"show_realnumber","id"));
        showname.setText(name);
        showid.setText(id);

    }


}

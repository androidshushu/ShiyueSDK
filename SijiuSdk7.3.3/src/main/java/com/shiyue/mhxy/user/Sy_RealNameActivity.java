package com.shiyue.mhxy.user;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shiyue.mhxy.R;
import com.shiyue.mhxy.common.BaseActivity;
import com.shiyue.mhxy.config.AppConfig;

import java.util.HashMap;

public class Sy_RealNameActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ImageView close;
    private FrameLayout real_fragment;
    private Sy_RealNameFragment_s sy_realNameFragment_s;
    private Sy_ReadNameFragment_f sy_readNameFragment_f;
    private String account = "";



    private String realname = "";
    private String realid = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(AppConfig.resourceId(this,"sy_real_name_activity","layout"));
        init();
    }

    private void init() {
        real_fragment = (FrameLayout) findViewById(AppConfig.resourceId(this,"realname_fragment","id"));
        back = (ImageView) findViewById(AppConfig.resourceId(this,"realname_back1","id"));
        close = (ImageView) findViewById(AppConfig.resourceId(this,"realname_back2","id"));
        back.setOnClickListener(this);
        close.setOnClickListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        write_realname();
    }

    private void write_realname() {
        FragmentManager fm = getFragmentManager();
        //开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        sy_readNameFragment_f = new Sy_ReadNameFragment_f();
        Bundle bundle = new Bundle();
        bundle.putString("account_data",account);
        sy_readNameFragment_f.setArguments(bundle);
        transaction.replace(AppConfig.resourceId(this,"realname_fragment","id"),sy_readNameFragment_f);
        transaction.commit();
    }

    private void showPersonlDataFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        sy_realNameFragment_s = new Sy_RealNameFragment_s();
        Bundle bundle = new Bundle();
        bundle.putString("realname",realname);
        bundle.putString("realid",realid);
        sy_realNameFragment_s.setArguments(bundle);
        transaction.replace(AppConfig.resourceId(this,"realname_fragment","id"),sy_realNameFragment_s);
        transaction.commit();
    }
    // TODO: 2017/8/16 这里留着一个问题，就是回退键的问题，要先判断当前是处于哪个Activity中 ，留着后面来改
    @Override
    public void onClick(View view) {
        if (view.getId()==AppConfig.resourceId(this,"realname_back1","id")){
            finish();
        }else if (view.getId()==AppConfig.resourceId(this,"realname_back2","id")){
            setDefaultFragment();
        }

    }
    public Handler real_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==AppConfig.FLAG_REALNAME_OK){
                HashMap hashMap = (HashMap) msg.obj;
                realname = (String) hashMap.get("realname");
                realid = (String) hashMap.get("realid");
                showPersonlDataFragment();

            }
        }
    };

    public String getRealname() {
        return realname;
    }

    public String getRealid() {
        return realid;
    }
}

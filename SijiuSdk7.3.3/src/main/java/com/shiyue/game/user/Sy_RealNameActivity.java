package com.shiyue.game.user;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shiyue.game.common.BaseActivity;
import com.shiyue.game.common.Phonedialog;
import com.shiyue.game.config.AppConfig;

import java.util.HashMap;

public class Sy_RealNameActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ImageView close;
    private FrameLayout real_fragment;
    private Sy_RealNameFragment_s sy_realNameFragment_s;
    private Sy_ReadNameFragment_f sy_readNameFragment_f;
    private String account = "";
    private Phonedialog pdialog;
    private String tips2 = "实名制认证未完成，请继续填写或退出登录。";



    private String realname = "";
    private String realid = "";
//    private Handler loginhandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(AppConfig.resourceId(this,"sy_real_name_activity","layout"));
//        LoginActivity loginActivity =
//        loginhandler = loginActivity.mHandler;

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
        //回传到Activity没问题
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
            if (AppConfig.AUTH_NAME_STATUS.equals("hard")&&AppConfig.EXTRA_INFO.equals("")){
            }else {
                finish();
            }
        }else if (view.getId()==AppConfig.resourceId(this,"realname_back2","id")){
            if (AppConfig.AUTH_NAME_STATUS.equals("hard")&&AppConfig.EXTRA_INFO.equals("")){

                pdialog = new Phonedialog(this, AppConfig.resourceId(this,
                        "Sj_Transparent", "style"), AppConfig.resourceId(this,
                        "sy_logindialog", "layout"), tips2, "hard", new Phonedialog.Phonelistener() {

                    @Override
                    public void onClick(View view, String text, String from) {
                        if (view.getId() == getResources().getIdentifier(
                                "dialog_phone", "id", getPackageName())) {

                            //确定，然后继续填写！！
                            pdialog.dismiss();
                            setDefaultFragment();

                        } else if (view.getId() == getResources().getIdentifier(
                                "dialog_cancel", "id", getPackageName())) {

                            //关闭然后直接退出登录,
                            //// TODO: 2017/8/17 这里要退出登录 ，还不知道怎么退

                            try{
                                Activity a = (Activity)lista.get(lista.size()-1);
                                a.finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            showMsg("已退出登录！");
                            finish();
                            pdialog.dismiss();


                        }
                    }
                });

//                        pdialog.setCancelable(false);

//                        pdialog.setText("是否将账号信息截图保存到系统相册");

                pdialog.show();
                //下面这个文字不是最终控制显示的，以
            }else {
                finish();
            }

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

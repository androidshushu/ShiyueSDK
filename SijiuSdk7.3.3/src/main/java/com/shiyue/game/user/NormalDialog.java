package com.shiyue.game.user;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shiyue.game.config.AppConfig;

/**
 * Created by Administrator on 2017/8/17.
 */

public class NormalDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int mviews;
    private Button cancleButton,confimButton;
    private Normallistener listener;
    private String text = "",from = "";
    private TextView dialogtext;
    private boolean flag = true;
    private int q = 1;
    public NormalDialog(Context mcontext,int theme, int view, String text,
                        String from, Normallistener normallistener ){
        super(mcontext,theme);
        this.context = mcontext;
        this.mviews = view;
        this.listener = normallistener;
        this.text = text;
        this.from = from;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(mviews);

        cancleButton = (Button) findViewById(AppConfig.resourceId(context,
                "dialog_cancel_btn", "id"));
        confimButton = (Button) findViewById(AppConfig.resourceId(context,
                "dialog_confim_btn", "id"));
        dialogtext = (TextView) findViewById(AppConfig.resourceId(context,
                "tv_showtext", "id"));
        if ("normal".equals(from)){
            cancleButton.setText("取消");
            confimButton.setText("确认");
        }else if ("hard".equals(from)){
            cancleButton.setText("退出登录");
            confimButton.setText("继续填写");
        }

        cancleButton.setOnClickListener(this);
        confimButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, text, from);
    }

    public interface Normallistener {
        public void onClick(View view, String text, String from);

    }

}

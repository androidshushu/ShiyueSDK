package com.shiyue.game.sdk;

import android.content.Context;
import android.util.Log;

import com.shiyue.game.common.DevListener;
import com.shiyue.game.config.AppConfig;
import com.shiyue.game.http.ApiAsyncTask;
import com.shiyue.game.http.ApiRequestListener;
import com.shiyue.game.http.SiJiuApiTask;
import com.shiyue.game.http.SiJiuSdk;
import com.shiyue.game.user.DevActionMessage;

/**
 * Created by Administrator on 2017/8/21.
 */

public class DevAction {

    private DevListener listener;
    private Context context;
    private ApiAsyncTask devacTask;
    private String ver_id;
    private String ad_id;
    private String imit;
    private String model;

    public DevAction(Context context,String ver_id,String ad_id,String imit,String model,
                     DevListener listener){
        Log.d("hereIsInit",model);
        this.context = context;
        this.ver_id = ver_id;
        this.ad_id = ad_id;
        this.imit = imit;
        this.model = model;
        initHttps();

    }

    /**
     * 初始化接口，这个是http请求的
     */

    private void initHttps() {
        Log.d("hereIsHttpInit",AppConfig.model);
        devacTask = SiJiuSDK.get().devAction(context, AppConfig.appId, AppConfig.appKey, ver_id, ad_id,
                imit, AppConfig.model, new ApiRequestListener() {
            @Override
            public void onSuccess(Object obj) {
                Log.d("DevActSuccess",obj+"");
                DevActionMessage result = (DevActionMessage) obj;
                Boolean results = result.getResult();
                String message = result.getMessage();
                Log.d("DevAcmessage：",message);
                if (results){
                    Log.d("DevActionsucess：",message);
                }else {
                    Log.d("DevActionFail：",message);
                }

            }

            @Override
            public void onError(int statusCode) {
                Log.d("DevActionerror","请检查网络连接。");

            }
        });
    }
}

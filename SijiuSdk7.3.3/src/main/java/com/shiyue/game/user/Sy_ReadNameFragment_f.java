package com.shiyue.game.user;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shiyue.game.common.Phonedialog;
import com.shiyue.game.config.AppConfig;
import com.shiyue.game.http.ApiAsyncTask;
import com.shiyue.game.http.ApiRequestListener;
import com.shiyue.game.sdk.SiJiuSDK;

import java.util.HashMap;

public class Sy_ReadNameFragment_f extends Fragment implements View.OnClickListener{
    private EditText edit_realname;
    private EditText edit_realid;
    private Button real_save;

    private View view;
    private boolean isrealname;
    private ApiAsyncTask realNameTask;
    private RealNameConfimMessage realnameMessage;
    private Handler handler;
    private Phonedialog pdialog;

    private String appKey = "";
    private String verId = "";
    private int appId;


    private String account = "";
    private String realname="";
    private String realid="";
    private String tips1 = "实名制信息一经填写,不可修改，请确保信息无误。";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        account = bundle.getString("account_data");


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(AppConfig.resourceId(getActivity(),"sy_real_name_fragment_f","layout"),container,false);
        Sy_RealNameActivity activity = (Sy_RealNameActivity) getActivity();
        handler = activity.real_handler;
        init();

        appId = AppConfig.appId;
        appKey = AppConfig.appKey;
        verId=AppConfig.ver_id;

        return view;
    }

    private void init() {
        edit_realname = (EditText) view.findViewById(AppConfig.resourceId(getActivity(),"edit_real_name","id"));
        edit_realid = (EditText) view.findViewById(AppConfig.resourceId(getActivity(),"real_person_number","id"));
        real_save = (Button) view.findViewById(AppConfig.resourceId(getActivity(),"realname_confirm","id"));
        real_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() ==AppConfig.resourceId(getActivity(),"realname_confirm","id")){
            if (edit_realname.getText().toString().equals("")){
                showMsg("真实姓名能为空！");
            }else if (edit_realid.getText().toString().equals("")){
                showMsg("身份证号码不能为空！");
            }else if (isChaineseName(edit_realname.getText().toString())){
                if (isPersonnalId(edit_realid.getText().toString())){
                    // TODO: 2017/8/17 自定义一个普通的弹出窗口的

                        pdialog = new Phonedialog(getActivity(), AppConfig.resourceId(getActivity(),
                                "Sj_Transparent", "style"), AppConfig.resourceId(getActivity(),
                                "sy_logindialog", "layout"), tips1, "normal", new Phonedialog.Phonelistener() {

                            @Override
                            public void onClick(View view, String text, String from) {
                                if (view.getId() == getResources().getIdentifier(
                                        "dialog_phone", "id", getActivity().getPackageName())) {
                                    realnameConfig();
                                    pdialog.dismiss();

                                } else if (view.getId() == getResources().getIdentifier(
                                        "dialog_cancel", "id", getActivity().getPackageName())) {
                                    pdialog.dismiss();
//                                    getActivity().finish();
                                }
                            }
                        });

//                        pdialog.setCancelable(false);

//                        pdialog.setText("是否将账号信息截图保存到系统相册");

                        pdialog.show();
                        //下面这个文字不是最终控制显示的，以线程里面的为准

                }else {
                    showMsg("身份证号码格式有错！");
                }
            }else {
                showMsg("姓名的格式有错！");
            }
        }

    }

    /**
     *
     */

    private void realnameConfig() {
        realname = edit_realname.getText().toString();
        realid = edit_realid.getText().toString();

        realNameTask = SiJiuSDK.get().startrealname(getActivity(), appId, appKey, verId, realname, realid, new ApiRequestListener() {
            @Override
            public void onSuccess(Object obj) {
                Log.d("realNameId_success_obj=",obj+"");
                
                if (obj!=null){
                    realnameMessage = (RealNameConfimMessage)obj;
                    boolean result = realnameMessage.getResult();
                    String message = realnameMessage.getMessage();
                    if (result){
                        sendData(AppConfig.FLAG_REALNAME_SUCCESS,"实名制认证成功",realnameHandler);
                    }else {
                        sendData(AppConfig.FLAG_REALNAME_FAIL,"实名制认证失败",realnameHandler);
                    }
                }else {
                    sendData(AppConfig.FLAG_REALNAME_FAILS,"获取数据失败",realnameHandler);
                }
                
            }

            @Override
            public void onError(int statusCode) {
                sendData(AppConfig.FLAG_REALNAME_ERROR,"网络连接失败，请检查您的网络连接",realnameHandler);
            }
        });


    }

    /**
     * show message
     * @param msg
     */
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 正则表达式判断姓名是否符合要求,一般人名字就是2到8个汉字，新疆人的就有‘•’或是‘·’就可以到15个汉字
     */
    public boolean  isChaineseName(String names){
//        String testName = "/^([\\u4e00-\\u9fa5]){2,7}$/";
        String testName ="";
        if (names.contains("•")||names.contains("·")){
            testName ="^[\u4E00-\u9FA5]{2,15}$";//匹配2到15个汉字
        }else {
            testName ="^[\u4E00-\u9FA5]{2,8}$";//匹配2到8个汉字
        }

        if (TextUtils.isEmpty(names)){
            return false;
        }else {
            return names.matches(testName);
        }
    }
    /**
     * 正则表达式判断身份证号是否符合要求
     */
    public boolean  isPersonnalId(String names){
//        String testName = "/^([\\u4e00-\\u9fa5]){2,7}$/";
        String testName ="^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";//匹配身份证号码
        if (TextUtils.isEmpty(names)){
            return false;
        }else {
            return names.matches(testName);
        }
    }
    /**
     * 接口返回数据处理
     */
    public void sendData(int num, Object data, Handler callback) {
        Message msg = callback.obtainMessage();
        msg.what = num;
        msg.obj = data;
        msg.sendToTarget();
    }
    private Handler realnameHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AppConfig.FLAG_REALNAME_SUCCESS:
                    //// TODO: 2017/8/16 实名认证成功后应该要发送消息回上一层的Activity里面提醒更改信息。
                    Message ms = handler.obtainMessage();
                    ms.what = AppConfig.FLAG_REALNAME_OK;
                    HashMap hashMap=new HashMap();
//                    hashMap.put("phone",phonenum); sendData(AppConfig.FLAG_REALNAME_OK,"实名制认证成功",handler);
                    hashMap.put("realname",realname);
                    hashMap.put("realid",realid);
                    ms.obj = hashMap;
                    AppConfig.EXTRA_INFO = realid+realname;
                    handler.sendMessage(ms);
                    Log.d("user_pernal_message",ms.toString());
                    break;
                case AppConfig.FLAG_REALNAME_FAIL:
                    showMsg("实名制认证失败");
                    break;
                case AppConfig.FLAG_REALNAME_FAILS:
                    showMsg("获取数据失败");
                    break;
                case AppConfig.FLAG_REALNAME_ERROR:
                    showMsg("网络连接失败，请检查您的网络连接");
                    break;
            }
        }
    };

}

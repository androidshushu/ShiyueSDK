package com.shiyue.game.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.shiyue.game.R;
import com.shiyue.game.common.ApiListenerInfo;
import com.shiyue.game.common.ExitListener;
import com.shiyue.game.common.InitListener;
import com.shiyue.game.common.LoginMessageInfo;
import com.shiyue.game.common.Syyx;
import com.shiyue.game.common.UserApiListenerInfo;
import com.shiyue.game.pay.SjyxPaymentInfo;
import com.shiyue.game.user.LittleHelper;
import com.shiyue.game.user.LoginInfo;

import java.util.HashMap;
import java.util.Properties;

public class MainActivity extends Activity {
	private Button button1, button4;
//	private Button button6;
	private Button button3;
	private Button button2;
	private Button button5;
	private Button button404;

//	杰测试模式参数
//	public static int syAppId = 101;
//	public static String syAppKey = "idhw19c71bc05m1";
//	public static String syVer_id="101";
//	测试服参数
	public static int syAppId = 104;
	public static String syAppKey = "TLWohrXiC8gW3hAFRB5bUxmf";
	public static String syVer_id="101";
//	正式模式参数
	private int syAppId1 =110;
	private String syAppKey1 = "OSrno5iiEx2qopCw3FluSnSR";
	private String syVer_id1 = "1001";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
	
		initView();
//		Syyx.startWelcomanie(this);
		Syyx.applicationInit(this);
		Syyx.setUserListener(new UserApiListenerInfo() {
			@Override
			public void onLogout(Object obj) {
				// TODO Auto-generated method stub
				super.onLogout(obj);

				LoginInfo loginInfo = new LoginInfo();
				try{
					Properties properties = new Properties();
					properties.load(getBaseContext().getAssets().open("sijiu.properties"));
					loginInfo.setFlags((properties.getProperty("flags")));
				}catch (Exception e){
					e.printStackTrace();
				}

				if (loginInfo.getFlags().equals("test")){
                    loginInfo.setAppid(syAppId);
                    loginInfo.setAppkey(syAppKey);
                    loginInfo.setAgent( syVer_id);
                }else {
                    loginInfo.setAppid(syAppId1);
                    loginInfo.setAppkey(syAppKey1);
                    loginInfo.setAgent( syVer_id1);
                }

//				loginInfo.setServer_id("");
				loginInfo.setOritation("landscape");// landscape
				Syyx.login(MainActivity.this, loginInfo, new ApiListenerInfo() {
					@Override
					public void onSuccess(Object obj) {
						if (obj != null) {
							LoginMessageInfo data = (LoginMessageInfo) obj;
							String result = data.getResult();
							String msg = data.getMessage();
							String userName = data.getUserName();
							String uid = data.getUid();
							String timeStamp = data.getTimestamp();
//							String sign = data.getSign();
							String token = data.getToken();

							Log.i("shiyues", "登录结果" + "result:" + result + "|msg:"
									+ msg + "|username:" + userName + "|uid:"
									+ uid + "|timeStamp:" + timeStamp
									 + "|token" + token);
							
						}
					}
				});
			}
		});
	}

	private void initView() {
		button1 = (Button) this.findViewById(R.id.button1);
//		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) this.findViewById(R.id.button3);
		button4 = (Button) this.findViewById(R.id.button4);
		button5 = (Button) this.findViewById(R.id.button5);
//		button6 = (Button) this.findViewById(R.id.button6);
//		button7 = (Button) this.findViewById(R.id.button7);
//		button8 = (Button) this.findViewById(R.id.button8);
		button404 = (Button) this.findViewById(R.id.button404);
		button1.setOnClickListener(oc);
		button4.setOnClickListener(oc);
//		button2.setOnClickListener(oc);
		button3.setOnClickListener(oc);
		button5.setOnClickListener(oc);
//		button6.setOnClickListener(oc);
//		button7.setOnClickListener(oc);
//		button8.setOnClickListener(oc);
		button404.setOnClickListener(oc);
	}

	OnClickListener oc = new OnClickListener() {
		// 26 9ff6a5224ec3412331cd512f37e36095
		@Override
		public void onClick(View v) {
			if (v.equals(button1)) {


//
//				//// TODO: 2017/8/21 在这里回调一些激活设备的方法
//				Syyx.devactinterface(MainActivity.this, syAppId, syAppKey, syVer_id, true, new DevListener() {
//					@Override
//					public void Success(String msg) {
//						Log.d("devactinterfacesuccess",msg.toString()+"");
//					}
//
//					@Override
//					public void fail(String msg) {
//						Log.d("devactinterfaceFail",msg.toString()+"");
//
//					}
//				});

                // TODO Auto-generated method stub 1
				Syyx.initInterface(MainActivity.this, syAppId, syAppKey, syVer_id, true, new InitListener() {
					@Override
					public void Success(String msg) {

						Log.d("initSuccess",msg.toString());
					}

					@Override
					public void fail(String msg) {

						Log.d("initFail",msg.toString());

					}
				});


			} else if (v.equals(button3)) {
				LoginInfo loginInfo = new LoginInfo();
				loginInfo.setAppid(syAppId);
				loginInfo.setAppkey(syAppKey);
				loginInfo.setAgent(syVer_id);
				loginInfo.setServer_id("");
				loginInfo.setOritation("landscape");// landscape
				Syyx.login(MainActivity.this, loginInfo, new ApiListenerInfo() {

					@Override
					public void onSuccess(Object obj) {
						if (obj != null) {
							LoginMessageInfo data = (LoginMessageInfo) obj;
							String result = data.getResult();
							String msg = data.getMessage();
							String userName = data.getUserName();
							String uid = data.getUid();
							String timeStamp = data.getTimestamp();
//							String sign = data.getSign();
							String token = data.getToken();
							Log.i("shiyue", "登录结果" + "result:" + result + "|msg:"
									+ msg + "|username:" + userName + "|uid:"
									+ uid + "|timeStamp:" + timeStamp
									+ "|sign:" + "|token" + token);



							//角色数据上报
							HashMap<String, Object> params = new HashMap<String, Object>();
							params.put("role_name","张3");
							params.put("project_id","1");
							params.put("role_id","4567");
							params.put("account","461235809");
							params.put("srv_id","3");
							params.put("srv_name","审核服");
							Syyx.setExtData(MainActivity.this,params,301);
							HashMap<String, Object> params1 = new HashMap<String, Object>();
							params.put("role_name","张3");
							params.put("project_id","1");
							params.put("role_id","4567");
							params.put("account","461235809");
							params.put("srv_id","3");
							params.put("srv_name","审核服");
							params.put("role_level","3");
							Syyx.setExtData(MainActivity.this,params,302);
							HashMap<String, Object> params2 = new HashMap<String, Object>();
							params.put("role_id","4567");
							params.put("srv_id","3");
							params.put("to_lev","4");
							Syyx.setExtData(MainActivity.this,params,303);
						}

					}

				});
			} else if (v.equals(button2)) {
				//	new Noticedata(MainActivity.this);

				/**
				 * * @param context 上下文
				 *
				 * @param Agent
				 *            渠道号
				 * @param scene_Id
				 * 场景
				 *  分别为进入服务器(enterServer)、玩家创建用户角色(createRole)、玩家升级(levelUp)
				 * @param roleId
				 *            角色id
				 * @param roleName
				 *            角色名
				 * @param roleLevel
				 *            角色等级
				 * @param zoneId
				 *            当前登录的游戏区服id
				 * @param zoneName
				 *            当前游戏区服名称
				 * @param balance
				 *            游戏币余额
				 * @param Vip
				 *            当前用户vip等级
				 * @param partyName
				 *            当前用户所属帮派
				 * */

//				Syyx.setExtData(MainActivity.this, syVer_id, "enterServer", "123",
//						"七仔", "80", "1", "55区", "25", "vip9",);

			} else if (v.equals(button4)) {
				SjyxPaymentInfo payInfo = new SjyxPaymentInfo();

				payInfo.setAppId(syAppId);
				payInfo.setAppKey(syAppKey);
				payInfo.setExtraInfo("66$$44|ffff");// 额外信息
				payInfo.setServerId("1");
				payInfo.setAmount("0.1");
				payInfo.setSubject("1");
				payInfo.setNotifyUrl("http://s1.sygame.xy.shiyuegame.com/api/pf/sygame/callback.php");
				payInfo.setProductId("com.leniu.xlqy.100");
				payInfo.setAccountId("1");
				payInfo.setRoleid("1");
//				payInfo.setAgent( syVer_id);
//
//				payInfo.setRolename("四九游");
//				payInfo.setLevel("26");
//				payInfo.setRoleid("123");//角色id
//				payInfo.setGameuid("456");//游戏用户id
//				payInfo.setProductname("元宝");//支付商品名称
//
//				payInfo.setBillNo("");//游戏订单号
//
//				payInfo.setUid(""); // 如果为""，说明是接入了我们的登录sdk，如果要只接入充值sdk，则需要传入对方平台的username

				Syyx.payment(MainActivity.this, payInfo, new ApiListenerInfo() {

					@Override
					public void onSuccess(Object obj) {
						if (obj != null) {
							// LoginMessageInfo login=(LoginMessageInfo) obj;
							Log.i("kk", "充值成功" + obj.toString());
						}

					}

				});
			}
//			else if (v.equals(button7)) {
//				SjyxPaymentInfo payInfo = new SjyxPaymentInfo();
//
//				payInfo.setAppId(syAppId);
//				payInfo.setAppKey(syAppKey);
//				payInfo.setExtraInfo("666");// 额外信息
//				payInfo.setServerId("1");
//				payInfo.setAmount("0.1");
//				payInfo.setSubject("1");
//				payInfo.setNotifyUrl("http://s1.sygame.xy.shiyuegame.com/api/pf/sygame/callback.php");
//				payInfo.setProductId("com.leniu.xlqy.100");
//				payInfo.setAccountId("1");
//				payInfo.setRoleid("1");
//
//				Syyx.payment(MainActivity.this, payInfo, new ApiListenerInfo() {
//					@Override
//					public void onSuccess(Object obj) {
//						if (obj != null) {
//							// LoginMessageInfo login=(LoginMessageInfo) obj;
//							Log.i("kk", "充值成功" + obj.toString());
//						}
//
//					}
//
//				});
//			}
//			else if (v.equals(button8)) {
//				SjyxPaymentInfo payInfo = new SjyxPaymentInfo();
//				payInfo.setAppId(syAppId);
//				payInfo.setAppKey(syAppKey);
//				payInfo.setExtraInfo("666");// 额外信息
//				payInfo.setServerId("1");
//				payInfo.setAmount("0.1");
//				payInfo.setSubject("1");
//				payInfo.setNotifyUrl("http://s1.sygame.xy.shiyuegame.com/api/pf/sygame/callback.php");
//				payInfo.setProductId("com.leniu.xlqy.100");
//				payInfo.setAccountId("1");
//				payInfo.setRoleid("1");
//
//				Syyx.payment(MainActivity.this, payInfo, new ApiListenerInfo() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						if (obj != null) {
//							// LoginMessageInfo login=(LoginMessageInfo) obj;
//							Log.i("kk", "充值成功" + obj.toString());
//						}
//
//					}
//
//				});

			 else if (v.equals(button5)) {
				//if ("success".equals(Syyx.exit(MainActivity.this))) {
				//System.exit(0);
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//
//					}
//				});
				Syyx.exit(MainActivity.this, new ExitListener() {

					@Override
					public void fail(String msg) {
						// TODO Auto-generated method stub

					}

					@Override
					public void ExitSuccess(String msg) {
						// TODO Auto-generated method stub
						if ("exit".equals(msg)) {
							System.exit(0);
						}
					}
				});
				//}
			}else if (v.equals(button404)){
                //跳转账号小助手页面
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LittleHelper.class);
                MainActivity.this.startActivity(intent);

			}
//			} else if (v.equals(button6)) {
//				SjyxPaymentInfo payInfo = new SjyxPaymentInfo();
//				payInfo.setAppId(syAppId);
//				payInfo.setAppKey(syAppKey);
//				payInfo.setExtraInfo("666");// 额外信息
//				payInfo.setServerId("1");
//				payInfo.setAmount("0.1");
//				payInfo.setSubject("1");
//				payInfo.setNotifyUrl("http://s1.sygame.xy.shiyuegame.com/api/pf/sygame/callback.php");
//				payInfo.setProductId("com.leniu.xlqy.100");
//				payInfo.setAccountId("1");
//				payInfo.setRoleid("1");
//				Syyx.payment(MainActivity.this, payInfo, new ApiListenerInfo() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						if (obj != null) {
//							// LoginMessageInfo login=(LoginMessageInfo) obj;
//							Log.i("kk", "充值成功" + obj.toString());
//						}
//
//					}
//
//				});
//
//			}

		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Syyx.exit(MainActivity.this, new ExitListener() {

						@Override
						public void fail(String msg) {
							// TODO Auto-generated method stub
							Log.i("kk", "-----"+ msg);
						}

						@Override
						public void ExitSuccess(String msg) {
							// TODO Auto-generated method stub
							Log.i("kk",msg);
							if("exit".equals(msg)){
								System.exit(0);
							}
						}
					});
				}
			});

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Syyx.onRestart(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Syyx.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Syyx.onPause(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Syyx.onstop(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Syyx.onDestroy(this);
		Syyx.applicationDestroy(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Syyx.onNewIntent(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Syyx.onActivityResult(MainActivity.this, requestCode, resultCode, data);
	}
}

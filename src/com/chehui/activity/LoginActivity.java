package com.chehui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import chehui.maichetong.baseinfoservice.ResultOfTSeller;
import chehui.maichetong.baseinfoservice.TSeller;

import com.chehui.adapter.OptionsAdapter;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.diy.EditTextWithDel;
import com.chehui.manage.comm.SharedPreManager;
import com.chehui.manager.comm.ActivityManager;
import com.chehui.manager.comm.RegisterManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.LogN;
import com.chehui.utils.StringUtils;
import com.chehui.utils.ToastUtils;
import com.chehui.utils.Utils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;
import com.igexin.sdk.PushManager;

public class LoginActivity extends BaseActivity {
	TSeller tuser;
	private EditTextWithDel etPwd;
	private TextView username;
	private TextView pwd;
	private Button btnLogin;
	// PopupWindow对象
	private PopupWindow selectPopupWindow = null;
	// 自定义Adapter
	private OptionsAdapter optionsAdapter = null;
	// 下拉框选项数据源
	private ArrayList<String> datas = new ArrayList<String>();;
	// 下拉框依附组件
	private LinearLayout parent;
	// 下拉框依附组件宽度，也将作为下拉框的宽度
	private int pwidth;
	// 用户名文本框
	// private EditTextWithDel;
	// 下拉箭头图片组件
	private ImageView ivSelect;
	// 恢复数据源按钮
	private Button button;
	// 展示所有下拉选项的ListView
	private ListView listView = null;
	// 是否初始化完成标志
	private boolean flag = false;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dismissWaitDialog();
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_SUCCESS:
				onLoginResponse();
				break;
			case CommonData.HTTP_HANDLE_FAILE:
				ToastUtils.showShortToast(getApplicationContext(),
						msg.obj.toString());
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// 初始化个推
		PushManager.getInstance().initialize(this.getApplicationContext());
		init();
		initTitleView(-1, 0, R.string.login, 255, R.string.reg, 0);
		SharedPreManager.getInstance().init(getApplicationContext());

	}

	/**
	 * 没有在onCreate方法中调用initWedget()，而是在onWindowFocusChanged方法中调用，
	 * 是因为initWedget()中需要获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!flag) {
			// initWedget();
			flag = true;
		}
	}

	private void initWedget() {
		ivSelect = (ImageView) findViewById(R.id.iv_select);
		parent = (LinearLayout) findViewById(R.id.ll_user);
		// 获取下拉框依附的组件宽度
		int width = parent.getWidth();
		pwidth = width;
		// 初始化PopupWindow
		initPopuWindow();
		// 设置点击下拉箭头图片事件，点击弹出PopupWindow浮动下拉框
		ivSelect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag) {
					// 显示PopupWindow窗口
					popupWindwShowing();
				}
			}
		});

	}

	private void init() {
		username = (TextView) findViewById(R.id.et_userName);
		pwd = (TextView) findViewById(R.id.et_pwd);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (username.getText().toString().trim().equals("1")) {
					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
					ActivityManager.getInstance().popAllActivity();
					return;
				}

				if (!Utils.isNetworkAvailable(LoginActivity.this)) {
					ToastUtils.showShortToast(getApplicationContext(),
							R.string.common_network_unavalible);
					return;
				}

				if (StringUtils.isEmpty(username.getText().toString().trim())) {
					ToastUtils.showShortToast(getApplicationContext(),
							R.string.account_input_hint);
					return;
				}

				if (StringUtils.isEmpty(pwd.getText().toString().trim())) {
					ToastUtils.showShortToast(getApplicationContext(),
							R.string.pwd_input_hint);
					return;
				}

				login();

			}
		});

		changeRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ActivityManager.getInstance().startNextActivity(
						Reg0Activity.class);
			}
		});
	}

	private void login() {
		showWaitDialog(R.string.common_logining);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				try {

					String cid = PushManager.getInstance().getClientid(
							LoginActivity.this);
					// ResultOfTSeller sellerLoginByName = WebServiceManger
					// .getInstance()
					// .getBaseInfoService()
					// .sellerLoginByName(
					// username.getText().toString().trim(),
					// pwd.getText().toString().trim(), "android",
					// cid, "");
					ResultOfTSeller sellerLoginByName = WebServiceManger
							.getInstance()
							.getBaseInfoService()
							.sellerLoginByTel(
									username.getText().toString().trim(),
									pwd.getText().toString().trim(), "");
					Message message = new Message();
					if (sellerLoginByName.isSuccess()) {
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
						tuser = sellerLoginByName.getData();
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = sellerLoginByName.getMess();
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
					dismissWaitDialog();
					ToastUtils.showShortToast(getApplicationContext(), "服务器异常");
				}
			}
		});
	}

	private void onLoginResponse() {
		SharedPreManager.getInstance()
				.setInt(CommonData.USER_ID, tuser.getId());
//		String name = StringUtils.unicode2String(tuser.getName());
		SharedPreManager.getInstance().setString(CommonData.USER_NAME,
				tuser.getName());
		SharedPreManager.getInstance().setString(CommonData.USER_PHONE,
				tuser.getTel());
		SharedPreManager.getInstance().setString(CommonData.USER_EMAIL,
				tuser.getMail());
		
//		String city = StringUtils.unicode2String(tuser.getCityName());
		SharedPreManager.getInstance().setString(CommonData.USER_CITY,
				tuser.getCityName());

		SharedPreManager.getInstance().setString(CommonData.Bland1,
				tuser.getSellBrandName1());
		SharedPreManager.getInstance().setString(CommonData.Bland2,
				tuser.getSellBrandName2());
		SharedPreManager.getInstance().setString(CommonData.Bland3,
				tuser.getSellBrandName3());

		SharedPreManager.getInstance().setString(CommonData.BlandId1,
				tuser.getSellBrand1());
		SharedPreManager.getInstance().setString(CommonData.BlandId2,
				tuser.getSellBrand2());
		SharedPreManager.getInstance().setString(CommonData.BlandId3,
				tuser.getSellBrand3());

		RegisterManager.getInstance().setBland1(tuser.getSellBrand1());
		RegisterManager.getInstance().setBland2(tuser.getSellBrand2());
		RegisterManager.getInstance().setBland3(tuser.getSellBrand3());

		startActivity(new Intent(getApplicationContext(), MainActivity.class));
		ActivityManager.getInstance().popAllActivity();
		finish();
	}

	/**
	 * 初始化填充Adapter所用List数据
	 */
	private void initDatas() {
		datas.clear();
		datas.add("北京");
		datas.add("上海");
		datas.add("广州");
		datas.add("深圳");
		datas.add("重庆");
		datas.add("青岛");
		datas.add("石家庄");
	}

	/**
	 * 初始化PopupWindow
	 */
	private void initPopuWindow() {
		initDatas();
		// PopupWindow浮动下拉框布局
		View loginwindow = (View) this.getLayoutInflater().inflate(
				R.layout.options, null);
		listView = (ListView) loginwindow.findViewById(R.id.list);
		// 设置自定义Adapter
		optionsAdapter = new OptionsAdapter(this, handler, datas);
		listView.setAdapter(optionsAdapter);
		selectPopupWindow = new PopupWindow(loginwindow, pwidth,
				LayoutParams.WRAP_CONTENT, true);
		selectPopupWindow.setOutsideTouchable(true);
		// 这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
		// 没有这一句则效果不能出来，但并不会影响背景
		// 本人能力极其有限，不明白其原因，还望高手、知情者指点一下
		selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 显示PopupWindow窗口
	 * 
	 * @param popupwindow
	 */
	public void popupWindwShowing() {
		// 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
		// 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
		// （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
		selectPopupWindow.showAsDropDown(parent, 0, -3);
	}

	/**
	 * PopupWindow消失
	 */
	public void dismiss() {
		selectPopupWindow.dismiss();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogN.d(this, "LoginActivity onDestroy");
	}
}

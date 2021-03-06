package com.chehui.activity;

/****
 * 添加银行卡页面1 （添加卡人 卡号）
 */

import java.util.ArrayList;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import chehui.maichetong.bankservice.ResultOfBoolean;

import com.chehui.adapter.OptionsAdapter;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

public class ActivityManagerAccountAddBank1 extends BaseActivity implements
		OnClickListener {
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
	// 展示所有下拉选项的ListView
	private ListView listView = null;
	// 是否初始化完成标志
	private boolean flag = false;
	private EditText etCarHolder;
	private EditText etCarNumber;

	private Button btnNext;
	private EditText holder;
	private EditText number;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				if (msg.obj.toString() != null) {
					ToastUtils.showShortToast(getApplicationContext(),
							msg.obj.toString());
				} else {
					ToastUtils.showShortToast(getApplicationContext(),
							R.string.common_abnormal_server);
				}
				break;
			case CommonData.HTTP_HANDLE_SUCCESS:
				onSuccessResponse();
				break;
			default:
				break;
			}
			
				dismissWaitDialog();
		}

		private void onSuccessResponse() {
			activityManager.getInstance().startNextActivity(
					ActivityManagerAccountAddBank3.class);
			ActivityManagerAccountAddBank1.this.finish();
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account_add_bank1);
		initTitleView(-1, 255, R.string.set_account_add_bank_card, 255, -1, 0);
		init();
	}

	private void init() {
		btnNext = (Button) findViewById(R.id.btn_next_add_bank);
		btnNext.setOnClickListener(this);

		holder = (EditText) findViewById(R.id.et_card_holder);
		number = (EditText) findViewById(R.id.et_card_holder_number);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_add_bank:
			if (holder.getText().toString() == null) {
				ToastUtils.showShortToast(getApplicationContext(), "持卡人不能为空！");
				return;

			}
			if (number.getText().toString() == null) {
				ToastUtils.showShortToast(getApplicationContext(), "卡号不能为空！");
				return;
			}
			addCardRequest(number.getText().toString(), number.getText()
					.toString());
			break;
		default:
			break;
		}
	}

	private void addCardRequest(final String carNumber, final String id) {
		showWaitDialog(R.string.common_querying);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				try {
					ResultOfBoolean addBankCard = WebServiceManger
							.getInstance().getBankService()
							.addBankCard(carNumber, "1");
					Message message = handler.obtainMessage();
					if (addBankCard.isSuccess()) {
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj=addBankCard.getMess();
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					dismissWaitDialog();
					e.printStackTrace();
				}
			}
		});

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

	/**
	 * 没有在onCreate方法中调用initWedget()，而是在onWindowFocusChanged方法中调用，
	 * 是因为initWedget()中需要获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!flag) {
//			initWedget();
			flag = true;
		}
	}

	private void initWedget() {
		etCarHolder = (EditText) findViewById(R.id.et_card_holder);
		etCarNumber = (EditText) findViewById(R.id.et_card_holder_number);
		parent = (LinearLayout) findViewById(R.id.ll_user);
		// 获取下拉框依附的组件宽度
		int width = parent.getWidth();
		pwidth = width;
		// 初始化PopupWindow
		initPopuWindow();
		// 设置点击下拉箭头图片事件，点击弹出PopupWindow浮动下拉框
		etCarHolder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag) {
					// 显示PopupWindow窗口
					popupWindwShowing();
				}
			}
		});
	}
}

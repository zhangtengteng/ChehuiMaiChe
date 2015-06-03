package com.chehui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import chehui.maichetong.baseinfoservice.ResultOfString;
import chehui.maichetong.useroperationservice.ResultOfListOfTBrand;
import chehui.maichetong.useroperationservice.TBrand;

import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.diy.PickerView.onSelectListener;
import com.chehui.manage.comm.SharedPreManager;
import com.chehui.manager.comm.RegisterManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;
import com.jinwan.pop.PoPBlandWindowManager;

public class Reg3Activity extends BaseActivity implements OnClickListener {
	private Button btn;
	private LinearLayout Bland1;
	private LinearLayout Bland2;
	private LinearLayout Bland3;

	private TextView textView1;
	private TextView textView2;
	private TextView textView3;

	List<TBrand> tBrand;
	List<String> data = new ArrayList<String>();
	Map brands = new HashMap<String, Integer>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dismissWaitDialog();
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_SUCCESS:
				onRegistResponse();
				break;
			case CommonData.HTTP_HANDLE_FAILE:

				if (msg.obj.toString() != null) {
					ToastUtils.showShortToast(getApplicationContext(),
							msg.obj.toString());
				} else {
					ToastUtils
							.showShortToast(getApplicationContext(), "服务器异常!");
				}

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg3);
		initTitleView(-1, 255, R.string.reg, 255, -1, 0);
		init();
	}

	private void init() {

		Bland1 = (LinearLayout) findViewById(R.id.ll_one);
		Bland1.setOnClickListener(this);

		Bland2 = (LinearLayout) findViewById(R.id.ll_two);
		Bland2.setOnClickListener(this);

		Bland3 = (LinearLayout) findViewById(R.id.ll_tree);
		Bland3.setOnClickListener(this);

		textView1 = (TextView) findViewById(R.id.tv_bland1);
		textView2 = (TextView) findViewById(R.id.tv_bland2);
		textView3 = (TextView) findViewById(R.id.tv_bland3);

		btn = (Button) findViewById(R.id.btn_next3);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				userRegister();

			}
		});

		setPopWidth();

		getPickViewData();
	}

	/***
	 * 设置pop的宽度
	 */
	private void setPopWidth() {
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(),
				width, 500, R.layout.pop_bland);
	}

	/**
	 * 注册
	 */
	private void userRegister() {
		final RegisterManager registerManager = RegisterManager.getInstance();
		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {

				try {
					
					String cityName = registerManager.getCityName();
//					cityName=StringUtils.string2Unicode(cityName);
//					cityName=URLEncoder.encode(cityName);
//					cityName=new String(cityName.getBytes("GB2312"),"8859_1");
					String nickName=registerManager.getNickName();
//					nickName=URLEncoder.encode(nickName);
//					nickName=StringUtils.string2Unicode(cityName);
//					String cityName = registerManager.getCityName().getBytes("ISO-8859-1").toString();
					
					ResultOfString userRegister = WebServiceManger
							.getInstance()
							.getBaseInfoService()
							.sellerRegister(nickName,
									registerManager.getPassWord(),
									registerManager.getTel(),
									registerManager.getTel()+"@163.com", cityName,
									registerManager.getBland1(),
									registerManager.getBland2(),
									registerManager.getBland3(), "");
					Message message = new Message();
					if (userRegister.isSuccess()) {
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = userRegister.getMess();
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});
	}

	private void onRegistResponse() {

		activityManager.getInstance().startNextActivity(Reg4Activity.class);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_one:
			setText(textView1, 1);
			break;
		case R.id.ll_two:
			setText(textView2, 2);
			break;
		case R.id.ll_tree:
			setText(textView3, 3);

			break;

		default:
			break;
		}
	}

	/**
	 * 用户选中品牌，更新品牌栏文字
	 * 
	 * @param view
	 */
	private void setText(final TextView view, final int i) {
		if (data == null) {
			ToastUtils.showShortToast(getApplicationContext(), "获取品牌信息失败！");
			getPickViewData();
			return;
		}
		PoPBlandWindowManager.getInstance().setPickViewData(data);
		PoPBlandWindowManager.getInstance().changeOnSelect(
				new onSelectListener() {

					@Override
					public void onSelect(String text) {
						view.setText(text);
						Integer brandId = (Integer) brands.get(text);
						if (i == 1) {
							RegisterManager.getInstance().setBland1(
									brandId + "");
							
							SharedPreManager.getInstance().setString(CommonData.BlandId1, brandId+"");
						} else if (i == 2) {
							RegisterManager.getInstance().setBland2(
									brandId + "");
							SharedPreManager.getInstance().setString(CommonData.BlandId2, brandId+"");
						} else if (i == 3) {
							RegisterManager.getInstance().setBland3(
									brandId + "");
							SharedPreManager.getInstance().setString(CommonData.BlandId3, brandId+"");
						}
					}
				});
		setPopWidth();
		PoPBlandWindowManager.getInstance().showPopAllLocation(view,
				Gravity.CENTER | Gravity.BOTTOM, 0, 0);
	};

	/***
	 * 获取品牌信息
	 */
	private void getPickViewData() {
		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					ResultOfListOfTBrand brandName = WebServiceManger
							.getInstance().getUserOperationService()
							.getBrandName();
					if (brandName.isSuccess()) {
						tBrand = brandName.getData().getTBrand();
						for (int i = 0; i < tBrand.size(); i++) {
							data.add(tBrand.get(i).getBrandName());
							brands.put(tBrand.get(i).getBrandName(), tBrand
									.get(i).getBrandID());
						}
					} else {
					}
					dismissWaitDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
}

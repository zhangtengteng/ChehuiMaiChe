package com.chehui.activity;

/****
 * 我的信息页面
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import chehui.maichetong.baseinfoservice.ResultOfString;
import chehui.maichetong.useroperationservice.ResultOfListOfTBrand;
import chehui.maichetong.useroperationservice.TBrand;

import com.chehu.pop.PoPModifyBrandManager;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.diy.PickerView.onSelectListener;
import com.chehui.manage.comm.SharedPreManager;
import com.chehui.manager.comm.RegisterManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.utils.Utils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;
import com.jinwan.pop.PoPBlandWindowManager;

public class ActivityManagerAccountPersion extends BaseActivity implements
		OnClickListener {
	private TextView userName;
	private TextView userPhone;
	private TextView userEmail;
	private TextView userCity;
	private TextView userBland1;
	private TextView userBland2;
	private TextView userBland3;

	private TextView modifyName;
	private TextView modifyPhone;
	private TextView modifyEmail;
	private TextView modifyLocal;
	private TextView modifiyBrand;

	private int brandId1;
	private int brandId2;
	private int brandId3;

	View brandview;
	private LinearLayout Bland1;
	private LinearLayout Bland2;
	private LinearLayout Bland3;
	List<TBrand> tBrand;
	Map brands = new HashMap<String, Integer>();
	List<String> data = new ArrayList<String>();

	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	View childAt;
	String[] strs;
	Builder builder;
	AlertDialog builder2;

	boolean flag;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String input = "";
			if (msg.obj != null) {
				input = msg.obj.toString();
			}
			switch (msg.what) {
			case 0:
				userName.setText(input);
				break;
			case 1:
				userPhone.setText(input);
				break;
			case 2:
				userEmail.setText(input);
				break;
			case 3:
				userCity.setText(input);
				break;
			case 4:
				// 品牌
				break;
			case 1001:
				onModifyesponse();
				break;
			case 1002:
				if (msg.obj != null) {
					ToastUtils.showShortToast(getApplicationContext(),
							msg.obj.toString());
				} else {

					ToastUtils.showShortToast(getApplicationContext(), "未知错误！");
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
		setContentView(R.layout.activity_manager_account_persion);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		init();
	}

	private void init() {

		childAt = ((ViewGroup) findViewById(android.R.id.content))
				.getChildAt(0);
		userName = (TextView) findViewById(R.id.tv_user_name);
		userName.setText(SharedPreManager.getInstance().getString(
				CommonData.USER_NAME, ""));

		userPhone = (TextView) findViewById(R.id.tv_user_phone);
		userPhone.setText(SharedPreManager.getInstance().getString(
				CommonData.USER_PHONE, ""));

		userEmail = (TextView) findViewById(R.id.tv_user_email);
		userEmail.setText(SharedPreManager.getInstance().getString(
				CommonData.USER_EMAIL, "888888"));

		userCity = (TextView) findViewById(R.id.tv_user_city);
		userCity.setText(SharedPreManager.getInstance().getString(
				CommonData.USER_CITY, "中国"));

		userBland1 = (TextView) findViewById(R.id.tv_user_bland1);
		userBland1.setText(SharedPreManager.getInstance().getString(
				CommonData.Bland1, "宝马"));
		// RegisterManager.getInstance().setBland1(userBland1.getText().toString());

		userBland2 = (TextView) findViewById(R.id.tv_user_bland2);
		userBland2.setText(SharedPreManager.getInstance().getString(
				CommonData.Bland2, "奥迪"));
		// RegisterManager.getInstance().setBland1(userBland2.getText().toString());

		userBland3 = (TextView) findViewById(R.id.tv_user_bland3);
		userBland3.setText(SharedPreManager.getInstance().getString(
				CommonData.Bland3, "奔驰"));
		// RegisterManager.getInstance().setBland1(userBland3.getText().toString());

		modifyName = (TextView) findViewById(R.id.tv_modify_name);
		modifyName.setOnClickListener(this);
		modifiyBrand = (TextView) findViewById(R.id.tv_modify_brand);
		modifiyBrand.setOnClickListener(this);
		modifyEmail = (TextView) findViewById(R.id.tv_modify_email);
		modifyEmail.setOnClickListener(this);
		modifyLocal = (TextView) findViewById(R.id.tv_modify_location);
		modifyLocal.setOnClickListener(this);
		modifyPhone = (TextView) findViewById(R.id.tv_modify_phone);
		modifyPhone.setOnClickListener(this);

		strs = getResources().getStringArray(R.array.modify_info);
		setPopWidth();
		getPickViewData();
		setPopModifyWidth();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_modify_name:
			showModifyDialog(0);
			flag = true;
			break;
		case R.id.tv_modify_phone:
			showModifyDialog(1);
			flag = true;
			break;
		case R.id.tv_modify_brand:
			// showModifyDialog("修改");
			showModifyDialog2(4);
			// PoPModifyBrandManager.getInstance().showPopAllLocation(childAt,Gravity.CENTER_VERTICAL,0,0);
			flag = true;
			break;
		case R.id.tv_modify_email:
			showModifyDialog(2);
			flag = true;
			break;
		case R.id.tv_modify_location:
			showModifyDialog(3);
			flag = true;
			break;
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

		dismissWaitDialog();
	}

	/***
	 * 显示修改对话框
	 */
	private void showModifyDialog(final int type) {
		final EditText et = new EditText(this);
		if (builder == null) {
			builder = new AlertDialog.Builder(this);
		}
		builder.setTitle(strs[type]).setIcon(android.R.drawable.ic_dialog_info)
				.setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String input = et.getText().toString();
						if (input.equals("")) {
							Toast.makeText(getApplicationContext(),
									"内容不能为空！" + input, Toast.LENGTH_LONG)
									.show();
						} else {
							Message message = handler.obtainMessage(type);
							message.obj = input;
							handler.sendMessage(message);
						}
					}

				}).setNegativeButton("取消", null).show();
	}

	/***
	 * 显示修改品牌对话框
	 */
	private void showModifyDialog2(final int type) {
		brandview = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.alertdialog_modify_brand, null);
		Bland1 = (LinearLayout) brandview.findViewById(R.id.ll_one);
		Bland1.setOnClickListener(this);

		Bland2 = (LinearLayout) brandview.findViewById(R.id.ll_two);
		Bland2.setOnClickListener(this);

		Bland3 = (LinearLayout) brandview.findViewById(R.id.ll_tree);
		Bland3.setOnClickListener(this);

		textView1 = (TextView) brandview.findViewById(R.id.tv_bland1);
		textView2 = (TextView) brandview.findViewById(R.id.tv_bland2);
		textView3 = (TextView) brandview.findViewById(R.id.tv_bland3);

		// if (builder2 == null) {
		// builder2 = new AlertDialog.Builder(this).create();
		// }
		// builder2.setTitle(strs[type]);
		// builder2.setIcon(android.R.drawable.ic_dialog_info);
		// builder2.setView(brandview);
		// builder2.show();
		if (builder == null) {
			builder = new AlertDialog.Builder(this);
		}
		builder.setTitle(strs[type]).setIcon(android.R.drawable.ic_dialog_info)
				.setView(brandview)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// // String input = et1.getText().toString();
						// if (input.equals("")) {
						// Toast.makeText(getApplicationContext(),
						// "内容不能为空！" + input, Toast.LENGTH_LONG)
						// .show();
						// } else {
						// Message message = handler.obtainMessage(type);
						// message.obj = input;
						// handler.sendMessage(message);
						// }

						SharedPreManager.getInstance().setString(
								CommonData.Bland1,
								textView1.getText().toString());
						SharedPreManager.getInstance().setString(
								CommonData.Bland2,
								textView2.getText().toString());
						SharedPreManager.getInstance().setString(
								CommonData.Bland3,
								textView3.getText().toString());

						userBland1.setText(textView1.getText().toString());
						userBland2.setText(textView2.getText().toString());
						userBland3.setText(textView3.getText().toString());

					}

				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 修改个人信息
	 */
	private void sellerModify() {
		if (!Utils.isNetworkAvailable(ActivityManagerAccountPersion.this)) {
			ToastUtils.showShortToast(getApplicationContext(),
					R.string.common_network_unavalible);
			return;
		}

		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				Message message = handler.obtainMessage();
				try {
					int id = SharedPreManager.getInstance().getInt(
							CommonData.USER_ID, 1);
					String myId = String.valueOf(id);
					String brand1 = RegisterManager.getInstance().getBland1();
					String brand2 = RegisterManager.getInstance().getBland2();
					String brand3 = RegisterManager.getInstance().getBland3();
					String userPhone1 = userPhone.getText().toString();
					String userEmail1 = userEmail.getText().toString();
					String userCity1 = userCity.getText().toString();
					ResultOfString sellerModify = WebServiceManger
							.getInstance()
							.getBaseInfoService()
							.sellerModify(myId, userPhone1, userEmail1,
									userCity1, "", "", brand1, brand2, brand3);
					if (sellerModify.isSuccess()) {
						message.what = 1001;
					} else {
						message.what = 1002;
						message.obj = sellerModify.getMess();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.sendMessage(message);

			}
		});
	}

	private void onModifyesponse() {
		SharedPreManager.getInstance().setString(CommonData.USER_NAME,
				userName.getText().toString());
		SharedPreManager.getInstance().setString(CommonData.USER_PHONE,
				userPhone.getText().toString());
		SharedPreManager.getInstance().setString(CommonData.USER_EMAIL,
				userEmail.getText().toString());
		SharedPreManager.getInstance().setString(CommonData.USER_CITY,
				userCity.getText().toString());
		// SharedPreManager.getInstance().setString(CommonData.Bland1,
		// tuser.getSELLBRAND1());
		// SharedPreManager.getInstance().setString(CommonData.Bland2,
		// tuser.getSELLBRAND2());
		// SharedPreManager.getInstance().setString(CommonData.Bland3,
		// tuser.getSELLBRAND3());

		flag = false;
		ToastUtils.showShortToast(getApplicationContext(), "信息修改完成！");
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
						System.out.println("brandId=" + brandId + ", i=" + i);
						if (i == 1) {
							RegisterManager.getInstance().setBland1(
									brandId + "");
						} else if (i == 2) {
							RegisterManager.getInstance().setBland2(
									brandId + "");
						} else if (i == 3) {
							RegisterManager.getInstance().setBland3(
									brandId + "");
						}
					}
				});
		setPopWidth();
		// View childAt =
		// ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
		// PoPBlandWindowManager.getInstance().showPopAllLocation(Bland3,
		// Gravity.CENTER | Gravity.BOTTOM, 0, 0);

		PoPBlandWindowManager.getInstance().showPopAsDropDown(brandview, 0, 0);
	};

	/***
	 * 获取品牌信息
	 */
	private void getPickViewData() {
		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {

			@Override
			public void run() {
				 ResultOfListOfTBrand allBrandName = WebServiceManger.getInstance()
						.getUserOperationService().getALLBrandName();

				if (allBrandName.isSuccess()) {
					tBrand = allBrandName.getData().getTBrand();
					for (int i = 0; i < tBrand.size(); i++) {
						data.add(tBrand.get(i).getBrandName());
						brands.put(tBrand.get(i).getBrandName(), tBrand.get(i)
								.getBrandID());
					}
				} else {
				}

				dismissWaitDialog();
			}
		});

	}

	/***
	 * 设置pop的宽度
	 */
	private void setPopWidth() {
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(),
				width, 700, R.layout.pop_bland);
	}

	/***
	 * 设置pop的宽度
	 */
	private void setPopModifyWidth() {
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		PoPBlandWindowManager.getInstance().init(getApplicationContext(),
				width, 500, R.layout.alertdialog_modify_brand);
	}

	@Override
	protected void onPause() {
		if (flag) {
			sellerModify();
		}
		super.onPause();
	}

}

package com.chehui.activity;

/****
 * 提交我的询价
 */
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import chehui.maichetong.selleroperationservice.ResultOfTQuote;

import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manage.comm.SharedPreManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.update.UpdateManager;
import com.chehui.utils.ToastUtils;
import com.chehui.utils.Utils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

public class XunJiaActivity extends BaseActivity {
	private EditText etCheXi;
	private int carID;
	private String cityName;
	private Button submit;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				if (msg.obj != null) {

					ToastUtils.showShortToast(getApplicationContext(),
							msg.obj.toString());
				} else {

					ToastUtils.showShortToast(getApplicationContext(), "未知错误！");
				}
				break;
			case CommonData.HTTP_HANDLE_SUCCESS:
				ToastUtils.showShortToast(getApplicationContext(), "报价成功！");
				break;
			default:

				break;
			}
			dismissWaitDialog();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xunjia);
		initTitleView(-1, 255, R.string.xunjia_baojia, 255, -1, 0);
		init();
	}

	private void init() {
		String title = (String) getIntent().getExtras().get("tvTopTitle");
		carID = Integer.valueOf(getIntent().getExtras().get("carID").toString());
		cityName = getIntent().getExtras().get("cityName").toString();
		
		
		etCheXi=(EditText) findViewById(R.id.tv_chexi);
		etCheXi.setText(title);
		
		submit=(Button) findViewById(R.id.btn_submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				addQuete();
			}
		});
	}

	/***
	 * 添加我的询价
	 */
	private void addQuete() {
		// sellerID 用户id
		// carID
		// cityname
		// floorPrice 底价
		// insurancePrice 保险
		// licensePrice 牌照
		// purchaseTax 购买方式
		// prize 奖品
		// dingPrice 定金
		// registrationFee 登记；注册；挂号 费
		// carDecoration
		// carColor
		// carGift 礼物
		// carPlan 面图 vt. 计划；设计；打算 vi. 计划；打算
		// payMode
		// carAddress

		if (!Utils.isNetworkAvailable(XunJiaActivity.this)) {
			ToastUtils.showShortToast(getApplicationContext(),
					R.string.common_network_unavalible);
			return;
		}
		
		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				Message message = handler.obtainMessage();
				int id = SharedPreManager.getInstance().getInt(CommonData.USER_ID, 1);
				try {
					ResultOfTQuote addQuete = WebServiceManger
							.getInstance()
							.getSellerOperationService()
							.addQuete(id,"100001", carID, cityName, "1", "1", "1", "1", "1", "1",
									"1", "1", "1", "1", "1", "1", "1");
					if (addQuete.isSuccess()) {
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					} else {
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					}
				} catch (Exception e) {
					dismissWaitDialog();
					e.printStackTrace();
				}
				handler.sendMessage(message);
			}
		});

	}

}

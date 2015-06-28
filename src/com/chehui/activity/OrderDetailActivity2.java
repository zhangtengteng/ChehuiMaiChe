package com.chehui.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import chehui.maichetong.selleroperationservice.ResultOfListOfTOrder;
import chehui.maichetong.selleroperationservice.TOrder;

import com.chehui.afinal.FinalBitmap;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.utils.Utils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

/***
 * 询价详情2
 * 
 * @author zhangtengteng
 * 
 */
public class OrderDetailActivity2 extends BaseActivity {
	private ImageView ivTopIcon;
	private TextView tvTopTitle;
	private TextView tvState;
	private TextView tvNumber;
	private EditText tvPrice;
	private TextView tvCity;
	private EditText tvInsurancePrice;
	private TextView tvPhone;
	private TextView tvIntroduction;
	private TextView tvPayTime;
	private Button btn;
	FinalBitmap finalBitmap;
	private TOrder order;
	Integer orderId;
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
				onSuccessResponse();
				break;
			default:

				break;
			}
			dismissWaitDialog();
		}

		private void onSuccessResponse() {
			// TOrder order = listOrder.get(0);
			tvTopTitle.setText(order.getCarDetail());
			if (order.getState() == 1) {
				tvState.setText("求价");
			} else if (order.getState() == 0) {
				tvState.setText("已求价");
			}
			tvInsurancePrice.setText(order.getInsurancePrice());
			// tvPrice.setText(order.getExpectPrice());
			tvPhone.setText(order.getTel());

			tvCity.setText(order.getCityname());
			// tvIntroduction.setText(order.getDescription());
			tvNumber.setText(order.getDdbh());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail3);
		initTitleView(-1, 255, R.string.order_detail, 255, -1, 0);
		init();

	}

	private void init() {

		order = (TOrder) getIntent().getExtras().get("orderDetail");

		// finalBitmap=FinalBitmap.create(this);

		// ivTopIcon=(ImageView) findViewById(R.id.iv_top_icon);
		tvTopTitle = (TextView) findViewById(R.id.tv_top_title);
		tvTopTitle.setText(order.getCarDetail());

//		tvState = (TextView) findViewById(R.id.tv_order_detial_state);
//		if (order.getState() == 1) {
//			tvState.setText("求价");
//		} else if (order.getState() == 0) {
//			tvState.setText("已求价");
//		}

		tvNumber = (TextView) findViewById(R.id.tv_order_detial_number);
		tvNumber.setText(order.getDdbh());

		//保险
		tvInsurancePrice = (EditText) findViewById(R.id.tv_order_detial_InsurancePrice);
		tvInsurancePrice.setText(order.getExpectPrice());
		//车辆价格
		tvPrice = (EditText) findViewById(R.id.tv_order_detial_price);
		tvPrice.setText(order.getInsurancePrice());
		//
//		tvPhone = (TextView) findViewById(R.id.tv_order_detial_phone);
//		tvPhone.setText(order.getTel());
//		
		tvIntroduction = (TextView) findViewById(R.id.tv_order_detial_introduction);
		

		//
		tvCity = (TextView) findViewById(R.id.tv_order_detial_city);
		tvCity.setText(order.getCityname());
		
		
		Button baojia = (Button) findViewById(R.id.btn_baojia);
		baojia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 activityManager.getInstance().startNextActivity(
				 ActivityOrderDetailOk.class);
				 OrderDetailActivity2.this.finish();
			}
		});
//		btn = (Button) findViewById(R.id.btn_check);
//		btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//
//				 Intent intent = new Intent();
//				 intent.putExtra("tvTopTitle",
//				 tvTopTitle.getText().toString());
//				 intent.putExtra("carID",order.getCarID());
//				 intent.putExtra("cityName",order.getCityname());
//				 activityManager.getInstance().startNextActivity(intent,
//				 XunJiaActivity.class);
//			}
//		});
		// getOrderDetail();
	}
}

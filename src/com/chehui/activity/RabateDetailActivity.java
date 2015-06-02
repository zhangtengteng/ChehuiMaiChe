package com.chehui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehui.afinal.FinalBitmap;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

/***
 * 返利详情
 * @author zhangtengteng
 *
 */
public class RabateDetailActivity extends BaseActivity {
	private ImageView ivTopIcon;
	private TextView tvTopTitle;
	private TextView tvState;
	private TextView tvNumber;
	private TextView tvCount;
	private TextView tvPrice;
	private TextView tvPhone;
	private TextView tvIntroduction;
	private TextView tvPayTime;
	
	FinalBitmap finalBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rabate_detail2);
		initTitleView(-1, 255, R.string.main_rebate, 255, -1, 0);
		init();
	}

	private void init() {
//		finalBitmap=FinalBitmap.create(this);
//		
		Bundle extras = getIntent().getExtras();
		int  state = Integer.valueOf(extras.get("state").toString());
		String  money = (String) extras.get("money");
		String number = (String) extras.get("number");
		
		
//		ivTopIcon=(ImageView) findViewById(R.id.iv_top_icon);
//		
		tvTopTitle=(TextView) findViewById(R.id.tv_top_title);
		tvTopTitle.setText("订单编号   "+number);
//		
		tvState=(TextView) findViewById(R.id.tv_order_detial_state);
		switch (state) {
		case 0:
			tvState.setText("未申请");
			break;
		case 1:
			tvState.setText("申请提款中");
			break;
		case 2:
			tvState.setText("提款被驳回");
			break;
		case 3:
			tvState.setText("申请通过,出账中");
			break;
		case 4:
			tvState.setText("已返利");
			break;
		default:
			tvState.setText("无状态");
			break;
		}
//		
//		tvNumber=(TextView) findViewById(R.id.tv_order_detial_number);
//		tvNumber.setText(goodsModel.getOID());
//		
//		
//		tvCount=(TextView) findViewById(R.id.tv_order_detial_count);
//		tvCount.setText(goodsModel.getOrderNum()+"件");
//		
//		
		tvPrice=(TextView) findViewById(R.id.tv_order_detial_money);
		tvPrice.setText(money+"元");
//		
		
		
		tvPayTime=(TextView) findViewById(R.id.tv_order_detial_time);
		tvPayTime.setText("2015年1月4日");
		
//		tvIntroduction=(TextView) findViewById(R.id.tv_order_detial_introduction);
//		tvIntroduction.setText(goodsModel.getIntro());
//		
//		tvPayTime=(TextView) findViewById(R.id.tv_order_detial_pay_time);
//		tvPayTime.setText(goodsModel.getPayTime());
//		
//		
//		SharedPreferences sharedPreferences = getSharedPreferences(
//				"chehui", Activity.MODE_PRIVATE);
//		// 使用getString方法获得value，注意第2个参数是value的默认值
//		String dataCity = sharedPreferences.getString("dataCity", "");
//		if (dataCity.equals("home")) {
//			dataCity = "nanjing";
//		}
//		String url = "http://" + dataCity + CommonData.IMAGE_ADDRESS
//				+ goodsModel.getPicFace();
//		ivTopIcon=(ImageView) findViewById(R.id.iv_top_icon);
//		finalBitmap.display(ivTopIcon, url);
	}
	
	
}

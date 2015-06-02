package com.chehui.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehui.afinal.FinalBitmap;
import com.chehui.comm.CommonData;
import com.chehui.model.GoodsModel;
import com.chehui.utils.LogN;
import com.example.myproject.R;

public class OrderPayAdapter extends BaseAdapter {
	private List<GoodsModel> list;
	private LayoutInflater inflater;
	private Context context;
	FinalBitmap finalBitmap;
	
	public OrderPayAdapter(List list, Context context) {
		super();
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}
	public OrderPayAdapter(Context context) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}
		
	public void  setData(List<GoodsModel> list){
		this.list= list;
	}
	public List<GoodsModel>  getData(){
		return this.list;
	}
	
	@Override
	public int getCount() {
		if(list.size()<=0){
			return 1;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(list.size()<=0){
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.listview_no_item, null);
			}
			
			return convertView;
		}
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_order_item, null);
		}
		final GoodsModel goodsModel = list.get(position);

		// 状态
		TextView tvOrderState = (TextView) convertView
				.findViewById(R.id.tv_order_state);
		String state = goodsModel.getPayState();
		LogN.d(this, "*******************state=" + state);
		if (state.equals("1")) {
			tvOrderState.setText("已消费");
		} else if (state.equals("0")) {
			tvOrderState.setText("已支付");
		}

		// 标题
		TextView tvOrderTitle = (TextView) convertView
				.findViewById(R.id.tv_order_title);
		tvOrderTitle.setText(goodsModel.getTitle());

		// 金额：19:00元 ●数量：10件
		TextView tvOrderMoney = (TextView) convertView
				.findViewById(R.id.tv_order_money);
		tvOrderMoney.setText(" 金额：" + goodsModel.getOrderPrice() + "元 ●数量："
				+ goodsModel.getOrderNum() + "件");

		// 手机号
		TextView tvOrderPhone = (TextView) convertView
				.findViewById(R.id.tv_order_phone);
		tvOrderPhone.setText(goodsModel.getSMSTel());

		// 订单号
		TextView tvOrderNumber = (TextView) convertView
				.findViewById(R.id.tv_order_number);
		tvOrderNumber.setText(goodsModel.getOID());
		// 时间
		TextView tvOrderTime = (TextView) convertView
				.findViewById(R.id.tv_order_time);
		tvOrderTime.setText(goodsModel.getPayTime());

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"chehui", Activity.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值
		String dataCity = sharedPreferences.getString("dataCity", "");
		if (dataCity.equals("home")) {
			dataCity = "nanjing";
		}
		String url = "http://" + dataCity + CommonData.IMAGE_ADDRESS
				+ goodsModel.getPicFace();
		System.out.println("url=====================" + url);
		// image
		ImageView ivImg = (ImageView) convertView.findViewById(R.id.iv_order_img);
		finalBitmap.display(ivImg, url);

//		// 点击列表进入订单详情页面
//		convertView.findViewById(R.id.rl_order_item).setOnClickListener(
//				new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Bundle bundle = new Bundle();
//						Intent intent = new Intent(context,
//								OrderDetailActivity.class);
//						intent.putExtra("goodsModel", goodsModel);
//						context.startActivity(intent);
//					}
//				});
		return convertView;
	}
}

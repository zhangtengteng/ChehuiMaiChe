package com.chehui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import chehui.maichetong.selleroperationservice.TBusiness;

import com.chehui.afinal.FinalBitmap;
import com.example.myproject.R;

/**
 * 返利 adapter
 * 
 * @author zhangtengteng
 * 
 */
public class RabateAdapter extends BaseAdapter {
	private List<TBusiness> list;
	private LayoutInflater inflater;
	private Context context;
	FinalBitmap finalBitmap;

	public RabateAdapter(List<TBusiness> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}

	public RabateAdapter(Context context) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}

	public void setData(List<TBusiness> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
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
		TBusiness business = list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_rabate_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.orderNumber = (TextView) convertView
					.findViewById(R.id.tv_rabate_order_number);
			holder.rabateMoney = (TextView) convertView
					.findViewById(R.id.tv_rabate_money);
			holder.rabateState = (TextView) convertView
					.findViewById(R.id.tv_rabate_state);
			holder.rabateTime = (TextView) convertView
					.findViewById(R.id.tv_rabate_time);
			 convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.orderNumber.setText(business.getBusinessID());
		holder.rabateMoney.setText(business.getReturnMoney());
//		holder.rabateTime.setText(business.getReturnMoney());
		
		//0:未申请 1:申请提款中 2:提款被驳回 3:申请通过,出账中 4:已返利
		int state = business.getState();
		switch (state) {
		case 0:
			holder.rabateState.setText("未申请");
			break;
		case 1:
			holder.rabateState.setText("申请提款中");
			break;
		case 2:
			holder.rabateState.setText("提款被驳回");
			break;
		case 3:
			holder.rabateState.setText("申请通过,出账中");
			break;
		case 4:
			holder.rabateState.setText("已返利");
			break;
		default:
			holder.rabateState.setText("无状态");
			break;
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView orderNumber;
		TextView rabateState;
		ImageView icon;
		TextView rabateMoney;
		TextView rabateTime;
	}
}

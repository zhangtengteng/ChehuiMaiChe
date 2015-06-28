package com.chehui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import chehui.maichetong.selleroperationservice.TQuote;

import com.chehui.afinal.FinalBitmap;
import com.example.myproject.R;

/**
 * 我的报价adapter
 * 
 * @author zhangtengteng
 * 
 */
public class MyOrderAdapter extends BaseAdapter implements OnClickListener {
	// 0 all ; 1 isok ;2 waitok;
	private int state;
	private List<TQuote> tQuotes = new ArrayList<TQuote>();
	private Context context;
	private LayoutInflater inflater;
	FinalBitmap finalBitmap;
	ViewHolder holder = null;

	public MyOrderAdapter(List<TQuote> tuanSigns, Context context, int state) {
		super();
		this.state = state;
		filterDatas(tuanSigns);
		this.context = context;
		inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}

	private void filterDatas(List<TQuote> tuanSigns) {
		tQuotes.clear();
		if(state==0){
			tQuotes=tuanSigns;
			return;
		}
		for (TQuote t : tuanSigns) {
			if (state == 1) {
				if (t.isIsUserPay()) {
					tQuotes.add(t);
				}
			} else if (state == 2) {
				if (!t.isIsUserPay()) {
					tQuotes.add(t);
				}
			} 
		}

	}

	@Override
	public int getCount() {
		return tQuotes.size();
	}

	@Override
	public Object getItem(int position) {
		return tQuotes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TQuote tQuote = tQuotes.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_order_check_item,
					null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.state = (ImageView) convertView
					.findViewById(R.id.tv_order_state);
			holder.title = (TextView) convertView
					.findViewById(R.id.tv_order_title);
			holder.expectPrice = (TextView) convertView
					.findViewById(R.id.tv_expect_price);
			holder.orderNumber = (TextView) convertView
					.findViewById(R.id.tv_order_number);
			holder.orderTime = (TextView) convertView
					.findViewById(R.id.tv_order_time);
		}
		holder.expectPrice.setText("裸车价：￥"+tQuote.getFloorPrice()+"元");
		holder.title.setText(tQuote.getCarDetail());
		if (tQuote.getBegindateStr() != null) {
			holder.orderTime.setText(tQuote.getBegindateStr());
		} else {
			holder.orderTime.setText("。。。");
		}

		if (tQuote.isIsUserPay()) {
			holder.state.setBackground(context.getResources().getDrawable(
					R.drawable.isok));
		} else {
			holder.state.setBackground(context.getResources().getDrawable(
					R.drawable.wait_ok));
		}
		
		holder.orderNumber.setText("您的返利：￥300元");
		return convertView;
	}

	@Override
	public void onClick(View v) {

	}

	/** 存放控件 */
	public final class ViewHolder {
		public ImageView icon;
		public TextView title;
		public TextView expectPrice;
		public TextView orderNumber;
		public TextView orderTime;
		public ImageView state;
	}
}

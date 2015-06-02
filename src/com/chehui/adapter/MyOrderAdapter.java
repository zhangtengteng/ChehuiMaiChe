package com.chehui.adapter;

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
 * @author zhangtengteng
 *
 */
public class MyOrderAdapter extends BaseAdapter implements OnClickListener {
	private List<TQuote> tQuotes;
	private Context context;
	private LayoutInflater inflater;
	FinalBitmap finalBitmap;
	ViewHolder holder = null;
	public MyOrderAdapter(List<TQuote> tuanSigns, Context context) {
		super();
		this.tQuotes = tuanSigns;
		this.context = context;
		inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
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
			holder.title = (TextView) convertView
					.findViewById(R.id.tv_order_title);
			holder.expectPrice = (TextView) convertView
					.findViewById(R.id.tv_expect_price);
			holder.orderNumber = (TextView) convertView
					.findViewById(R.id.tv_order_number);
			holder.orderTime = (TextView) convertView
					.findViewById(R.id.tv_order_time);
		}
		
		holder.expectPrice.setText(tQuote.getLicensePrice());
		holder.title.setText(tQuote.getCarDetail());
		if(tQuote.getBegindateStr()!=null){
			holder.orderTime.setText(tQuote.getBegindateStr());
		}else{
			holder.orderTime.setText("。。。");
		}
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
	}
}

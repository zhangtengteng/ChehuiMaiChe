package com.chehui.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import chehui.maichetong.selleroperationservice.TOrder;

import com.chehui.afinal.FinalBitmap;
import com.example.myproject.R;
/**
 * 询价列表 adapter
 * @author zhangtengteng
 *
 */
public class OrderAdapter extends BaseAdapter {
	private List<TOrder> list= new ArrayList<TOrder>();
	private LayoutInflater inflater;
	private Context context;
	FinalBitmap finalBitmap;
	
	public OrderAdapter(List<TOrder> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}
	public OrderAdapter(Context context) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}
		
	public void  setData(List<TOrder> list){
		this.list= list;
	}
	public List<TOrder>  getData(){
		return this.list;
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

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		if(list.size()<=0){
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.listview_no_item, null);
//			}
//			
//			return convertView;
//		}
		TOrder tOrder = list.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_order_check_item, null);
		}
			
		TextView title = (TextView) convertView.findViewById(R.id.tv_order_title);
		TextView expectPrice = (TextView) convertView.findViewById(R.id.tv_expect_price);
		//订单编号改成付款方式
		TextView orderNumber = (TextView) convertView.findViewById(R.id.tv_order_number);
		orderNumber.setText(tOrder.getPayMode());
		
		TextView time = (TextView) convertView.findViewById(R.id.tv_order_time);
		
		title.setText(tOrder.getSeriesName()+tOrder.getCarName());
		expectPrice.setText("期望价："+tOrder.getInsurancePrice());
		
		String begindateStr = tOrder.getBegindateStr();
		
		if(begindateStr==null){
			time.setText("拿不到值");
		}else{
			time.setText(begindateStr);
		}
		
		return convertView;
	}
}

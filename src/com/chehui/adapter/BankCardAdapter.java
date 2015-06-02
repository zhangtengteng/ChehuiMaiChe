package com.chehui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import chehui.maichetong.bankservice.TBankCard;
import chehui.maichetong.selleroperationservice.TBusiness;

import com.chehui.afinal.FinalBitmap;
import com.example.myproject.R;

/**
 * 银行卡列表 adapter
 * 
 * @author zhangtengteng
 * 
 */
public class BankCardAdapter extends BaseAdapter {
	private List<TBankCard> list;
	private LayoutInflater inflater;
	private Context context;
	FinalBitmap finalBitmap;

	public BankCardAdapter(List<TBankCard> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}

	public BankCardAdapter(Context context) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
	}

	public void setData(List<TBankCard> list) {
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
		TBankCard bankCard = list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_banck_card_item, null);
			holder = new ViewHolder();
			holder.cardName = (TextView) convertView
					.findViewById(R.id.tv_bank_card_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.cardName.setText(bankCard.getBankName() + " "
				+ bankCard.getBankCardNum());
		return convertView;
	}

	class ViewHolder {
		TextView cardName;
	}
}

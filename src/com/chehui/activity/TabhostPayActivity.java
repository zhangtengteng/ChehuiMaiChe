package com.chehui.activity;

/****
 * tabhost 已支付订单页面
 */
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.chehui.adapter.OrderPayAdapter;
import com.chehui.afinal.utils.JSONUtil;
import com.chehui.autolistview.widget.AutoListView;
import com.chehui.autolistview.widget.AutoListView.OnLoadListener;
import com.chehui.autolistview.widget.AutoListView.OnRefreshListener;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.model.GoodsModel;
import com.chehui.net.OrderAccountRequest;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class TabhostPayActivity extends BaseActivity implements
		OnRefreshListener, OnLoadListener {
	private AutoListView lstv;
	private OrderPayAdapter adapter;
	private List<GoodsModel> list = new ArrayList<GoodsModel>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			List<GoodsModel> result = doAfterSuccessLv1(msg.obj.toString());
			switch (msg.what) {
			case 0:
				Toast.makeText(TabhostPayActivity.this,msg.obj.toString(), 2000).show();
				break;
			// 成功
			case 1:
				if(msg.arg1==AutoListView.LOAD){
					lstv.onLoadComplete();
					adapter.getData().addAll(result);
				}else if(msg.arg1==AutoListView.REFRESH){
					lstv.onRefreshComplete();
					list.clear();
					list.addAll(result);
				}else{
					Toast.makeText(TabhostPayActivity.this, R.string.error, 2000).show();
				}
			
				break;
			default:
				break;
			}
			// dismissWaitDialog();
			lstv.setResultSize(result.size());
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost_pay);
		init();
	}

	private void init() {
		lstv = (AutoListView) findViewById(R.id.lv_pay);
		adapter = new OrderPayAdapter(list, this);
		lstv.setAdapter(adapter);
		lstv.setOnRefreshListener(this);
		lstv.setOnLoadListener(this);
		getData(CommonData.TIME_ALL, "1", CommonData.pageSize + "",
				AutoListView.REFRESH);
	}

	/***
	 * 获取数据成功响应
	 * 
	 * @param str
	 * @return
	 */
	private List<GoodsModel> doAfterSuccessLv1(String str) {
		List<GoodsModel> goodsModel = new ArrayList<GoodsModel>();
		try {
			JSONObject o = new JSONObject(str);
			String result = o.getString("result");
			if ("1".equals(result)) {
				JSONArray dataArray = o.getJSONArray("data");
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject obj = dataArray.getJSONObject(i);
					GoodsModel model = null;
					try {
						model = (GoodsModel) JSONUtil.fromJsonToJava(obj,
								GoodsModel.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					goodsModel.add(model);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return goodsModel;
	}

	@Override
	public void onLoad() {
		getData(CommonData.TIME_ALL, (CommonData.currentPage + 1) + "",
				CommonData.pageSize + "", AutoListView.LOAD);// 支付
	}

	@Override
	public void onRefresh() {
		getData(CommonData.TIME_ALL, "1",
				CommonData.pageSize + "", AutoListView.REFRESH);// 支付
	}

	/***
	 * 
	 * @param time
	 *            0.全部、1.一个月之内2.一个星期之内
	 * @param nextpage
	 * @param pageCount
	 * @param type
	 *            刷新 | 加载
	 */
	private void getData(final String time, final String nextpage,
			final String pageCount, final int type) {
		ExtractorThread.getInstance().getMainHandler().post(new Runnable() {

			@Override
			public void run() {
				OrderAccountRequest orderAccountRequest = new OrderAccountRequest(
						handler);

				SharedPreferences sharedPreferences = TabhostPayActivity.this
						.getSharedPreferences("chehui", Activity.MODE_PRIVATE);

				String name = sharedPreferences.getString("username", "");
				orderAccountRequest.setParams(name, CommonData.PAY, time,
						nextpage, pageCount, type);
				orderAccountRequest.sendRequest();
				// showWaitDialog(R.string.common_querying);
			}
		});

	}

}
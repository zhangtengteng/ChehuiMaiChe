package com.chehui.activity;

/****
 * 我的订单页面  one
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import chehui.maichetong.selleroperationservice.ResultOfListOfTQuote;
import chehui.maichetong.selleroperationservice.TQuote;

import com.chehui.adapter.MyOrderAdapter;
import com.chehui.autolistview.widget.AutoListView;
import com.chehui.autolistview.widget.AutoListView.OnLoadListener;
import com.chehui.autolistview.widget.AutoListView.OnRefreshListener;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manage.comm.RequestTimeOutManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

public class ViewPageOneActivity extends BaseActivity implements
		OnRefreshListener, OnLoadListener {
	private boolean flag;
	private AutoListView lstv;
	private MyOrderAdapter adapter;
	private List<TQuote> tQuotes;
	private List<TQuote> tQuotesByTime;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				if (msg.obj!= null) {
					ToastUtils.showShortToast(getApplicationContext()
							.getApplicationContext(), msg.obj.toString());
				} else {
					ToastUtils.showShortToast(getApplicationContext()
							.getApplicationContext(), "服务器异常!");
				}
				lstv.onRefreshComplete();
				lstv.onLoadComplete();
				adapter.notifyDataSetChanged();
				break;
			case AutoListView.REFRESH:
				if (adapter == null) {
					adapter = new MyOrderAdapter(tQuotes,
							getApplicationContext());
					lstv.setAdapter(adapter);
				}
				lstv.onRefreshComplete();
				break;
			case AutoListView.LOAD:
				if (adapter == null) {
					adapter = new MyOrderAdapter(tQuotes,
							getApplicationContext());
					lstv.setAdapter(adapter);
				}
				lstv.onLoadComplete();
				break;
			case CommonData.HTTP_TIME_OUT:
				dismissWaitDialog();
				if (msg.obj != null) {
					Integer type = Integer.valueOf(msg.obj.toString());
					RequestTimeOutManager.getInstance().refushAutoListView(
							getApplicationContext(), lstv, adapter, type);
				}
				break;

			default:
				break;
			}
			flag = false;
			lstv.setResultSize(tQuotes.size());
			adapter.notifyDataSetChanged();
			dismissWaitDialog();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost_pay);
		init();
	}

	private void init() {
		lstv = (AutoListView) findViewById(R.id.lv_pay);
		lstv.setOnRefreshListener(this);
		lstv.setOnLoadListener(this);
		// getData("1",AutoListView.REFRESH);
		// lstv.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View view, int position,
		// long arg3) {
		// Intent intent = new
		// Intent(getApplicationContext(),MyOrderDetailActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putSerializable("myOrderDetail", list.get(position-1));
		// intent.putExtras(bundle);
		// intent.putExtra("isTemp", false);
		// activityManager.getInstance().startNextActivity(intent,MyOrderDetailActivity.class);
		// }
		// });

		getData("1", AutoListView.LOAD, AutoListView.LOAD);
	}

	@Override
	public void onLoad() {
		getData("1", AutoListView.LOAD, AutoListView.LOAD);
		RequestTimeOutManager.getInstance().startTimerTask(flag, handler,
				AutoListView.LOAD);
	}

	@Override
	public void onRefresh() {
		getData("1", AutoListView.REFRESH, AutoListView.REFRESH);
		RequestTimeOutManager.getInstance().startTimerTask(flag, handler,
				AutoListView.REFRESH);
	}

	private void getData(final String state, final int what, final int id
			) {
		flag = true;
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			Message message = handler.obtainMessage();

			@Override
			public void run() {
				try {
					ResultOfListOfTQuote queteByID = WebServiceManger
							.getInstance().getSellerOperationService()
							.getQueteByID("1", "");
					if (queteByID.isSuccess()) {
						message.what = what;
						tQuotes = queteByID.getData().getTQuote();
						relaceStr(tQuotes, 1);
					} else {
						message.obj = queteByID.getMess();
						message.what = CommonData.HTTP_HANDLE_FAILE;
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					
				}
			}

		});

	}

	protected void relaceStr(List<TQuote> tQuotes2, int timeType) {
		for (int i = 0; i < tQuotes2.size(); i++) {

			try {
				String begindateStr = tQuotes2.get(i).getBegindateStr();
				begindateStr = begindateStr.replace("年", "-");
				Log.d("time", begindateStr);
				begindateStr = begindateStr.replace("月", "-");
				Log.d("time", begindateStr);
				begindateStr = begindateStr.replace("日", "");
				Log.d("time", begindateStr);
				//2015-01-01
				//2015-1-1
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
				Date date = sdf.parse(begindateStr);
				pagingByTime(1, date);
				tQuotes2.get(i).setBegindateStr(begindateStr);
				Log.d("time", "修改后的：" + tQuotes2.get(i).getBegindateStr());
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		tQuotes = tQuotes2;
	}

	/***
	 * 根据时间分页
	 */
	private void pagingByTime(int timeType, Date date) {
		if (timeType == 1) {
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			// 一个月
			long dmm = curDate.getTime() - date.getTime();
			int d = (int) dmm / 1000;
			Log.d("time", "秒 "+d);
		} else if (timeType == 2) {
			// 一个星期

		} else {
			// 全部
			tQuotesByTime = tQuotes;
		}
	}
}
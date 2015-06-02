package com.chehui.activity;

/****
 * 询价订单
 */
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.chehui.autolistview.widget.AutoListView;
import com.chehui.autolistview.widget.AutoListView.OnLoadListener;
import com.chehui.autolistview.widget.AutoListView.OnRefreshListener;
import com.chehui.comm.ExtractorThread;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class OrderActivity extends BaseActivity implements
		OnRefreshListener, OnLoadListener {
	private AutoListView lstv;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
		
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost_pay);
		init();
	}

	private void init() {
		lstv = (AutoListView) findViewById(R.id.list_order);
//		adapter = new MyOrderAdapter(list, this);
//		lstv.setAdapter(adapter);
		lstv.setOnRefreshListener(this);
		lstv.setOnLoadListener(this);
		getData("1",AutoListView.REFRESH);
//		lstv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position,
//					long arg3) {
//				Intent intent = new Intent(getApplicationContext(),MyOrderDetailActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("myOrderDetail", list.get(position-1));
//				intent.putExtras(bundle);
//				intent.putExtra("isTemp", false);
//				activityManager.getInstance().startNextActivity(intent,MyOrderDetailActivity.class);
//			}
//		});
	}
	@Override
	public void onLoad() {
		getData("1",AutoListView.LOAD);
	}
	@Override
	public void onRefresh() {
		getData("1",AutoListView.REFRESH);
	}

	
	private void getData(final String state,final int i) {
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				Message message = new Message();
				message.arg1=i;
//				//SharedPreManager.getInstance().getString(CommonData.USER_NAME, "")
//				ResultOfListOfTTuanSign allTuanSignByUser = WebServiceManger.getInstance().getJWService().getAllTuanSignByUser("1");
//				if(allTuanSignByUser.isSuccess()){
//					message.what=1;
//					List<TTuanSign> result  = allTuanSignByUser.getData().getTTuanSign();
//					message.obj=result;
//				}else{
//					message.what=0;
//					message.obj=allTuanSignByUser.getMess();
//				}
//					handler.sendMessage(message);
			}
		});

	}

}
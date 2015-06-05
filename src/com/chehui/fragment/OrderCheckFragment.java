package com.chehui.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import chehui.maichetong.selleroperationservice.ResultOfListOfTOrder;
import chehui.maichetong.selleroperationservice.TOrder;

import com.chehui.activity.OrderDetailActivity;
import com.chehui.activity.OrderDetailActivity2;
import com.chehui.adapter.OrderAdapter;
import com.chehui.autolistview.widget.AutoListView;
import com.chehui.autolistview.widget.AutoListView.OnLoadListener;
import com.chehui.autolistview.widget.AutoListView.OnRefreshListener;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manage.comm.RequestTimeOutManager;
import com.chehui.ui.base.BaseFragment;
import com.chehui.utils.ToastUtils;
import com.chehui.utils.Utils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

/***
 * 询价列表
 * 
 * @author zhangtengteng
 * 
 */
public class OrderCheckFragment extends BaseFragment implements
		OnRefreshListener, OnLoadListener {
	private TOrder orderDetail;
	private AutoListView listView;
	private OrderAdapter adapter;
	private boolean flag;
	private Timer timer;
	private List<TOrder> tOrder = new ArrayList<TOrder>();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				if (msg.obj!= null) {
					ToastUtils.showShortToast(getActivity()
							.getApplicationContext(), msg.obj.toString());
				} else {
					ToastUtils.showShortToast(getActivity()
							.getApplicationContext(), "服务器异常!");
				}
				break;
			case CommonData.HTTP_HANDLE_SUCCESS:
				onGetOrderResponse();
				break;
			case AutoListView.REFRESH:
				if (adapter == null) {
					adapter = new OrderAdapter(tOrder, getActivity()
							.getApplicationContext());
					listView.setAdapter(adapter);
				}
				listView.onRefreshComplete();
				listView.setResultSize(tOrder.size());
				break;
			case AutoListView.LOAD:
				if (adapter == null) {
					adapter = new OrderAdapter(tOrder, getActivity()
							.getApplicationContext());
					listView.setAdapter(adapter);
				}
				listView.onLoadComplete();
				listView.setResultSize(tOrder.size());
				break;

			case CommonData.HTTP_TIME_OUT:
				dismissWaitDialog();
				if (msg.obj != null) {
					Integer type = Integer.valueOf(msg.obj.toString());
					RequestTimeOutManager.getInstance().refushAutoListView(
							getActivity(), listView, adapter, type);
				}
				break;
			default:
				break;
			}
			flag = false;
			dismissWaitDialog();
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.fragment_order_check, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		getMyOrder(AutoListView.REFRESH);
	}

	/**
	 * 获取询价列表
	 * 
	 * @param what
	 */
	private void getMyOrder(final int what) {
		showWaitDialog(R.string.common_requesting);
		flag = true;
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {

				Message message = handler.obtainMessage();

				try {
					ResultOfListOfTOrder orderByBrands = WebServiceManger
							.getInstance().getSellerOperationService()
							.getOrderByBrands("1", "2", "3");
					if (orderByBrands.isSuccess()) {
						message.what = what;
						tOrder = orderByBrands.getData().getTOrder();
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = orderByBrands.getMess();
					}
				} catch (Exception e) {
					e.printStackTrace();
					dismissWaitDialog();
				}
				handler.sendMessage(message);
			}
		});
	}

	private void onGetOrderResponse() {
		if (orderDetail != null) {
			 
//			String str=orderDetail.getState()+"|"+orderDetail.getDdbh()+"|"+orderDetail.get

			 Intent intent = new Intent(getActivity().getApplicationContext(),OrderDetailActivity2.class);
			 intent.putExtra("orderDetail", (Serializable) orderDetail);
			 startActivity(intent);
			 
			 

		}

	}

	private void init() {
		// timer = new Timer();
		listView = (AutoListView) getActivity().findViewById(R.id.list_order);
		listView.setOnRefreshListener(this);
		listView.setOnLoadListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TOrder order = tOrder.get(position - 1);
				getOrderDetailRequest(tOrder.get(position - 1).getOrderID());
			}
		});

	}

	@Override
	public void onLoad() {
		// loadData(AutoListView.LOAD);
		getMyOrder(AutoListView.LOAD);
	}

	@Override
	public void onRefresh() {
		// loadData(AutoListView.REFRESH);
		getMyOrder(AutoListView.REFRESH);
		RequestTimeOutManager.getInstance().startTimerTask(flag, handler,
				AutoListView.REFRESH);

		// TimerTask timerTask = new TimerTask() {
		// @Override
		// public void run() {
		// if (!flag) {
		// Message message = handler.obtainMessage();
		// message.what = CommonData.HTTP_TIME_OUT;
		// message.obj = AutoListView.REFRESH;
		// handler.sendMessage(message);
		// }
		// }
		// };
		//
		// timer.schedule(timerTask, CommonData.TIME_OUT);
	}

	/***
	 * 获取我的询价列表
	 * 
	 * @param orderId
	 */
	private void getOrderDetailRequest(final int orderId) {
		if (!Utils.isNetworkAvailable(OrderCheckFragment.this.getActivity())) {
			ToastUtils.showShortToast(getActivity(),
					R.string.common_network_unavalible);
			return;
		}
		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				Message message = handler.obtainMessage();
				try {
					ResultOfListOfTOrder orderByID = WebServiceManger
							.getInstance().getSellerOperationService()
							.getOrderByID(String.valueOf(orderId));
					if (orderByID.isSuccess()) {
						orderDetail = orderByID.getData().getTOrder().get(0);
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = orderByID.getMess();
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					
				}
			}
		});
	}

}

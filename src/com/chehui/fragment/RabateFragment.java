package com.chehui.fragment;

import java.util.List;

import org.jinouts.ws.exception.ExceptionConstants;

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

import chehui.maichetong.selleroperationservice.ResultOfListOfTBusiness;
import chehui.maichetong.selleroperationservice.TBusiness;

import com.chehui.activity.RabateDetailActivity;
import com.chehui.adapter.OrderAdapter;
import com.chehui.adapter.RabateAdapter;
import com.chehui.autolistview.widget.AutoListView;
import com.chehui.autolistview.widget.AutoListView.OnLoadListener;
import com.chehui.autolistview.widget.AutoListView.OnRefreshListener;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manage.comm.RequestTimeOutManager;
import com.chehui.ui.base.BaseFragment;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

/***
 * 返利
 * 
 * @author zhangtengteng
 * 
 */
public class RabateFragment extends BaseFragment
		implements
			OnRefreshListener,
			OnLoadListener {
	private boolean isRequesting;
	private AutoListView autoListView;
	private RabateAdapter adapter;
	private List<TBusiness> tBusiness;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case CommonData.HTTP_HANDLE_FAILE :
					if (msg.obj != null) {
						ToastUtils.showShortToast(getActivity()
								.getApplicationContext(), msg.obj.toString());
					} else {
						ToastUtils.showShortToast(getActivity()
								.getApplicationContext(), "服务器异常!");
					}

					break;
				case CommonData.HTTP_HANDLE_SUCCESS :
					break;
				case AutoListView.REFRESH :
					if (adapter == null) {
						adapter = new RabateAdapter(tBusiness, getActivity());
						autoListView.setAdapter(adapter);
					}
					autoListView.onRefreshComplete();
					break;
				case AutoListView.LOAD :
					if (adapter == null) {
						adapter = new RabateAdapter(tBusiness, getActivity());
						autoListView.setAdapter(adapter);
					}
					autoListView.onLoadComplete();
					break;

				case CommonData.HTTP_TIME_OUT :
					dismissWaitDialog();
					if (msg.obj != null) {
						Integer type = Integer.valueOf(msg.obj.toString());
						RequestTimeOutManager.getInstance().refushAutoListView(
								getActivity(), autoListView, adapter, type);
					}
					break;
				default :
					break;
			}
			isRequesting = false;
			autoListView.setResultSize(tBusiness.size());
			adapter.notifyDataSetChanged();
			dismissWaitDialog();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_rabate, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		autoListView = (AutoListView) getActivity().findViewById(
				R.id.list_rabate);
		autoListView.setOnRefreshListener(this);
		autoListView.setOnLoadListener(this);
		autoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				TBusiness business = tBusiness.get(position - 1);
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), RabateDetailActivity.class);
				intent.putExtra("state", business.getState());
				intent.putExtra("money", business.getReturnMoney());
				intent.putExtra("number", business.getBusinessID());
				// intent.putExtra("time", business.getReturnMoney());
				startActivity(intent);
			}
		});
		getRabateRequest(AutoListView.REFRESH);
	}

	/***
	 * 获取返利
	 */
	private void getRabateRequest(final int what) {
		isRequesting = true;
		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					ResultOfListOfTBusiness myBusiness = WebServiceManger
							.getInstance().getSellerOperationService()
							.getMyBusiness("1");
					Message message = handler.obtainMessage();
					if (myBusiness.isSuccess()) {
						message.what = what;
						tBusiness = myBusiness.getData().getTBusiness();
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = myBusiness.getMess();
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
					dismissWaitDialog();
				}
			}
		});

	}
	
	@Override
	public void onLoad() {
		getRabateRequest(AutoListView.LOAD);
		RequestTimeOutManager.getInstance().startTimerTask(isRequesting,
				handler, AutoListView.LOAD);
	}

	@Override
	public void onRefresh() {
		getRabateRequest(AutoListView.REFRESH);
		RequestTimeOutManager.getInstance().startTimerTask(isRequesting,
				handler, AutoListView.REFRESH);
	}

}

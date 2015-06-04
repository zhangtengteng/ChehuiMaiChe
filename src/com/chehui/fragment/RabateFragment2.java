package com.chehui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import chehui.maichetong.bankservice.ResultOfTSellerSum;
import chehui.maichetong.bankservice.TSellerSum;

import com.chehui.activity.ActivityManagerAccountWallet3;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manager.comm.BankManager;
import com.chehui.ui.base.BaseFragment;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

/***
 * 返利2
 * 
 * @author zhangtengteng
 * 
 */
public class RabateFragment2 extends BaseFragment implements OnClickListener {
	private TSellerSum seller;
	private TextView balance;
	private Button btnBalance;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				if (msg.obj.toString() != null) {
					ToastUtils.showShortToast(getActivity(),
							msg.obj.toString());
				} else {
					ToastUtils.showShortToast(getActivity(),
							R.string.common_abnormal_server);
				}
				break;
			case CommonData.HTTP_HANDLE_SUCCESS:
				onSuccessResponse();
				break;
			default:
				break;
			}
			dismissWaitDialog();
		}

	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_rabate2, container, false);
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		getMyBalanceRequest();
	}
	
	private void initViews() {
		balance = (TextView) getActivity().findViewById(R.id.tv_balance);
		btnBalance = (Button) getActivity().findViewById(R.id.btn_balance);
		btnBalance.setOnClickListener(this);
	}
	/***
	 * 获取我余额
	 */
	private void getMyBalanceRequest() {
		showWaitDialog(R.string.common_request_no_title);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				try {
					// 获取余额
					ResultOfTSellerSum sellerSum = WebServiceManger
							.getInstance().getBankService().getSellerSum("1");
					Message message = handler.obtainMessage();
					if (sellerSum.isSuccess()) {
						seller = sellerSum.getData();
						BankManager.getInstance().setBalance(seller.getMyMoney());
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = sellerSum.getMess();
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
					dismissWaitDialog();
				}
			}
		});
	}

	private void onSuccessResponse() {
		balance.setText("￥"+seller.getMyMoney());
//		Intent intent = new Intent();
//		intent.putExtra("balance", seller.getMyMoney());
//		// intent.putExtra("bankCards",(Serializable)bankCards);
//		activityManager.getInstance().startNextActivity(intent,
//				ActivityManagerAccountWallet1.class);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_balance :
				startActivity(new Intent(getActivity(),ActivityManagerAccountWallet3.class));
				break;

			default :
				break;
		}
	}
}

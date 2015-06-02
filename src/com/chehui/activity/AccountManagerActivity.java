package com.chehui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import chehui.maichetong.bankservice.ResultOfTSellerSum;
import chehui.maichetong.bankservice.TBankCard;
import chehui.maichetong.bankservice.TSellerSum;

import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manager.comm.BankManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

/**
 * 个人信息 我的钱包页面
 * 
 * @author zhangtengteng
 * 
 */
public class AccountManagerActivity extends BaseActivity implements
		OnClickListener {
	private LinearLayout llUser;
	private LinearLayout llUserBalance;
	private TSellerSum seller;
	private List<TBankCard> bankCards;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				if (msg.obj.toString() != null) {
					ToastUtils.showShortToast(getApplicationContext(),
							msg.obj.toString());
				} else {
					ToastUtils.showShortToast(getApplicationContext(),
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account);
		init();
		initTitleView(-1, 255, R.string.set_account, 255, -1, 0);
	}

	private void init() {
		llUser = (LinearLayout) findViewById(R.id.ll_user);
		llUser.setOnClickListener(this);
		llUserBalance = (LinearLayout) findViewById(R.id.ll_user_balance);
		llUserBalance.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_user:
			activityManager.getInstance().startNextActivity(
					ActivityManagerAccountPersion.class);
			break;
		case R.id.ll_user_balance:
			getMyBalanceRequest();
			break;
		default:
			break;
		}
	}

	/***
	 * 获取我余额
	 */
	private void getMyBalanceRequest() {
		showWaitDialog(R.string.common_requesting);
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
		Intent intent = new Intent();
		intent.putExtra("balance", seller.getMyMoney());
		// intent.putExtra("bankCards",(Serializable)bankCards);
		activityManager.getInstance().startNextActivity(intent,
				ActivityManagerAccountWallet1.class);
	}
}

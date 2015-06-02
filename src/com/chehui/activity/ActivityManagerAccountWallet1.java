package com.chehui.activity;

/****
 * 我的钱包页面1 显示余额 银行卡列表
 */

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import chehui.maichetong.bankservice.ResultOfListOfTBankCard;
import chehui.maichetong.bankservice.TBankCard;

import com.chehui.adapter.BankCardAdapter;
import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manager.comm.BankManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

public class ActivityManagerAccountWallet1 extends BaseActivity implements
		OnClickListener {
	private TextView tvMoney;
	private LinearLayout llAddBank;
	private LinearLayout llMOney;
	private ListView listView;
	int money;
	BankCardAdapter adapter;
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

		private void onSuccessResponse() {
			if (adapter == null) {
				adapter = new BankCardAdapter(bankCards,
						getApplicationContext());
				listView.setAdapter(adapter);
			} else {
				adapter.setData(bankCards);
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account_wallet1);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		init();
	}

	private void init() {
		tvMoney = (TextView) findViewById(R.id.tv_money);
		tvMoney.setText(String.valueOf(BankManager.getInstance().getBalance()));

		llAddBank = (LinearLayout) findViewById(R.id.ll_add_bank);
		llAddBank.setOnClickListener(this);
		llMOney = (LinearLayout) findViewById(R.id.ll_money);
		llMOney.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.lv_bank_card);
		getBankCardListRequest();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 添加银行卡
		case R.id.ll_add_bank:
			activityManager.getInstance().startNextActivity(
					ActivityManagerAccountAddBank1.class);
			break;
		// 查询余额
		case R.id.ll_money:
			Intent intent = new Intent();
			intent.putExtra("balance", money);
			activityManager.getInstance().startNextActivity(
					ActivityManagerAccountWallet2.class);
			break;
		default:
			break;
		}
	}

	private void getBankCardListRequest() {
		showWaitDialog(R.string.common_requesting);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {
			@Override
			public void run() {
				try {
					ResultOfListOfTBankCard bankCardList = WebServiceManger
							.getInstance().getBankService()
							.getBankCardList("1");
					Message message = handler.obtainMessage();
					if (bankCardList.isSuccess()) {
						bankCards = bankCardList.getData().getTBankCard();
						BankManager.getInstance().setBankCards(bankCards);
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = bankCardList.getMess();
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
	protected void onResume() {
		super.onResume();
		getBankCardListRequest();
	}
}

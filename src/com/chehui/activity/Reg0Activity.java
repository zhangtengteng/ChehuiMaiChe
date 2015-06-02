package com.chehui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import chehui.maichetong.messageservice.ResultOfBoolean;

import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.diy.EditTextWithDel;
import com.chehui.manage.comm.SharedPreManager;
import com.chehui.manager.comm.RegisterManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.StringUtils;
import com.chehui.utils.ToastUtils;
import com.chehui.utils.Utils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

/**
 * 注册发送手机验证码
 * 
 * @author zhangtengteng
 * 
 */
public class Reg0Activity extends BaseActivity {
	private Button btnSendCode;
	private Button btnNext;
	EditTextWithDel phone;
	EditTextWithDel code;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_FAILE:
				if (msg.obj.toString() != null) {
					ToastUtils.showShortToast(getApplicationContext(),
							msg.obj.toString());
				} else {
					ToastUtils
							.showShortToast(getApplicationContext(), "服务器异常!");
				}
				break;
			case CommonData.HTTP_HANDLE_SUCCESS:
				onSendCodeSuccessResponse();
				break;
			default:
				break;
			}
		}
	};
	private TimeCount timeCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg0);
		initTitleView(-1, 255, R.string.reg, 255, -1, 0);
		init();
	}

	protected void onSendCodeSuccessResponse() {
		ToastUtils.showShortToast(getApplicationContext(), "发送成功！");
	}

	private void init() {
		phone = (EditTextWithDel) findViewById(R.id.et_phone);
		code = (EditTextWithDel) findViewById(R.id.et_code);

		timeCount = new TimeCount(30000, 1000);

		btnSendCode = (Button) findViewById(R.id.btn_send_code);
		btnSendCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				sendCode();
				// RegisterManager.getInstance().setUserName(
				// userName.getText().toString().trim());
				//
				// RegisterManager.getInstance().setPassWord(
				// pwd.getText().toString().trim());
				//
				// activityManager.getInstance().startNextActivity(
				// Reg2Activity.class);
			}

		});

		btnNext = (Button) findViewById(R.id.btn_next0);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				activityManager.getInstance().startNextActivity(
						Reg2Activity.class);
			}
		});

	}

	/***
	 * 发送手机验证码
	 */
	private void sendCode() {

		if (StringUtils.isEmpty(phone.getText().toString())) {
			ToastUtils.showShortToast(getApplicationContext(), "手机号不能为空！");
			return;
		}
		if (!Utils.isNetworkAvailable(this)) {
			ToastUtils.showShortToast(getApplicationContext(),
					R.string.common_network_unavalible);
			return;
		}
//		SharedPreManager.getInstance().setString(CommonData.USER_PHONE, phone.getText().toString());
		RegisterManager.getInstance().setTel(phone.getText().toString());
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {

			@Override
			public void run() {
				try {
					// 开始倒计时
					timeCount.start();
					ToastUtils.showShortToast(getApplicationContext(), phone
							.getText().toString());
					ResultOfBoolean validCode = WebServiceManger.getInstance()
							.getMessageService()
							.getValidCode(phone.getText().toString());
					Message message = handler.obtainMessage();
					if (validCode.isSuccess()) {
						message.what = CommonData.HTTP_HANDLE_SUCCESS;
					} else {
						message.what = CommonData.HTTP_HANDLE_FAILE;
						message.obj = validCode.getMess();
					}
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
					dismissWaitDialog();
				}
			}
		});

	}

	/**
	 * 倒计时
	 * 
	 * @author zhangtengteng
	 * 
	 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btnSendCode.setText("重新验证");
			btnSendCode.setClickable(true);
			btnSendCode.setBackground(getResources().getDrawable(
					R.drawable.corners_bg_reg0_bule));

		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btnSendCode.setBackground(getResources().getDrawable(
					R.drawable.corners_bg_reg0_gray));
			btnSendCode.setClickable(false);
			btnSendCode.setText(millisUntilFinished / 1000 + "秒");
		}
	}
}

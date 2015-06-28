package com.chehui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;
public class ActivityCertifiedMerchants2 extends BaseActivity {
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_cretified_photograph, null);
		setContentView(parentView);
		init();
		initTitleView(-1, 255, R.string.set_account_money, 255, -1, 0);
	}

	private void init() {
		findViewById(R.id.btn_next1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activityManager.getInstance().startNextActivity(ActivityCertifiedMerchants3.class);
			}
		});
	}

}

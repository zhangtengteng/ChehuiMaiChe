package com.chehui.activity;
/****
 *loading 3
 */
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.chehui.ui.base.BaseActivity;
import com.chehui.update.UpdateManager;
import com.example.myproject.R;

public class ViewPager3Activity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_viewpager3);
		init();
	}
	private void init() {
		findViewById(R.id.iv_star).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		activityManager.startNextActivity(LoginActivity.class);
	}


}

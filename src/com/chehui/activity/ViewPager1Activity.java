package com.chehui.activity;
/****
 *loading 1
 */
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.chehui.ui.base.BaseActivity;
import com.chehui.update.UpdateManager;
import com.example.myproject.R;

public class ViewPager1Activity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_viewpager1);
		init();
	}
	private void init() {
		
	}
	@Override
	public void onClick(View v) {
		
	}


}

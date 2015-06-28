package com.chehui.activity;
/****
 * 报价成功！
 */
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.chehui.ui.base.BaseActivity;
import com.chehui.update.UpdateManager;
import com.example.myproject.R;

public class ActivityOrderDetailOk extends BaseActivity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail_baojia_ok);
		initTitleView(-1, 0, R.string.main_order, 255, -1, 0);
		init();
	}

	private void init() {
		findViewById(R.id.btn_next4).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ActivityOrderDetailOk.this.finish();
			}
		});
	}


}

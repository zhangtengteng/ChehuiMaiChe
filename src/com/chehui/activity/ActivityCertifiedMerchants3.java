package com.chehui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityCertifiedMerchants3 extends BaseActivity {
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private ImageView iv1, iv2;
	private ImageView currentIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(
				R.layout.activity_cretified_upload, null);
		setContentView(parentView);
		init();
		initTitleView(-1, 255, R.string.set_account_money, 255, -1, 0);
	}

	private void init() {
		iv1 = (ImageView) findViewById(R.id.iv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		findViewById(R.id.btn_next1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activityManager.getInstance().startNextActivity(
						ActivityCertifiedMerchants4.class);
			}
		});
		iv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				currentIv = iv1;
				if (!pop.isShowing()) {
					pop.showAtLocation(parentView, Gravity.CENTER, 0, 0);
				}
			}
		});
		iv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				currentIv = iv2;
				if (!pop.isShowing()) {
					pop.showAtLocation(parentView, Gravity.CENTER, 0, 0);
				}
			}
		});

		pop = new PopupWindow(ActivityCertifiedMerchants3.this);
		// ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				// ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				// ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Intent intent = new Intent(ActivityCertifiedMerchants2.this,
				// AlbumActivity.class);
				// startActivity(intent);
				// overridePendingTransition(R.anim.activity_translate_in,
				// R.anim.activity_translate_out);
				pop.dismiss();
				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				// ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				// ll_popup.clearAnimation();
			}
		});

		findViewById(R.id.btn_next1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activityManager.getInstance().startNextActivity(
						ActivityCertifiedMerchants4.class);
			}
		});
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PICTURE:
			if (resultCode == RESULT_OK) {

				Bitmap bm = (Bitmap) data.getExtras().get("data");
				currentIv.setImageBitmap(bm);
				currentIv.setBackground(null);
				// FileUtils.saveBitmap(bm, fileName);
				//
				// ImageItem takePhoto = new ImageItem();
				// takePhoto.setBitmap(bm);
				// Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		pop.dismiss();
	}
}

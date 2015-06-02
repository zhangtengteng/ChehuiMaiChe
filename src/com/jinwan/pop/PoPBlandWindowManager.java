package com.jinwan.pop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import chehui.maichetong.useroperationservice.ResultOfListOfTBrand;
import chehui.maichetong.useroperationservice.TBrand;

import com.chehui.comm.ExtractorThread;
import com.chehui.diy.PickerView;
import com.chehui.diy.PickerView.onSelectListener;
import com.chehui.ui.widget.dialog.WaitingAlertDialog;
import com.chehui.utils.LogN;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

public class PoPBlandWindowManager {
	private PopupWindow pop;
	private View popView;
	private PickerView pickerView;
	private static volatile PoPBlandWindowManager instance;
	List<TBrand> tBrand;
	List<String> data = new ArrayList<String>();
	private Context context;

	private PoPBlandWindowManager() {
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static PoPBlandWindowManager getInstance() {
		if (instance == null) {
			instance = new PoPBlandWindowManager();
		}
		return instance;
	}

	/***
	 * popWindow初始化方法
	 * 
	 * @param context
	 * @param w
	 */
	public void init(Context context, final int width, int height, int id) {
		this.context = context;
		// 创建PopupWindow对象
		pop = new PopupWindow(setPopView(context, id),
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
		setPopWidth(context, width, height);
		// 需要设置一下此参数，点击外边可消失
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);

	}

	public PopupWindow getPopView() {
		return pop;
	}

	public View setPopView(Context context, int id) {
		if (popView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			// 引入窗口配置文件
			popView = inflater.inflate(id, null);

			popView.getBackground().setAlpha(110);
		}
		return popView;
	}

	public void showPopAsDropDown(View view, int x, int y) {
		if (pop != null) {
			pop.showAsDropDown(view, x, y);
		} else {
			LogN.e(this, "popWindow is null!!!");
		}
	}

	public void showPopAllLocation(View parent, int gravity, int x, int y) {
		if (pop != null) {
			pop.showAtLocation(parent, gravity, x, y);
		} else {
			LogN.e(this, "popWindow is null!!!");
		}
	}

	public void dismissPop() {
		pop.dismiss();
	}

	public void setPopWidth(Context context, final int width, final int height) {
		if (popView == null) {
			LogN.e(this, "popView is null !!!");
			return;
		}
		popView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 动态设置pop的宽度
						FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) popView
								.getLayoutParams();
						linearParams.width = width;
						linearParams.height = height;
					}
				});
	}

	public void changeOnClick(View view, OnClickListener onClickListener) {

	}

	public void changeOnSelect(onSelectListener selectListener) {
		pickerView.setOnSelectListener(selectListener);
	}

	public void setPickViewData(List<String> data) {
		this.data = data;
		if (pickerView == null) {
			pickerView = (PickerView) popView.findViewById(R.id.minute_pv);
		}
		pickerView.setData(data);
	}
}

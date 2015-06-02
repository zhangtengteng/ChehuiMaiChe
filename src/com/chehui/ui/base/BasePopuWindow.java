package com.chehui.ui.base;

import com.jinwan.pop.PoPBlandWindowManager;

import android.view.View;
import android.widget.PopupWindow;

public class BasePopuWindow {
	//popuwindow对象
	private PopupWindow pop;
	//popuwindow view
	private View popView;
	private static volatile BasePopuWindow instance;
	
	
	private BasePopuWindow() {
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static BasePopuWindow getInstance() {
		if (instance == null) {
			instance = new BasePopuWindow();
		}
		return instance;
	}
}

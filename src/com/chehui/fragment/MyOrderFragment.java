package com.chehui.fragment;

/****
 * 我的询价
 */
import java.util.ArrayList;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chehu.pop.PoPTimeManager;
import com.chehui.activity.ViewPageOneActivity;
import com.chehui.adapter.MyPagerAdapter;
import com.chehui.autolistview.widget.AutoListView.OnLoadListener;
import com.chehui.autolistview.widget.AutoListView.OnRefreshListener;
import com.chehui.ui.base.BaseFragment;
import com.chehui.utils.ToastUtils;
import com.example.myproject.R;

public class MyOrderFragment extends BaseFragment implements OnClickListener {
	private RadioButton rb1, rb2, rb3;
	private int flag;
	ViewPager pager = null;
	@SuppressWarnings("deprecation")
	LocalActivityManager manager = null;
	private TextView popTextOne;
	private TextView popTextTwo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.viewpager_my_order, container, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		manager = new LocalActivityManager(getActivity(), true);
		manager.dispatchCreate(savedInstanceState);
		init();
		initPagerViewer();
	}

	private void init() {

		rb1 = (RadioButton) getActivity().findViewById(R.id.rb1);
		rb2 = (RadioButton) getActivity().findViewById(R.id.rb2);
		rb3 = (RadioButton) getActivity().findViewById(R.id.rb3);
		rb1.setOnClickListener(this);
		rb2.setOnClickListener(this);
		rb3.setOnClickListener(this);

		PoPTimeManager.getInstance().init(getActivity(), rb2.getWidth());
		PoPTimeManager.getInstance().changeOnClick(this);
		popTextOne = PoPTimeManager.getInstance().getPopTextOne();
		popTextTwo = PoPTimeManager.getInstance().getPopTextTwo();
	}

	/**
	 * 初始化PageViewer
	 */
	private void initPagerViewer() {
		pager = (ViewPager) getActivity().findViewById(R.id.vp_myorder);
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(getActivity(), ViewPageOneActivity.class);
		list.add(getView("0", intent));
		Intent intent2 = new Intent(getActivity(), ViewPageOneActivity.class);
		list.add(getView("1", intent2));
		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				System.out.println("onPageSelected=" + position);
				if (position == 0) {
					rb1.setChecked(true);
				}

				if (position == 1) {
					rb3.setChecked(true);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb1:
			flag=0;
			pager.setCurrentItem(0);
			break;
		case R.id.rb3:
			flag=1;
			pager.setCurrentItem(1);
			break;
		case R.id.rb2:
			if (flag == 0) {
				rb1.setChecked(true);
			}
			if (flag == 1) {
				rb3.setChecked(true);
			}
			showPopWindow();
			break;
		case R.id.tv_one:
			changePopTitle(Integer.valueOf(popTextOne.getTag().toString()));
			break;
		case R.id.tv_two:
			changePopTitle(Integer.valueOf(popTextTwo.getTag().toString()));
			break;
		default:
			break;
		}
	}

	private void changePopTitle(Integer i) {
		if (i == 1) {
			CharSequence odlText = rb2.getText();
			CharSequence newText = popTextOne.getText();
			popTextOne.setText(odlText);
			rb2.setText(newText);
		} else if (i == 2) {
			CharSequence odlText = rb2.getText();
			CharSequence newText = popTextTwo.getText();
			popTextTwo.setText(odlText);
			rb2.setText(newText);
		}
		
		PoPTimeManager.getInstance().disMissPop();
	}

	/**
	 * 显示选择时间的popWindow
	 */
	private void showPopWindow() {
		PoPTimeManager.getInstance().setPopWidth(getActivity(), rb2.getWidth());
		PoPTimeManager.getInstance().showPop(rb2, 0, 0);
	}

}

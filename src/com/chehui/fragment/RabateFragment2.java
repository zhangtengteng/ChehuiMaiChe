package com.chehui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chehui.ui.base.BaseFragment;
import com.example.myproject.R;

/***
 * 返利2
 * 
 * @author zhangtengteng
 * 
 */
public class RabateFragment2 extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_rabate2, container, false);
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}

package com.chehui.fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chehui.ui.base.BaseFragment;
import com.example.myproject.R;

public class AddressFragment extends BaseFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.address_fragment, container, false);
	}
	
}

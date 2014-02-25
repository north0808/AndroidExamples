package com.yt.prefence;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;

public class MyPreferenveCatgory extends PreferenceCategory {

	public MyPreferenveCatgory(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setLayoutResource(R.layout.wireless_item_s);
	}


}

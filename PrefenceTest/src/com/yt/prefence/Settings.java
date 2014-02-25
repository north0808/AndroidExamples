package com.yt.prefence;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class Settings extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		//去除title   
		//requestWindowFeature(Window.FEATURE_NO_TITLE);  
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity);
		addPreferencesFromResource(R.xml.settings);
		
		ListView listView = getListView();
		listView.setCacheColorHint(android.graphics.Color.TRANSPARENT);
		listView.setBackgroundResource(R.drawable.s0_buddy_add_detail_bg);
		listView.setDividerHeight(1);
		listView.setDivider(getResources().getDrawable(R.drawable.list_line));
		
		/*PreferenceCategory preferenceCategory = (PreferenceCategory)findPreference("pcate");
		preferenceCategory.setLayoutResource(R.layout.wireless_item_s);*/
		
		/*ListPreference listPreference = (ListPreference)findPreference("num_channels");
		listPreference.setLayoutResource(R.layout.preference);*/
		
		CheckBoxPreference checkBoxPreference =(CheckBoxPreference)findPreference("check1");
		checkBoxPreference.setLayoutResource(R.layout.preference);
		
		Preference preferencema =(Preference)findPreference("mac_address");
		preferencema.setLayoutResource(R.layout.preference);
		
		CheckBoxPreference checkBoxPreference1 =(CheckBoxPreference)findPreference("use_static_ip");
		checkBoxPreference1.setLayoutResource(R.layout.preference);
		
		CheckBoxPreference checkBoxPreference2 =(CheckBoxPreference)findPreference("b");
		checkBoxPreference2.setLayoutResource(R.layout.preference);
		
		CheckBoxPreference checkBoxPreference3 =(CheckBoxPreference)findPreference("c");
		checkBoxPreference3.setLayoutResource(R.layout.preference);
		
		EditTextPreference editTextPreferenceadd =(EditTextPreference)findPreference("ip_address");
		editTextPreferenceadd.setLayoutResource(R.layout.preference);
		
		EditTextPreference editTextPreferenceps =(EditTextPreference)findPreference("gateway");
		editTextPreferenceps.setLayoutResource(R.layout.preference);
		
		EditTextPreference editTextPreferenms =(EditTextPreference)findPreference("netmask");
		editTextPreferenms.setLayoutResource(R.layout.preference);
		
		EditTextPreference editTextPreferencedns =(EditTextPreference)findPreference("dns1");
		editTextPreferencedns.setLayoutResource(R.layout.preference);
		
		EditTextPreference editTextPreferencedsn2 =(EditTextPreference)findPreference("dns2");
		editTextPreferencedsn2.setLayoutResource(R.layout.preference);
		
	}
     
}

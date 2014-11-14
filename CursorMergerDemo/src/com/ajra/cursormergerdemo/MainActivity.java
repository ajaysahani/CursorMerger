package com.ajra.cursormergerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ajra.cursormargerdemo.R;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private Button regularInterval;
	private Button userDefineInterval;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		regularInterval = (Button) findViewById(R.id.regularInterval);
		userDefineInterval = (Button) findViewById(R.id.userDefineInterval);
		regularInterval.setOnClickListener(this);
		userDefineInterval.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regularInterval:
			openRegularIntervalDemo();
			break;

		case R.id.userDefineInterval:
			openUserDefineIntervalDemo();
			break;
		default:
			break;
		}
	}

	private void openRegularIntervalDemo() {
		openActivity(RegularIntervalDemo.class);
	}

	private void openUserDefineIntervalDemo() {
		openActivity(UserDefinePositionDemo.class);
	}

	@SuppressWarnings("rawtypes")
	private void openActivity(Class myclass) {
		Intent intent = new Intent(getApplicationContext(), myclass);
		startActivity(intent);
	}
}

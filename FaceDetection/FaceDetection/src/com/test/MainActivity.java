package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button b;
	String filename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
b=(Button)findViewById(R.id.detct);
//detect.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		filename="try.png";
		return true;
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
	}
	public void openReg(View view)
    {

    	Intent intent = new Intent(this, FaceDetectionActivity.class);
    	intent.putExtra("File", filename);
    	startActivity(intent);
    }
	public void openCompare(View view)
    {

    }

}

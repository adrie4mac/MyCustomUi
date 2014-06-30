package com.mobileminted.customui;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.mobileminted.customui.imageview.FlipImageView;
import com.mobileminted.customui.imageview.FlipNetworkImageView;
import com.mobileminted.customui.models.FlipNetworkImageViewModel;

public class ExampleFlipImageView extends Activity
{

	private FlipImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		iv = (FlipImageView)findViewById(R.id.flipimageview);
//		iv.setDrawable(getResources().getDrawable(R.drawable.imgdummy1));
//		iv.setDrawable2(getResources().getDrawable(R.drawable.imgdummy2));
	}

}

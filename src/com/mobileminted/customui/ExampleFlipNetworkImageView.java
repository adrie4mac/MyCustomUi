package com.mobileminted.customui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.mobileminted.customui.imageview.FlipNetworkImageView;
import com.mobileminted.customui.models.FlipNetworkImageViewModel;

public class ExampleFlipNetworkImageView extends Activity
{

	private FlipNetworkImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exmp_flipnetworkiv);
		
//		iv = (FlipNetworkImageView)findViewById(R.id.imageviewnetwork);
//		String[] listimg = {"http://mobileminted.com/tecong/pict/women/mix/Chu%20Chu%2019.jpg",
//							"http://mobileminted.com/tecong/pict/women/mix/Dong%20Xuan%205.jpg",
//							"http://mobileminted.com/tecong/pict/women/mix/Jiang%20Yan%20126.jpg",
//							"http://mobileminted.com/tecong/pict/women/mix/New%20New%2005.jpg",
//							"http://mobileminted.com/tecong/pict/women/mix/ZhaoYufei-hexun-004_003.jpg",
//						   };	
//		
//		iv.downloadListImages(listimg);
//		iv.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				FlipNetworkImageViewModel mdl = iv.getActiveImageModel();
//				Toast.makeText(getApplicationContext(), "val "+mdl.getImageUri(), Toast.LENGTH_SHORT).show();	
//			}
//		});
		
	}


}

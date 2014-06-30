package com.mobileminted.customui;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.mobileminted.customui.compoundview.BannerView;
import com.mobileminted.customui.imageview.FlipImageView;
import com.mobileminted.customui.imageview.FlipNetworkImageView;
import com.mobileminted.customui.models.BannerModel;
import com.mobileminted.customui.models.FlipNetworkImageViewModel;
import com.mobileminted.customui.progress.DrawableAnimLoading;
import com.mobileminted.customui.progress.LoadingRotateImage;

public class MainActivity extends Activity
{

//	private BannerView banner;
//	private FlipNetworkImageView iv;
//	private FlipNetworkImageView iv2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);

//		LoadingRotateImage x = new LoadingRotateImage(this,R.style.CustomDialogTheme,R.drawable.img_prg6,"Loading your data");
//		x.setCancelable(true);
//		x.setCanceledOnTouchOutside(false);
//		x.setOnCancelListener(new DialogInterface.OnCancelListener()
//		{
//			@Override
//			public void onCancel(DialogInterface dialog)
//			{
//				
//			}
//		});
//		x.show();
		
//		DrawableAnimLoading x = new DrawableAnimLoading(this,R.style.CustomDialogTheme,R.drawable.loadimage,"Loading your data");
//		x.setCancelable(true);
//		x.setCanceledOnTouchOutside(false);
//		x.setOnCancelListener(new DialogInterface.OnCancelListener()
//		{
//			@Override
//			public void onCancel(DialogInterface dialog)
//			{
//				
//			}
//		});
//		x.show();
		
		
//		Dialog d = new Dialog(this,R.style.CustomAlertDialog);
//		d.setContentView(R.layout.dlg_progress);
//		d.show();
		
		
		
//		List<BannerModel> list = new ArrayList<BannerModel>();
//		list.add(new BannerModel("Title 1", "subtitle 1", "http://i.imgur.com/0usQek0.jpg", "Link1"));
//		list.add(new BannerModel("Title 2", "subtitle 2", "http://i.imgur.com/EkAb07H.jpg", "Link2"));
//		list.add(new BannerModel("Title 3", "subtitle 3", "http://i.imgur.com/Acs3Nyd.jpg", "Link3"));
//		list.add(new BannerModel("Title 4", "subtitle 4", "http://i.imgur.com/1V1b1Md.jpg", "Link4"));
//		list.add(new BannerModel("Title 5", "subtitle 5", "http://i.imgur.com/EQwaw7U.jpg", "Link5"));
//		
//		banner = (BannerView)findViewById(R.id.banner1);
//		
//		banner.downloadAllBannerImages(list);
		
//		setContentView(R.layout.exmp_flipnetworkiv);
//		
//		iv = (FlipNetworkImageView)findViewById(R.id.imageviewnetwork);
//		List<FlipNetworkImageViewModel> list = new ArrayList<FlipNetworkImageViewModel>();
//		list.add(new FlipNetworkImageViewModel("http://i.imgur.com/D5mHYJ6.jpg", "Link1"));
//		list.add(new FlipNetworkImageViewModel("http://i.imgur.com/Q2pG0P3.jpg", "Link2"));
//		list.add(new FlipNetworkImageViewModel("http://i.imgur.com/NGoP0c8.jpg", "Link3"));
//		list.add(new FlipNetworkImageViewModel("http://i.imgur.com/Fz1twm4.jpg", "Link4"));
//		list.add(new FlipNetworkImageViewModel("http://i.imgur.com/ym45z98.jpg", "Link5"));
//		
//		iv.downloadListImages(list);
//		iv.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				FlipNetworkImageViewModel mdl = iv.getActiveImageModel();
//				Toast.makeText(getApplicationContext(), "val "+mdl.getUrlLink(), Toast.LENGTH_SHORT).show();	
//			}
//		});
//		
//		iv2 = (FlipNetworkImageView)findViewById(R.id.imageviewnetwork2);
//		List<FlipNetworkImageViewModel> list2 = new ArrayList<FlipNetworkImageViewModel>();
//		list2.add(new FlipNetworkImageViewModel("http://i.imgur.com/fg804M6.jpg", "Link1b"));
//		list2.add(new FlipNetworkImageViewModel("http://i.imgur.com/vfdXeqV.jpg", "Link2b"));
//		list2.add(new FlipNetworkImageViewModel("http://i.imgur.com/h9pasgl.jpg", "Link3b"));
//		
//		iv2.downloadListImages(list2);
//		iv2.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				FlipNetworkImageViewModel mdl = iv2.getActiveImageModel();
//				Toast.makeText(getApplicationContext(), "val "+mdl.getUrlLink(), Toast.LENGTH_SHORT).show();	
//			}
//		});
		
		
		
		
	}

}

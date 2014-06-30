package com.mobileminted.customui.models;

import android.graphics.drawable.Drawable;

public class FlipNetworkImageViewModel
{
	private Drawable drawable;
	private String imageUri;
	private String urlLink;

	public FlipNetworkImageViewModel(String urlImage,String urlLink)
	{
		super();
		this.imageUri = urlImage;
		this.urlLink = urlLink;
	}
	
	public FlipNetworkImageViewModel(Drawable image,String urlLink)
	{
		super();
		this.drawable = image;
		this.urlLink = urlLink;
	}

	public Drawable getDrawable()
	{
		return drawable;
	}

	public String getImageUri()
	{
		return imageUri;
	}

	public String getUrlLink()
	{
		return urlLink;
	}
	
	
	
	
	
	
}

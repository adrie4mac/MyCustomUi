package com.mobileminted.customui.models;

import android.graphics.drawable.Drawable;

public class BannerModel
{
	private String title;
	private String subtitle;
	private String urlImage;
	private Drawable image;
	private String urlLink;
	public BannerModel(String title,String subtitle,String urlImage,String urlLink)
	{
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.urlImage = urlImage;
		this.image = image;
		this.urlLink = urlLink;
	}
	
	public BannerModel(String title,String subtitle,Drawable image,String urlLink)
	{
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.urlImage = urlImage;
		this.image = image;
		this.urlLink = urlLink;
	}
	
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getSubtitle()
	{
		return subtitle;
	}
	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
	}
	public String getUrlImage()
	{
		return urlImage;
	}
	public void setUrlImage(String urlImage)
	{
		this.urlImage = urlImage;
	}
	public Drawable getImage()
	{
		return image;
	}
	public void setImage(Drawable image)
	{
		this.image = image;
	}
	public String getUrlLink()
	{
		return urlLink;
	}
	public void setUrlLink(String urlLink)
	{
		this.urlLink = urlLink;
	}
	
	
	
}

package com.mobileminted.customui.compoundview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.mobileminted.customui.R;
import com.mobileminted.customui.models.BannerModel;
import com.mobileminted.customui.models.FlipNetworkImageViewModel;
import com.mobileminted.customui.toolsui.CustomScaleMatrixImageView;
import com.mobileminted.customui.toolsui.CustomScaleMatrixImageView.CustomScaleTypes;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ViewSwitcher;

public class BannerView extends RelativeLayout
{
	public static final int TITLE_LAYOUT_POSITION_TOP = 0;
	public static final int TITLE_LAYOUT_POSITION_BOTTOM = 1;
	
	public static final int ANIM_SLIDING = 0;
	public static final int ANIM_FADE = 1;
	public static final int ANIM_SLIDING_FADE = 2;
	public static final int ANIM_GROWSHRINK = 3;
	public static final int ANIM_FLIP_HORIZONTAL = 4;
	public static final int ANIM_FLIP_VERTICAL = 5;
	public static final int ANIM_GROWIN_SHRINKOUT = 6;
	
	
	public static final int INDICATOR_TOP_INSIDE = 0;
	public static final int INDICATOR_BOTTOM_INSIDE = 1;
	public static final int INDICATOR_BOTTOM_OFF = 2;
	
	public static final int INDICATOR_ITEM_LEFT = 0;
	public static final int INDICATOR_ITEM_CENTRE = 1;
	public static final int INDICATOR_ITEM_RIGHT = 2;
	
	private Context ctx;
	private View v;
	private int titleLayoutPost = 0;
	private boolean isUsingBgOnTitleLayout = true;
	private int titleLayoutColor = getResources().getColor(R.color.clear);
	private String title = "";
	private String subTitle = "";
	private int titleColor = getResources().getColor(android.R.color.black);
	private int subTitleColor = getResources().getColor(android.R.color.darker_gray);
	private Drawable bannerImage = getResources().getDrawable(R.drawable.ic_launcher);
	private int animationCode = 0;
	private ScaleType imageScaleType = ImageView.ScaleType.MATRIX;
	private int bannerSize = 4;
	private boolean isUsingIndicator = true;
	private boolean isUsingNumberIndicator = false;
	private int indicatorPos = 1;
	private int indicatorItemPos = 2;
	private int indicatorBgColor = getResources().getColor(R.color.clear);
	private Drawable indicatorNormal = getResources().getDrawable(R.drawable.bn_indi1);
	private Drawable indicatorSelected = getResources().getDrawable(R.drawable.bn_indi1);
	private int indicatorNumberNormalColor = getResources().getColor(R.color.gray_light);
	private int indicatorNumberSelectedColor = getResources().getColor(R.color.white);
	
	private RelativeLayout layoutParent;
	private LinearLayout layoutTitle;
	private LinearLayout layoutIndicator;
	private TextView tvTitle,tvSubTitle;
	private ImageSwitcher imageSwitcher;
	
    private boolean isAutomaticRun = true;
	private int changeInterval = 3000;
    
	
	private ImageLoader imgLoader = ImageLoader.getInstance(); 
	private DisplayImageOptions option;
	
	
	private List<BannerModel> listBannerCompleted = new ArrayList<BannerModel>();


	public void setAnimationCode(int animationCode)
	{
		this.animationCode = animationCode;
	}


	public BannerView(Context _context, AttributeSet attrs)
	{
		super(_context, attrs);
		
		if(!isInEditMode())
		{
			ctx = _context;
			handler = new Handler();
			parseAttributes(_context,_context.obtainStyledAttributes(attrs,R.styleable.BannerView));
			
			
			
			option = new DisplayImageOptions.Builder()
			.cacheOnDisc(true)
			.cacheInMemory(true)
			.showImageOnLoading(R.drawable.ic_launcher)
			.imageScaleType(ImageScaleType.EXACTLY)
			.build();
		}
	}
	
	
	
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
//		invalidate();
	}

	@SuppressWarnings("deprecation")
	private void parseAttributes(Context context,TypedArray a)
	{
		titleLayoutPost = a.getInteger(R.styleable.BannerView_TitleLayoutPosition, titleLayoutPost);
		title = a.getString(R.styleable.BannerView_Title);
		subTitle = a.getString(R.styleable.BannerView_SubTitle);
		titleColor = a.getInteger(R.styleable.BannerView_TitleColor, titleColor);
		subTitleColor = a.getInteger(R.styleable.BannerView_SubTitleColor, subTitleColor);
		bannerImage = a.getDrawable(R.styleable.BannerView_BannerImage);
		isUsingBgOnTitleLayout = a.getBoolean(R.styleable.BannerView_UsingBgOnTitleLayout, isUsingBgOnTitleLayout);
		titleLayoutColor = a.getInteger(R.styleable.BannerView_TitleLayoutColour, titleLayoutColor);
		isAutomaticRun = a.getBoolean(R.styleable.BannerView_AutomaticRun, isAutomaticRun);
		changeInterval = a.getInteger(R.styleable.BannerView_ChangeInterval,changeInterval);
		animationCode = a.getInteger(R.styleable.BannerView_Animation, animationCode);
		bannerSize = a.getInteger(R.styleable.BannerView_BannerSize, bannerSize);
		isUsingIndicator = a.getBoolean(R.styleable.BannerView_IsUsingIndicator, isUsingIndicator);
		isUsingNumberIndicator = a.getBoolean(R.styleable.BannerView_IsUsingNumberIndicator, isUsingNumberIndicator);
		indicatorPos = a.getInteger(R.styleable.BannerView_IndicatorPosition, indicatorPos);
		indicatorItemPos = a.getInteger(R.styleable.BannerView_IndicatorItemPosition, indicatorItemPos);
		indicatorBgColor = a.getInteger(R.styleable.BannerView_IndicatorBgColor, indicatorBgColor);
		indicatorNormal = a.getDrawable(R.styleable.BannerView_IndicatorImageNormal);
		indicatorSelected = a.getDrawable(R.styleable.BannerView_IndicatorImageSelected);
		indicatorNumberNormalColor = a.getInteger(R.styleable.BannerView_IndicatorNumberNormalColor, indicatorNumberNormalColor);
		indicatorNumberSelectedColor = a.getInteger(R.styleable.BannerView_IndicatorNumberSelectedColor, indicatorNumberSelectedColor);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.cv_banner, this,true);
		
		imageSwitcher = (ImageSwitcher)v.findViewById(R.id.bannerImageSwitcher);
		layoutParent = (RelativeLayout)v.findViewById(R.id.parentLayout);
		calculateBannerHeightSize();
		
		layoutTitle = (LinearLayout)v.findViewById(R.id.layoutTitle);
		tvTitle = (TextView)v.findViewById(R.id.tvBannerTitle);
		tvSubTitle = (TextView)v.findViewById(R.id.tvSubBannerTitle);
		layoutIndicator = (LinearLayout)v.findViewById(R.id.layoutIndicator);
		layoutIndicator.setBackgroundColor(indicatorBgColor);
		
		imageSwitcher.setFactory(new ViewSwitcher.ViewFactory()
		{
			@Override
			public View makeView()
			{
//				ImageView imageView = new ImageView(ctx);
//			    imageView.setScaleType(imageScaleType);
//			    imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//			    return imageView;
				
				CustomScaleMatrixImageView imageView = new CustomScaleMatrixImageView(ctx);
				imageView.setScaleType(imageScaleType);
				imageView.setCustomScaleType(CustomScaleTypes.CROP_TOP_WIDTH);
//				imageView.startYAxis = -100f;
			    imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			    return imageView;
				
			}
		});
		
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		if(titleLayoutPost == TITLE_LAYOUT_POSITION_TOP)	
		{
			param.addRule(RelativeLayout.ALIGN_TOP,R.id.bannerImageSwitcher);
		}
		else if(titleLayoutPost == TITLE_LAYOUT_POSITION_BOTTOM)	
		{
			param.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.bannerImageSwitcher);
		}
			
		layoutTitle.setLayoutParams(param);
		if(isUsingBgOnTitleLayout)
			layoutTitle.setBackgroundColor(titleLayoutColor);
		else
			layoutTitle.setBackgroundColor(getResources().getColor(R.color.clear));
		
		imageSwitcher.setImageDrawable(bannerImage);
		tvTitle.setText(title);
		tvTitle.setTextColor(titleColor);
		tvSubTitle.setText(subTitle);
		tvSubTitle.setTextColor(subTitleColor);
		
		a.recycle();
	}
	
	private int drawIndex = 0;
	private void changeBanner(int animCode)
	{
    	if(drawIndex == listBannerCompleted.size())
    		drawIndex = 0;
    	
    	Animation slideIn = AnimationUtils.loadAnimation(ctx, R.anim.banner_slide_in_left);
    	Animation slideOut = AnimationUtils.loadAnimation(ctx, R.anim.banner_slide_out_right);
    	Animation fadeIn = AnimationUtils.loadAnimation(ctx, R.anim.banner_fade_in);
    	Animation fadeOut = AnimationUtils.loadAnimation(ctx, R.anim.banner_fade_out);
    	Animation growing = AnimationUtils.loadAnimation(ctx, R.anim.growing_from_middle);
    	Animation shrink = AnimationUtils.loadAnimation(ctx, R.anim.shrink_to_middle);
    	Animation growIn = AnimationUtils.loadAnimation(ctx, R.anim.growing_in_left);
    	Animation shrinkOut = AnimationUtils.loadAnimation(ctx, R.anim.shrink_out_right);
    	Animation flipInHorizontal = AnimationUtils.loadAnimation(ctx, R.anim.flip_horizontal_in);
    	Animation flipOutHorizontal = AnimationUtils.loadAnimation(ctx, R.anim.flip_horizontal_out);
    	Animation flipInVertical = AnimationUtils.loadAnimation(ctx, R.anim.flip_vertical_in);
    	Animation flipOutVertical = AnimationUtils.loadAnimation(ctx, R.anim.flip_vertical_out);
    	
    	AnimationSet animSetIn = new AnimationSet(false);
    	AnimationSet animSetOut = new AnimationSet(false);
    	


		switch(animCode)
		{
			case ANIM_SLIDING : 
				imageSwitcher.setInAnimation(slideIn);
		    	imageSwitcher.setOutAnimation(slideOut);
				break;
			case ANIM_FADE : 
				imageSwitcher.setInAnimation(fadeIn);
		    	imageSwitcher.setOutAnimation(fadeOut);
				break;
			case ANIM_SLIDING_FADE :
				animSetIn.addAnimation(slideIn);
		    	animSetIn.addAnimation(fadeIn);
		    	animSetOut.addAnimation(fadeOut);
		    	animSetOut.addAnimation(slideOut);
		    	
				imageSwitcher.setInAnimation(animSetIn);
		    	imageSwitcher.setOutAnimation(animSetOut);
				break;
			case ANIM_GROWSHRINK :
				imageSwitcher.setInAnimation(growing);
		    	imageSwitcher.setOutAnimation(shrink);
			case ANIM_GROWIN_SHRINKOUT :	
				imageSwitcher.setInAnimation(growIn);
		    	imageSwitcher.setOutAnimation(shrinkOut);
				break;
			case ANIM_FLIP_HORIZONTAL :
				imageSwitcher.setInAnimation(flipInHorizontal);
		    	imageSwitcher.setOutAnimation(flipOutHorizontal);
				break;
			case ANIM_FLIP_VERTICAL :
				imageSwitcher.setInAnimation(flipInVertical);
		    	imageSwitcher.setOutAnimation(flipOutVertical);
		    	break;
		}
		
		imageSwitcher.setImageDrawable(listBannerCompleted.get(drawIndex).getImage());
		
		tvTitle.setText(listBannerCompleted.get(drawIndex).getTitle());
		tvSubTitle.setText(listBannerCompleted.get(drawIndex).getSubtitle());
		
		tvTitle.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				bannerClicked(v,listBannerCompleted.get(drawIndex-1));
			}
		});
		tvSubTitle.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				bannerClicked(v,listBannerCompleted.get(drawIndex-1));
			}
		});
		imageSwitcher.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				bannerClicked(v,listBannerCompleted.get(drawIndex-1));
			}
		});
		
		if(isUsingIndicator)
		{
			if(isUsingNumberIndicator)
			{
				if(isUsingIndicator)
				{
					for(TextView tv : listIndicatorText)
					{
						tv.setTypeface(null,Typeface.NORMAL);
						tv.setTextColor(indicatorNumberNormalColor);
					}
					listIndicatorText.get(drawIndex).setTypeface(null,Typeface.BOLD);
					listIndicatorText.get(drawIndex).setTextColor(indicatorNumberSelectedColor);
				}
			}
			else
			{
				for(ImageView iv : listIndicator)
				{
					iv.setImageDrawable(indicatorNormal);
	//				iv.setImageDrawable(getResources().getDrawable(R.drawable.bn_indi1));
				}
	//			listIndicator.get(drawIndex).setImageDrawable(getResources().getDrawable(R.drawable.bn_indi2));
				listIndicator.get(drawIndex).setImageDrawable(indicatorSelected);
			}
		}
		
		drawIndex++;
	}
	
	private OnBannerClickListener onBannerClickListener;
	public interface OnBannerClickListener
	{
	    public void bannerClicked(View v2, BannerModel bannerModel);
	}
	
	public void setOnBannerClickListener(OnBannerClickListener callback)
	{
		this.onBannerClickListener = callback;
    }
	
    protected void bannerClicked(View v2, BannerModel bannerModel)
	{
    	onBannerClickListener.bannerClicked(v2,bannerModel);
	}

	private Handler handler;
    Runnable runBanner = new Runnable()
	{
		@Override
		public void run()
		{
			changeBanner(animationCode);
			handler.postDelayed(runBanner, changeInterval);
		}
	};
	
	int x = 0;
	private List<ImageView> listIndicator = new ArrayList<ImageView>();
	private List<TextView> listIndicatorText = new ArrayList<TextView>();
	private List<BannerModel> listTemp;
	public void downloadAllBannerImages(List<BannerModel> list)
    {
		listTemp = list;
    	for(BannerModel mdl : list)
    	{
    		ImageSize targetSize = new ImageSize(this.getWidth(), this.getHeight());
        	imgLoader.loadImage(mdl.getUrlImage(), targetSize, option, new SimpleImageLoadingListener() 
        	{
        		
        		@Override
				public void onLoadingStarted(String imageUri,View view)
				{
        			
				}
        		
				BannerModel m;
				@SuppressWarnings("deprecation")
				@Override
        	    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
        	    {
					m = findItem(imageUri);
					listBannerCompleted.add(new BannerModel(m.getTitle(),m.getSubtitle(),new BitmapDrawable(getResources(), loadedImage),m.getUrlLink()));
					
					if(isUsingIndicator)
					{
						if(isUsingNumberIndicator)
						{
							x++;
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							int margin = (int)ctx.getResources().getDimension(R.dimen.bannerNumberIndicatorMargin);
							params.setMargins(margin,0,margin,0);
							TextView tv = new TextView(ctx);
							tv.setTag(x);
							tv.setLayoutParams(params);
							tv.setText(String.valueOf(x));
							tv.setTypeface(null,Typeface.NORMAL);
							tv.setTextColor(indicatorNumberNormalColor);
							tv.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.numer_rect_bg));
							tv.setOnClickListener(new View.OnClickListener()
							{
								@Override
								public void onClick(View v)
								{
									drawIndex = Integer.parseInt(v.getTag().toString())-1;
									imageSwitcher.setImageDrawable(listBannerCompleted.get(drawIndex).getImage());
									if(isUsingIndicator)
									{
										for(TextView tv : listIndicatorText)
										{
											tv.setTypeface(null,Typeface.NORMAL);
											tv.setTextColor(indicatorNumberNormalColor);
										}
										listIndicatorText.get(drawIndex).setTypeface(null,Typeface.BOLD);
										listIndicatorText.get(drawIndex).setTextColor(indicatorNumberSelectedColor);
									}
									drawIndex++;
								}
							});
							layoutIndicator.addView(tv);
							listIndicatorText.add(tv);
						}
						else
						{
							x++;
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							ImageView iv = new ImageView(ctx);
							iv.setTag(x);
							iv.setLayoutParams(params);
							iv.setImageDrawable(indicatorNormal);
							iv.setOnClickListener(new View.OnClickListener()
							{
								@Override
								public void onClick(View v)
								{
									Log.i("_______",v.getTag().toString()+"");
									drawIndex = Integer.parseInt(v.getTag().toString())-1;
									imageSwitcher.setImageDrawable(listBannerCompleted.get(drawIndex).getImage());
									if(isUsingIndicator)
									{
										for(ImageView iv : listIndicator)
										{
											iv.setImageDrawable(indicatorNormal);
	//										iv.setImageDrawable(getResources().getDrawable(R.drawable.bn_indi1));
										}
	//									listIndicator.get(drawIndex).setImageDrawable(getResources().getDrawable(R.drawable.bn_indi2));
										listIndicator.get(drawIndex).setImageDrawable(indicatorSelected);
									}
									drawIndex++;
								}
							});
							layoutIndicator.addView(iv);
							listIndicator.add(iv);
						}
						
						RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
						switch(indicatorPos)
						{
							case INDICATOR_TOP_INSIDE : param.addRule(RelativeLayout.ALIGN_TOP,R.id.bannerImageSwitcher);break;
							case INDICATOR_BOTTOM_INSIDE : param.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.bannerImageSwitcher);break;
							case INDICATOR_BOTTOM_OFF : param.addRule(RelativeLayout.BELOW,R.id.bannerImageSwitcher);break;
						}
						layoutIndicator.setLayoutParams(param);
						
						switch(indicatorItemPos)
						{
							case INDICATOR_ITEM_LEFT : layoutIndicator.setGravity(Gravity.LEFT);break;
							case INDICATOR_ITEM_CENTRE : layoutIndicator.setGravity(Gravity.CENTER_HORIZONTAL);break;
							case INDICATOR_ITEM_RIGHT : layoutIndicator.setGravity(Gravity.RIGHT);break;
						}
						
						
						RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
						if(titleLayoutPost == TITLE_LAYOUT_POSITION_TOP)	
						{
							param1.addRule(RelativeLayout.BELOW,R.id.layoutIndicator);
						}
						else if(titleLayoutPost == TITLE_LAYOUT_POSITION_BOTTOM)	
						{
							param1.addRule(RelativeLayout.ABOVE,R.id.layoutIndicator);
						}
						layoutTitle.setLayoutParams(param1);
					}
					
					if(isAutomaticRun)
        	    	{
	        	    	if(listBannerCompleted.size() == 0)
	        	    	{
	        	    		imageSwitcher.setImageDrawable(listBannerCompleted.get(0).getImage());
	        	    		tvTitle.setText(listBannerCompleted.get(0).getTitle());
	        	    		tvSubTitle.setText(listBannerCompleted.get(0).getSubtitle());
	        	    	}
	        	    	else if(listBannerCompleted.size() == 1)
	        	    	{
	        	    		runBanner.run();
	        	    	}
        	    	}
        	    	else
        	    	{
        	    		imageSwitcher.setImageDrawable(listBannerCompleted.get(0).getImage());
        	    		tvTitle.setText(listBannerCompleted.get(0).getTitle());
        	    		tvSubTitle.setText(listBannerCompleted.get(0).getSubtitle());
        	    	}
        	    }
        	});
        	
        	
    	}
    }
	
	private BannerModel findItem(String urlImage)
	{
		for(BannerModel model : listTemp)
		{
			if(model.getUrlImage().equalsIgnoreCase(urlImage))
				return model;
		}
		return null;
	}
	
	
	private void calculateBannerHeightSize()
	{		
		if(bannerSize > 2)
		{
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity)ctx).getWindowManager().getDefaultDisplay().getMetrics(metrics);			
			imageSwitcher.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, metrics.heightPixels/bannerSize));
		}
	}
	
	public BannerView(Context context)
	{
		super(context);
	}

	
	public int getAnimationCode()
	{
		return animationCode;
	}
	
}

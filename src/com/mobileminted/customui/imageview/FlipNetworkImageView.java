package com.mobileminted.customui.imageview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.Toast;
import com.mobileminted.customui.R;
import com.mobileminted.customui.imageview.FlipNetworkImageView.FlipAnimator;
import com.mobileminted.customui.imageview.FlipNetworkImageView.OnFlipNetworkListener;
import com.mobileminted.customui.models.BannerModel;
import com.mobileminted.customui.models.FlipNetworkImageViewModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

@SuppressLint("Recycle")
public class FlipNetworkImageView extends ImageView implements View.OnClickListener,Animation.AnimationListener
{
	private static final int FLAG_ROTATION_X = 1 << 0;
	private static final int FLAG_ROTATION_Y = 1 << 1;
    private static final int FLAG_ROTATION_Z = 1 << 2;
	
    private static final Interpolator fDefaultInterpolator = new DecelerateInterpolator();

    private static int defaultDuration = 500;
    private static int defaultRotations = 2;
    private static boolean defaultAnimated = true;
    private static boolean defaultFlipped = false;
    private static boolean defaultIsRotationReversed = false;
    
    
    private FlipAnimator animation;
    private OnFlipNetworkListener listener;

    private boolean isFlipped;
    private boolean isDefaultAnimated;
    
    private Drawable drawable1;
    

    private boolean isRotationXEnabled;
    private boolean isRotationYEnabled;
    private boolean isRotationZEnabled;
    
    private boolean isFlipping;
    private boolean isRotationReversed;
    private int rotation;
    
    
    private boolean isAutomaticRun = true;
    private boolean isRandomAxis= true;
    private int startInterval = 2000;
    private int endInterval = 5000;
    
    private ImageLoader imgLoader = ImageLoader.getInstance(); 
    private DisplayImageOptions option;
   
    public interface OnFlipNetworkListener 
    {
        public void onClick(FlipNetworkImageView view);
        public void onFlipStart(FlipNetworkImageView view);
        public void onFlipEnd(FlipNetworkImageView view);
    }
    
    public FlipNetworkImageView(Context context) 
    {
        super(context);
    }

    public FlipNetworkImageView(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
        parseAttributes(context,context.obtainStyledAttributes(attrs,R.styleable.FlipNetworkImageView));
        
        handler = new Handler();
        
        option = new DisplayImageOptions.Builder()
		.cacheOnDisc(true)
		.cacheInMemory(true)
		.showImageOnLoading(R.drawable.ic_launcher)
		.imageScaleType(ImageScaleType.EXACTLY)
		.build();
    }
	
	private void parseAttributes(Context context,TypedArray a)
	{	
		isDefaultAnimated = a.getBoolean(R.styleable.FlipNetworkImageView_fnIsAnimated, defaultAnimated);
        isFlipped = a.getBoolean(R.styleable.FlipNetworkImageView_fnIsFlipped, defaultFlipped);
        
        isAutomaticRun = a.getBoolean(R.styleable.FlipNetworkImageView_fnIsAutomaticRun, isAutomaticRun);
        isRandomAxis = a.getBoolean(R.styleable.FlipNetworkImageView_fnIsRandomAxis, isRandomAxis);
        startInterval = a.getInteger(R.styleable.FlipNetworkImageView_fnStartInterval, startInterval);
        endInterval = a.getInteger(R.styleable.FlipNetworkImageView_fnEndInterval, endInterval);
        
        int duration = a.getInt(R.styleable.FlipNetworkImageView_fnFlipDuration, defaultDuration);
        int interpolatorResId = a.getResourceId(R.styleable.FlipNetworkImageView_fnFlipInterpolator, 0);
        
        Interpolator interpolator = interpolatorResId > 0 ? AnimationUtils.loadInterpolator(context, interpolatorResId) : fDefaultInterpolator;
        
        rotation = a.getInteger(R.styleable.FlipNetworkImageView_fnFlipRotations, defaultRotations);
        
        isRotationXEnabled = (rotation & FLAG_ROTATION_X) != 0;
        isRotationYEnabled = (rotation & FLAG_ROTATION_Y) != 0;
        isRotationZEnabled = (rotation & FLAG_ROTATION_Z) != 0;

        isRotationReversed = a.getBoolean(R.styleable.FlipNetworkImageView_fnReverseRotation, defaultIsRotationReversed);

        animation = new FlipAnimator();
        animation.setAnimationListener(this);
        animation.setInterpolator(interpolator);
        animation.setDuration(duration);

        setOnClickListener(this);

        drawable1 = getDrawable();
        
        setImageDrawable(drawable1);
        isFlipping = false;

        a.recycle();
	}
	
	public void setOnFlipListener(OnFlipNetworkListener listener) 
	{
       
    }


    @Override
    public void onClick(View v) 
    {
        toggleFlip(true);
        if(listener != null) 
        {
            listener.onClick(this);
        }
    }

    
    @Override
    public void onAnimationStart(Animation animation) 
    {
        if (listener != null) 
        {
            listener.onFlipStart(this);
        }
        isFlipping = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) 
    {
        if(listener != null) 
        {
            listener.onFlipEnd(this);
        }
        isFlipping = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) 
    {
    
    }
	
    private int interval;
    private Handler handler;
    private Random random = new Random();
    Runnable runBanner = new Runnable()
	{
		@Override
		public void run()
		{
			toggleFlip(true);
			
			if(endInterval==startInterval)
				handler.postDelayed(runBanner, startInterval);
			else
			{
				interval = random.nextInt(endInterval-startInterval)+startInterval;
				handler.postDelayed(runBanner, interval);
			}	
		}
	};
    
	private List<FlipNetworkImageViewModel> listCompleted = new ArrayList<FlipNetworkImageViewModel>();
	private List<FlipNetworkImageViewModel> listTemp;
    public void downloadListImages(List<FlipNetworkImageViewModel> listModel)
    {
    	listTemp = listModel;
    	for(FlipNetworkImageViewModel item : listModel)
    	{
    		ImageSize targetSize = new ImageSize(this.getWidth(), this.getHeight());
        	imgLoader.loadImage(item.getImageUri(), targetSize, option, new SimpleImageLoadingListener() 
        	{
        		FlipNetworkImageViewModel m;
				@Override
        	    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
        	    {
					m = findItem(imageUri);
					listCompleted.add(new FlipNetworkImageViewModel(new BitmapDrawable(getResources(), loadedImage), m.getUrlLink()));
        	    	
        	    	if(isAutomaticRun)
        	    	{
	        	    	if(listCompleted.size() == 1)
	        	    	{
	        	    		setImageDrawable(listCompleted.get(0).getDrawable());
	        	    	}
	        	    	else if(listCompleted.size() == 2)
	        	    	{
	        	    		runBanner.run();
	        	    	}
        	    	}
        	    	else
        	    		setImageDrawable(listCompleted.get(0).getDrawable());
        	    	
        	    }				
        	});        	
    	}
    }
    
    private FlipNetworkImageViewModel findItem(String urlImage)
	{
		for(FlipNetworkImageViewModel model : listTemp)
		{
			if(model.getImageUri().equalsIgnoreCase(urlImage))
				return model;
		}
		return null;
	}
    
    private FlipNetworkImageViewModel activeImageModel;
    
    private int drawIndex = 0;
    private Random rndRotation = new Random();
    public void toggleFlip(boolean animated) 
    {
    	drawIndex += 1;
    	if(drawIndex == listCompleted.size())
    		drawIndex = 0;
    	
    	if(isRandomAxis)
    	{
    		int rot = rndRotation.nextInt(3-1)+1;
    		switch(rot)
			{
				case 1:setRotation(FLAG_ROTATION_X);break;
				case 2:setRotation(FLAG_ROTATION_Y);break;
				case 3:setRotation(FLAG_ROTATION_Z);break;
			}
    		
    		isRotationXEnabled = (rot & FLAG_ROTATION_X) != 0;
            isRotationYEnabled = (rot & FLAG_ROTATION_Y) != 0;
            isRotationZEnabled = (rot & FLAG_ROTATION_Z) != 0;
    	}
    	
    	if (animated) 
        {
            animation.setToDrawable(listCompleted.get(drawIndex).getDrawable());
    		startAnimation(animation);
        } 
        else 
        {
        	setImageDrawable(listCompleted.get(drawIndex).getDrawable());
        }
        
    	setActiveImageModel(listCompleted.get(drawIndex));
    	
        isFlipped = !isFlipped;

    }
    
    
    
    public FlipNetworkImageViewModel getActiveImageModel()
	{
		return activeImageModel;
	}

	public void setActiveImageModel(FlipNetworkImageViewModel activeImageModel)
	{
		this.activeImageModel = activeImageModel;
	}

	public void setFlipped(boolean flipped) 
    {
        setFlipped(flipped, isDefaultAnimated);
    }

    public void setFlipped(boolean flipped, boolean animated) 
    {
        if (flipped != isFlipped) 
        {
            toggleFlip(animated);
        }
    }
    
    public void setRotation(int rot)
    {
    	rotation = rot;
    }
    
    public boolean isRotationXEnabled() 
    {
        return isRotationXEnabled;
    }

    public void setRotationXEnabled(boolean enabled) 
    {
        isRotationXEnabled = enabled;
    }

    public boolean isRotationYEnabled() 
    {
        return isRotationYEnabled;
    }

    public void setRotationYEnabled(boolean enabled) 
    {
        isRotationYEnabled = enabled;
    }

    public boolean isRotationZEnabled() 
    {
        return isRotationZEnabled;
    }

    public void setRotationZEnabled(boolean enabled) 
    {
        isRotationZEnabled = enabled;
    }

    public FlipAnimator getFlipAnimation() 
    {
        return animation;
    }

    public void setInterpolator(Interpolator interpolator) 
    {
        animation.setInterpolator(interpolator);
    }

    public void setDuration(int duration) 
    {
       animation.setDuration(duration);
    }

    public boolean isFlipped() 
    {
        return isFlipped;
    }

    public boolean isFlipping() 
    {
        return isFlipping;
    }

    public boolean isRotationReversed()
    {
        return isRotationReversed;
    }

    public void setRotationReversed(boolean rotationReversed)
    {
        isRotationReversed = rotationReversed;
    }

    public boolean isAnimated() 
    {
        return isDefaultAnimated;
    }

    public void setAnimated(boolean animated) 
    {
        isDefaultAnimated = animated;
    }

    

    


	
	public class FlipAnimator extends Animation 
	{
		private Camera camera;
		private Drawable toDrawable;
		private float centerX;
		private float centerY;
		private boolean visibilitySwapped;

		public FlipAnimator() 
		{
			setFillAfter(true);
		}

		public void setToDrawable(Drawable to) 
		{
			toDrawable = to;
			visibilitySwapped = false;
		}

		@Override
		public void initialize(int width, int height, int parentWidth, int parentHeight) 
		{
			super.initialize(width, height, parentWidth, parentHeight);
			camera = new Camera();
			this.centerX = width / 2;
			this.centerY = height / 2;
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) 
		{
			// Angle around the y-axis of the rotation at the given time. It is calculated both in radians and in the equivalent degrees.
			final double radians = Math.PI * interpolatedTime;
			float degrees = (float) (180.0 * radians / Math.PI);

			if(isRotationReversed)
			{
				degrees = -degrees;
			}

			// Once we reach the midpoint in the animation, we need to hide the  source view and show the destination view. We also need to change
			// the angle by 180 degrees so that the destination does not come in flipped around. This is the main problem with SDK sample, it does not
			// do this.
			if (interpolatedTime >= 0.5f) 
			{
				if(isRotationReversed)
					degrees += 180.f;  
				else
					degrees -= 180.f;

				if (!visibilitySwapped) 
				{
					setImageDrawable(toDrawable);
					visibilitySwapped = true;
				}
			}

			final Matrix matrix = t.getMatrix();

			camera.save();
			camera.translate(0.0f, 0.0f, (float) (150.0 * Math.sin(radians)));
			camera.rotateX(isRotationXEnabled ? degrees : 0);
			camera.rotateY(isRotationYEnabled ? degrees : 0);
			camera.rotateZ(isRotationZEnabled ? degrees : 0);
			camera.getMatrix(matrix);
			camera.restore();

			matrix.preTranslate(-centerX, -centerY);
			matrix.postTranslate(centerX, centerY);
		}
	}

}
package com.mobileminted.customui.imageview;

import com.mobileminted.customui.R;
import android.widget.ImageView;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class FlipImageView extends ImageView implements View.OnClickListener,Animation.AnimationListener
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
    private OnFlipListener listener;

    private boolean isFlipped;
    private boolean isDefaultAnimated;

    private Drawable drawable1;
    private Drawable drawable2;

    private boolean isRotationXEnabled;
    private boolean isRotationYEnabled;
    private boolean isRotationZEnabled;
    private boolean isFlipping;
    private boolean isRotationReversed;
    
    public interface OnFlipListener 
    {
        public void onClick(FlipImageView view);
        public void onFlipStart(FlipImageView view);
        public void onFlipEnd(FlipImageView view);
    }
    
    public FlipImageView(Context context) 
    {
        super(context);
    }

    public FlipImageView(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
        parseAttributes(context,context.obtainStyledAttributes(attrs,R.styleable.FlipImageView));
    }
	
	private void parseAttributes(Context context,TypedArray a)
	{
		defaultDuration = (int)a.getInteger(R.styleable.FlipImageView_flipDuration, defaultDuration);
		defaultRotations = (int)a.getInteger(R.styleable.FlipImageView_flipRotations, defaultRotations);
		defaultAnimated = (boolean)a.getBoolean(R.styleable.FlipImageView_isAnimated, defaultAnimated);
		defaultFlipped = (boolean)a.getBoolean(R.styleable.FlipImageView_isFlipped, defaultFlipped);
		
		isDefaultAnimated = a.getBoolean(R.styleable.FlipImageView_isAnimated, defaultAnimated);
        isFlipped = a.getBoolean(R.styleable.FlipImageView_isFlipped, defaultFlipped);
        
        int duration = a.getInt(R.styleable.FlipImageView_flipDuration, defaultDuration);
        int interpolatorResId = a.getResourceId(R.styleable.FlipImageView_flipInterpolator, 0);
        
        Interpolator interpolator = interpolatorResId > 0 ? AnimationUtils.loadInterpolator(context, interpolatorResId) : fDefaultInterpolator;
        int rotations = a.getInteger(R.styleable.FlipImageView_flipRotations, defaultRotations);
        
        isRotationXEnabled = (rotations & FLAG_ROTATION_X) != 0;
        isRotationYEnabled = (rotations & FLAG_ROTATION_Y) != 0;
        isRotationZEnabled = (rotations & FLAG_ROTATION_Z) != 0;

        drawable1 = getDrawable();
        drawable2 = a.getDrawable(R.styleable.FlipImageView_Drawable2);
        
        isRotationReversed = a.getBoolean(R.styleable.FlipImageView_reverseRotation, defaultIsRotationReversed);

        animation = new FlipAnimator();
        animation.setAnimationListener(this);
        animation.setInterpolator(interpolator);
        animation.setDuration(duration);

        setOnClickListener(this);

        setImageDrawable(isFlipped ? drawable2 : drawable1);
        isFlipping = false;

        a.recycle();
	}
	
	public void setOnFlipListener(OnFlipListener listener) 
	{
        listener = listener;
    }


    @Override
    public void onClick(View v) 
    {
        toggleFlip();
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
	
	public void setDrawable2(Drawable drawable2)
	{
        if(isFlipped)
        	setImageDrawable(drawable2);
    }

    public void setDrawable(Drawable drawable1)
    {
        if(!isFlipped)
        	setImageDrawable(drawable1);
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

    public void toggleFlip() 
    {
        toggleFlip(isDefaultAnimated);
    }

    public void toggleFlip(boolean animated) 
    {
        if (animated) 
        {
            animation.setToDrawable(isFlipped ? drawable1 : drawable2);
            startAnimation(animation);
        } 
        else 
        {
            setImageDrawable(isFlipped ? drawable1 : drawable2);
        }
        
        isFlipped = !isFlipped;

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

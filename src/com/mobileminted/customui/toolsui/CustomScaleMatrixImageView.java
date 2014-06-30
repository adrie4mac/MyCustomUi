package com.mobileminted.customui.toolsui;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomScaleMatrixImageView extends ImageView
{

	private Matrix mMatrix;
    private boolean mHasFrame;

    
    public enum CustomScaleTypes
    {
    	CROP_TOP_WIDTH, FIT_XY,SCALE_BY_WIDTH_FULLIMAGE;
    }
    
    public CustomScaleTypes customScaleType;
    
    public float startYAxis = 0;
    public float startXAxis = 0;
    
    

	
	public CustomScaleTypes getCustomScaleType()
	{
		return customScaleType;
	}

	public void setCustomScaleType(CustomScaleTypes customScaleType)
	{
		this.customScaleType = customScaleType;
	}

	public float getStartYAxis()
	{
		return startYAxis;
	}

	public void setStartYAxis(float startYAxis)
	{
		this.startYAxis = startYAxis;
	}

	public float getStartXAxis()
	{
		return startXAxis;
	}

	public void setStartXAxis(float startXAxis)
	{
		this.startXAxis = startXAxis;
	}

	@SuppressWarnings("UnusedDeclaration")
    public CustomScaleMatrixImageView(Context context) {
        this(context, null, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public CustomScaleMatrixImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public CustomScaleMatrixImageView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        mHasFrame = false;
        mMatrix = new Matrix();
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b)
    {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed)// we do not want to call this method if nothing changed 
        {
            mHasFrame = true;
            setupScaleMatrix(r-l, b-t);
        }
        return changed;
    }

    
    
    @Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
//		if(tipe == 4 && getDrawable() !=  null)
		if(customScaleType == CustomScaleTypes.SCALE_BY_WIDTH_FULLIMAGE)
		{
			int width = MeasureSpec.getSize(widthMeasureSpec);
		    int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
		    setMeasuredDimension(width, height);
		}
	}

	private void setupScaleMatrix(int width, int height) 
    {
    	if(customScaleType == CustomScaleTypes.CROP_TOP_WIDTH)
    	{
	    	final Drawable drawable = getDrawable();
	    	if (!mHasFrame) 
	    		return;// ensure that we already have frame called and have width and height
	
	    	if (drawable == null) 
	    		return;// if drawable is null because when not initialized at startup drawable we can ise NullPointerException
	
	    	Matrix matrix = mMatrix;
	    	final int intrinsicWidth = drawable.getIntrinsicWidth();
	    	final int intrinsicHeight = drawable.getIntrinsicHeight();
	
	    	float factorWidth = width/(float) intrinsicWidth;
	    	float factorHeight = height/(float) intrinsicHeight;
	    	float factor = Math.max(factorHeight, factorWidth);
	
	
	    	matrix.setTranslate(-intrinsicWidth/2.0f, startYAxis);
	    	matrix.postScale(factor, factor, 0, 0);
	    	matrix.postTranslate(width/2.0f, 0);
	    	setImageMatrix(matrix);
    	}
    	else if(customScaleType == CustomScaleTypes.FIT_XY)
    	{
    		
    		final Drawable drawable = getDrawable();
    		Matrix m = this.getImageMatrix();
    		RectF drawableRect = new RectF(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    		RectF viewRect = new RectF(0, 0, this.getWidth(), this.getHeight());

    		m.setRectToRect(drawableRect, viewRect,Matrix.ScaleToFit.FILL);
    		
    		setImageMatrix(m);
    	}
    	else if(customScaleType == CustomScaleTypes.SCALE_BY_WIDTH_FULLIMAGE)
    	{//tipe 4 sama dengan tipe 1 cuman nanti dibedain di overide onMeasure
    		final Drawable drawable = getDrawable();
	    	if (!mHasFrame) 
	    		return;// ensure that we already have frame called and have width and height
	
	    	if (drawable == null) 
	    		return;// if drawable is null because when not initialized at startup drawable we can ise NullPointerException
	
	    	Matrix matrix = mMatrix;
	    	final int intrinsicWidth = drawable.getIntrinsicWidth();
	    	final int intrinsicHeight = drawable.getIntrinsicHeight();
	
	    	float factorWidth = width/(float) intrinsicWidth;
	    	float factorHeight = height/(float) intrinsicHeight;
	    	float factor = Math.max(factorHeight, factorWidth);
	
	
	    	matrix.setTranslate(-intrinsicWidth/2.0f, startYAxis);
	    	matrix.postScale(factor, factor, 0, 0);
	    	matrix.postTranslate(width/2.0f, 0);
	    	setImageMatrix(matrix);
    	}
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        // We have to recalculate image after chaning image
        setupScaleMatrix(getWidth(), getHeight());
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        // We have to recalculate image after chaning image
        setupScaleMatrix(getWidth(), getHeight());
    }

//	@Override
//	public void setImageUrl(String url,ImageLoader imageLoader,boolean loadIfNecessary)
//	{
//		super.setImageUrl(url, imageLoader, loadIfNecessary);
//		setupScaleMatrix(getWidth(), getHeight());
//	}


    
    
    // We do not have to overide setImageBitmap because it calls 
    // setImageDrawable method

	
}

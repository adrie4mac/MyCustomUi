package com.mobileminted.customui.progress;

import java.text.NumberFormat;
import org.apache.http.client.CircularRedirectException;
import com.mobileminted.customui.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingRotateImage extends AlertDialog
{
	private Context mContext; 
	/** Creates a ProgressDialog with a circular, spinning progress
     * bar. This is the default.
     */
    public static final int STYLE_SPINNER = 0;
    
    /** Creates a ProgressDialog with a horizontal progress bar.
     */
    public static final int STYLE_HORIZONTAL = 1;
    
    private ProgressBar mProgress;
    private TextView mMessageView;
    
    private int mProgressStyle = STYLE_SPINNER;
   
    
    private boolean mIndeterminate;
    private CharSequence mMessage;
    
    private boolean mHasStarted;
    
    
   
    public LoadingRotateImage(Context context, int theme,int imgDrawable,CharSequence description) 
    {
        super(context, theme);
        mContext = context;
        imgSource = imgDrawable;
        text = description.toString();
    }
 

    private int imgSource;
    private String text;
    private ImageView progressView;
    private TextView lblText;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View v = inflater.inflate(R.layout.dlg_progress, null);

    	lblText = (TextView)v.findViewById(R.id.prgs_text);
    	progressView = (ImageView)v.findViewById(R.id.imageLoading);
    	
    	progressView.setImageResource(imgSource);
    	progressView.startAnimation(setupAnimation(12, 1000));

    	lblText.setText(text+"\n");
    	
    	setView(v);
        
        
        super.onCreate(savedInstanceState);
    }
    
    public Animation setupAnimation(final int frameCount, final int duration) 
    {
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
        a.setDuration(duration);
        a.setRepeatCount(Animation.INFINITE);
        a.setInterpolator(new Interpolator() 
        {
            @Override
            public float getInterpolation(float input) 
            {
                return (float)Math.floor(input*frameCount)/frameCount;
            }
        });
        return a;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        mHasStarted = true;
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        } else {
            mIndeterminate = indeterminate;
        }
    }

    public boolean isIndeterminate() {
        if (mProgress != null) {
            return mProgress.isIndeterminate();
        }
        return mIndeterminate;
    }
    
    @Override
    public void setMessage(CharSequence message) {
        if (mProgress != null) {
            if (mProgressStyle == STYLE_HORIZONTAL) {
                super.setMessage(message);
            } else {
                mMessageView.setText(message);
            }
        } else {
            mMessage = message;
        }
    }
    
    public void setProgressStyle(int style) {
        mProgressStyle = style;
    }

   
}

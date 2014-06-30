package com.mobileminted.customui.progress;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mobileminted.customui.R;

public class DrawableAnimLoading extends AlertDialog
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
    
    
   
    public DrawableAnimLoading(Context context, int theme,int imgDrawable,CharSequence description) 
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

    	lblText.setText(text+"\n");
    	
		final AnimationDrawable animProgress = (AnimationDrawable)progressView.getDrawable();
		progressView.post(new Runnable()
		{
			public void run()
			{
				animProgress.start();
			}
		});
		
		
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
package com.sidak.crystallball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.sidak.crystallball.R;

public class ActivityButton extends Activity {
	/*
	 * properties abt objects are typically c/d member variables 
	 * and the convention is to name them with a lowercase m and
	 * then the class name of the object
	 */
	private CrystalBall mCrystalBall = new CrystalBall();
	private TextView mAnswerLabel;//we need not even have that final modifier now
	// (initially it was declared in oncreate method)
	private Button mGetAnswerButton;
	private ImageView mCrystalBallImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_activity_main);
		// assign values from layout files
		mAnswerLabel =(TextView)findViewById(R.id.textToBeShown);
		mGetAnswerButton =(Button)findViewById(R.id.enlightenButton);
		mCrystalBallImage =(ImageView)findViewById(R.id.mainImage); 
		mGetAnswerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// don't create a crystalball obj over here, then everytime 
				// the button is pressed , it will be created, slows the process
				mAnswerLabel.setText(mCrystalBall.getAnAnswer());
				animateCrystalBall();
				animateText();
				playSound();
			}
		});
		

	}
	private void playSound(){
		MediaPlayer player= MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});
	}
	
	private void animateCrystalBall(){
		
		mCrystalBallImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation =(AnimationDrawable)mCrystalBallImage.getDrawable();
		// solving a bug in android with animation
		// it thinks that animation is still there , even when finished
		// causes a problem when we click a second time on the button
		if(ballAnimation.isRunning()){
			ballAnimation.stop();
		}
		ballAnimation.start();
	}

	private void animateText(){
		AlphaAnimation fadeInAnimation =new AlphaAnimation(0, 1);
		fadeInAnimation.setDuration(1500);
		fadeInAnimation.setFillAfter(true);//animation stays after it is over
		mAnswerLabel.setAnimation(fadeInAnimation);
	}
}

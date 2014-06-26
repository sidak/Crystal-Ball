package com.sidak.crystallball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidak.crystallball.R;
import com.sidak.crystallball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {
	/*
	 * properties abt objects are typically c/d member variables 
	 * and the convention is to name them with a lowercase m and
	 * then the class name of the object
	 */
	private CrystalBall mCrystalBall = new CrystalBall();
	private TextView mAnswerLabel;//we need not even have that final modifier now
	// (initially it was declared in oncreate method)
	private ImageView mCrystalBallImage;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	public static final String TAG=MainActivity.class.getSimpleName();
	
	//refactory tools will be able to check it
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// assign values from layout files
		mAnswerLabel =(TextView)findViewById(R.id.textToBeShown);
		mCrystalBallImage =(ImageView)findViewById(R.id.mainImage); 
		mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector =new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				handleAnswer();
			}
		});
		
		// here we use a static method
		//Toast.makeText(this,"yay, our activity was started",
			//	Toast.LENGTH_LONG	).show();
		// if u want to do something more with toasts , store it in a 
		// variable , then apply show method on that variable
		/*Toast welcomeToast= Toast.makeText(this, "look me up here", Toast.LENGTH_LONG);
		welcomeToast.setGravity(Gravity.TOP, 0, 30);
		welcomeToast.show();
		*/
		Log.d(TAG, "running in the oncreate method");
		
	}
	@Override
	public void onResume(){
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector); 	
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
	private void handleAnswer() {
		mAnswerLabel.setText(mCrystalBall.getAnAnswer());
		animateCrystalBall();
		animateText();
		playSound();
	}
}

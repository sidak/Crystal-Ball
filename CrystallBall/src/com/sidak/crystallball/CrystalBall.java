package com.sidak.crystallball;
import java.util.Random;


public class CrystalBall {
	public String[] mAnswers = {
			"It is certain",
			"It is decidedly so",
			"All signs say YES",
			"The stars are not aligned",
			"My reply is no",
			"It is doubtful",
			"Better not tell you now",
			"Concentrate and ask again",
			"Unable to answer now" };
	/**
	 * @param args
	 */
	public String getAnAnswer(){
		// TODO Auto-generated method stub
		
		
		Random randomGenerator= new Random();
		int randomNum= randomGenerator.nextInt(mAnswers.length);
		/*
		 * 0 = yes
		 * 1= no
		 * 2= maybe
		 */
		/*if (randomNum==0) ans="Yes";
		else if (randomNum==1) ans="No";
		else if (randomNum==2) ans="Maybe";
		else ans="error";
		*/
		return mAnswers[randomNum];

	}

}

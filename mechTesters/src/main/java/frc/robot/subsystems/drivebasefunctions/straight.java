package frc.robot.subsystems.drivebasefunctions;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.subsystems.drivebasefunctions.mathFuncs;



import edu.wpi.first.wpilibj.Timer;


public class straight{
  

//only uses encoder, goes x amount of revultions
public static void goRevs(double leftRevs, double rightRevs, TalonSRX leftMast, TalonSRX rightMast) {
	leftRevs = mathFuncs.revsToTicks(leftRevs);
	rightRevs = mathFuncs.revsToTicks(rightRevs);
	
	double leftSet =leftRevs+leftMast.getSelectedSensorPosition(0);
	double rightSet =rightRevs+rightMast.getSelectedSensorPosition(0);
		
	leftMast.set(ControlMode.Position, leftSet);
	rightMast.set(ControlMode.Position, rightSet);
}	

//only uses motion Magic, 4 inch drift over ten ft
public static void goInchesEncoderOnly(double input, TalonSRX leftMast, TalonSRX rightMast) {
	double leftSet = mathFuncs.inchesToTicks(input) + leftMast.getSelectedSensorPosition(0);
	double rightSet = mathFuncs.inchesToTicks(input) + rightMast.getSelectedSensorPosition(0);
	
	// leftMast.set(ControlMode.Position, leftSet);
	// rightMast.set(ControlMode.Position, leftSet);

	leftMast.set(ControlMode.MotionMagic, leftSet);
	rightMast.set(ControlMode.MotionMagic, rightSet);
	
}

//USES GYRO AND ENCODER 4 inch drift over 70ft
//the concept of this is pretty simple
//run motion magic on TalonSRX then use the gyro to ajust gain
//if a side is slower we incrase the P value on the talon
//this tells the tallon it needs to work harder in order to keep up
public static void goInchesGyroEncoder(double input, AHRS ahrs, TalonSRX leftMast, TalonSRX rightMast){
	System.out.println("Starting to go Straight!");
		//get our starting postion
		double heading = ahrs.getAngle();
		//wait a second
		Timer.delay(.1);
		//print it out
		System.out.println("Initial Heading Gyro reads:" + ahrs.getAngle());
		
		//get our left and right target
		double leftSet = mathFuncs.inchesToTicks(input) + leftMast.getSelectedSensorPosition(0);
		double rightSet = mathFuncs.inchesToTicks(input) + rightMast.getSelectedSensorPosition(0);
		
		//get our starting encoder postion
		double leftCurrent = leftMast.getSelectedSensorPosition(0);

		//set left and right target
		leftMast.set(ControlMode.MotionMagic, leftSet);
		rightMast.set(ControlMode.MotionMagic, rightSet);

		//init accumilation and error value 
		double gyroAcc = 0;
		double error =0;
		double previousError = 0;
		double curDev = 0;
		double sumDev = 0;

		//create array of last twity dertives
		double[] lastTwenty = new double[20];
		//create second arry for tempaory use
		double[] lastTwentyShifted = new double[20];

		//intialize the array to all 0
		for(int i = 0; i<20; i++){
			lastTwenty[i] = 0;
		}
		
		//loop for while we are moving
		while(leftCurrent < leftSet - 100){
			//save our old error
			previousError = error;
			//get our new error
			error = (heading-ahrs.getAngle());
			//get our change in error
			curDev = error - previousError;
			
			//shift over the last 19 values, thorwing out the last one
			for(int i =0; i<19; i++){
				lastTwentyShifted[i+1] = lastTwenty[i];
			}
			//make the arry equal to the shifted one
			for(int i =0; i<20; i++){
				lastTwenty[i] = lastTwentyShifted[i];
			}
			//add in the new error for this one
			lastTwenty[0] =curDev;
			//sum the error over the last tewnty
			for(int i =0; i<20; i++){
				sumDev = sumDev+ lastTwenty[i];
			}
			
	
			//get our linaer term
			double gyroError = straightConstants.gyroCorP*error;

			//print out the drivte and linear term
			// System.out.println(sumDev + "||" + gyroError);

			//add up error over time
			gyroAcc = gyroAcc + error;

			//mulitple aculltion error by a constant
			double gyroAccScaled = gyroAcc * straightConstants.gyroCorI;

			//scale the dervtive
			double sumDevScaled = sumDev * straightConstants.gyroCorD;

			//add up the gyro correction factor
			double gyroCorFact = gyroError + gyroAccScaled + sumDevScaled;

			//make P more senstive on the side that is dragging
			Robot.DriveBaseSub.setPIDFLeftSide(straightConstants.driveP + gyroCorFact, straightConstants.driveI, straightConstants.driveD, straightConstants.driveF);
			Robot.DriveBaseSub.setPIDFRightSide(straightConstants.driveP - gyroCorFact, straightConstants.driveI, straightConstants.driveD, straightConstants.driveF);
			
			//see how far along we are
			leftCurrent = leftMast.getSelectedSensorPosition(0);

			//sanity check if we are over 5 degrees off
			if (error > 5 || error < -5){
				System.out.println("I e-stopped since i am more than 5 degress off");
				break;
			}
		}

		//stop the motors
		leftMast.set(ControlMode.PercentOutput, 0);
		rightMast.set(ControlMode.PercentOutput, 0);

		//reset PID coefferints
		Robot.DriveBaseSub.setPIDFLeftSide(straightConstants.driveP, straightConstants.driveI, straightConstants.driveD, straightConstants.driveF);
		Robot.DriveBaseSub.setPIDFRightSide(straightConstants.driveP, straightConstants.driveI, straightConstants.driveD, straightConstants.driveF);
			
		// //tells us we stopped
		// Timer.delay(.1);
		// System.out.println("I stopped driving, I am off by:" + (heading-ahrs.getAngle()));
		// Timer.delay(.25);
		// System.out.println("final gyro reading:" + ahrs.getAngle());
}


  
}

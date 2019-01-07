package frc.robot.subsystems.drivebasefunctions;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.subsystems.drivebasefunctions.mathFuncs;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;


public class turn{
	//This function runs super fast, overshoots a lot at the start, and then corrects itself to within 2 degrees of the desired angle each time.
	//the basic method how it works is the same as slow
	//we would recomond not using it
	public static void FASTturnAbsoluteDegreesGyro(double deg, PIDController gyroTurnController, AHRS ahrs, TalonSRX leftMast, TalonSRX rightMast){
		gyroTurnController.setPID(turnConstants.gyroP, turnConstants.gyroI, turnConstants.gyroD);
		gyroTurnController.enable(); //turn it on

		double currentDirection = ahrs.getAngle();
		gyroTurnController.setSetpoint((currentDirection + deg)%360); //set the target
		
		System.out.println("Starting Turn, Gyro Reads:" + ahrs.getAngle());

		//gyroTurnController.setPercentTolerance(2); //Set the percentage error which is considered tolerable for use with OnTarget.
		//Set the absolute error which is considered tolerable for use with OnTarget.
		Timer.delay(.1);

		leftMast.set(ControlMode.Velocity, gyroTurnController.get()*1250);
		rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*1250);

		Timer.delay(.1);

		while(gyroTurnController.onTarget() != true){
			// System.out.println(ahrs.getAngle() + "||" + gyroTurnSpeed*slower);
			// leftMast.set(ControlMode.PercentOutput, gyroTurnController.get()*slower);
			// rightMast.set(ControlMode.PercentOutput, -gyroTurnController.get()*slower);

			leftMast.set(ControlMode.Velocity, gyroTurnController.get()*1500);
			rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*1500);

			// System.out.println(leftMast.getSelectedSensorVelocity(0));
		}

		// leftMast.set(ControlMode.PercentOutput, 0);
		// rightMast.set(ControlMode.PercentOutput, 0);
		leftMast.set(ControlMode.Velocity, 0);
		rightMast.set(ControlMode.Velocity,0);

		Timer.delay(.5);

		System.out.println("Intermediate Pause, gyro reads:" + ahrs.getAngle());
		System.out.println("Middle error is:" + (deg-ahrs.getAngle()));

		gyroTurnController.setPID(turnConstants.gyroP, 1.5*turnConstants.gyroI, turnConstants.gyroD);

		Timer.delay(.1);

		leftMast.set(ControlMode.Velocity, gyroTurnController.get()*250);
		rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*250);

		Timer.delay(.1);

		while(gyroTurnController.onTarget() != true){
			// System.out.println(ahrs.getAngle() + "||" + gyroTurnSpeed*slower);
			// leftMast.set(ControlMode.PercentOutput, gyroTurnController.get()*slower);
			// rightMast.set(ControlMode.PercentOutput, -gyroTurnController.get()*slower);

			leftMast.set(ControlMode.Velocity, gyroTurnController.get()*500);
			rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*500);

			// System.out.println(leftMast.getSelectedSensorVelocity(0));
		}

		leftMast.set(ControlMode.Velocity, 0);
		rightMast.set(ControlMode.Velocity,0);
		
		leftMast.set(ControlMode.PercentOutput, 0);
		rightMast.set(ControlMode.PercentOutput, 0);

		Timer.delay(.25);

		System.out.println("Final Position, gyro reads:" + ahrs.getAngle());
		System.out.println("Final error is:" + gyroTurnController.getError());

		gyroTurnController.disable();

    }

	//only uses arc length and assumes perfect traction
	//this doesnt work and we would not reccomend you use it
    public static void turnEncodersOnly(double degrees, boolean clockwise, TalonSRX leftMast, TalonSRX rightMast) {
		//absutle val
		degrees = Math.abs(degrees);
		
		//convert degres to arclength
		double arcLengthInches = degrees*27.875*3.14159/360;
		
		//convert length to ticks
		double arcLengthTicks = mathFuncs.inchesToTicks(arcLengthInches);
		
		//make left and right
		double rightTicks = arcLengthTicks;
		double leftTicks = arcLengthTicks;
		
		//chose dercition
		if(clockwise) {
			rightTicks = -1*rightTicks;
		}else {
			leftTicks = -1*leftTicks;
		}
		
		//set motors
		// leftMast.set(ControlMode.Position, leftTicks + leftMast.getSelectedSensorPosition(0));
		// rightMast.set(ControlMode.Position, rightTicks + rightMast.getSelectedSensorPosition(0));

		leftMast.set(ControlMode.MotionMagic, leftTicks + leftMast.getSelectedSensorPosition(0));
		rightMast.set(ControlMode.MotionMagic, rightTicks + rightMast.getSelectedSensorPosition(0));
    }
	
	//this works great, within half a degree everytime
	//the basic conpect is to use wpi lib pid object
	//then overshoot, and go again to correct that with a higher I gain
	//if you want to learn more about why this works read a bit about the math of PID
    public static void SLOWturnAbsoluteDegreesGyro(double deg, PIDController gyroTurnController, AHRS ahrs, TalonSRX leftMast, TalonSRX rightMast){
		//set the PID objects coefficants
		gyroTurnController.setPID(turnConstants.gyroP, turnConstants.gyroI, turnConstants.gyroD);
		//turn it on so it starts taking inputs and makes outputs
		gyroTurnController.enable(); //turn it on
		gyroTurnController.setSetpoint(deg); //set the target
		
		System.out.println("Starting Turn, Gyro Reads:" + ahrs.getAngle());

		//gyroTurnController.setPercentTolerance(2); //Set the percentage error which is considered tolerable for use with OnTarget.
		//Set the absolute error which is considered tolerable for use with OnTarget.
		Timer.delay(.1);

		//set the output speed of the motors, times 250 to match the encoder tick, experiment with this value
		leftMast.set(ControlMode.Velocity, gyroTurnController.get()*250);
		rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*250);

		Timer.delay(.1);

		//creates a while loop which stops once you reach the setpoint
		//this loop will get you within 3 degrees of your setpoint
		while(gyroTurnController.onTarget() != true){
			// System.out.println(ahrs.getAngle() + "||" + gyroTurnSpeed*slower);
			// leftMast.set(ControlMode.PercentOutput, gyroTurnController.get()*slower);
			// rightMast.set(ControlMode.PercentOutput, -gyroTurnController.get()*slower);

			//set the new speed, we go faster here in the while loop
			leftMast.set(ControlMode.Velocity, gyroTurnController.get()*500);
			rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*500);

			// System.out.println(leftMast.getSelectedSensorVelocity(0));
		}

		//kill the motors
		leftMast.set(ControlMode.PercentOutput, 0);
		rightMast.set(ControlMode.PercentOutput, 0);
		// leftMast.set(ControlMode.Velocity, 0);
		// rightMast.set(ControlMode.Velocity,0);

		Timer.delay(.5);

		//print out values
		System.out.println("Intermediate Pause, gyro reads:" + ahrs.getAngle());
		System.out.println("Middle error is:" + (deg-ahrs.getAngle()));

		//set a new I error to make the turn controller more sesntivte
		gyroTurnController.setPID(turnConstants.gyroP, 1.5*turnConstants.gyroI, turnConstants.gyroD);

		Timer.delay(.1);

		//start up motors again
		leftMast.set(ControlMode.Velocity, gyroTurnController.get()*250);
		rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*250);

		Timer.delay(.1);

		//do one more while loop to correct any final error
		//this should get you within half a degree
		while(gyroTurnController.onTarget() != true){
			// System.out.println(ahrs.getAngle() + "||" + gyroTurnSpeed*slower);
			// leftMast.set(ControlMode.PercentOutput, gyroTurnController.get()*slower);
			// rightMast.set(ControlMode.PercentOutput, -gyroTurnController.get()*slower);

			leftMast.set(ControlMode.Velocity, gyroTurnController.get()*500);
			rightMast.set(ControlMode.Velocity,-gyroTurnController.get()*500);

			// System.out.println(leftMast.getSelectedSensorVelocity(0));
		}

		//kill the motors		
		leftMast.set(ControlMode.PercentOutput, 0);
		rightMast.set(ControlMode.PercentOutput, 0);

		//wait a bit to get an accute gyro reading
		Timer.delay(.25);

		//print
		System.out.println("Final Position, gyro reads:" + ahrs.getAngle());
		System.out.println("Final error is:" + gyroTurnController.getError());

		//stop the controller from taking in inputs
		gyroTurnController.disable();

	}
}
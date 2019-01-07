package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.codeTest;
import frc.robot.commands.manualMode;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.drivebasefunctions.straight;
import frc.robot.subsystems.drivebasefunctions.turn;
import frc.robot.subsystems.drivebasefunctions.turnConstants;
import frc.robot.subsystems.drivebasefunctions.initers;



public class DriveBaseSub extends Subsystem implements PIDOutput{

	
	private VictorSPX leftFol;
	private VictorSPX rightFol;
	public TalonSRX leftMast;
	public TalonSRX rightMast;
	
	public AHRS ahrs;

	public PIDController gyroTurnController;
	public double gyroTurnSpeed=-.1;
	public double gyroTurnTolerance;
	

	public DriveBaseSub() {
		System.out.println("HELLO WORLD");
		SmartDashboard.putString("Hello ", "world");		
		//gets can ids for all the motors
		leftFol = new VictorSPX(RobotMap.LEFTFOL.value);
		rightFol = new VictorSPX(RobotMap.RIGHTFOL.value);
		leftMast = new TalonSRX(RobotMap.LEFTTAL.value);
		rightMast = new TalonSRX(RobotMap.RIGHTTAL.value);

		//basic init of all motors
		initVictor(leftFol);
		initVictor(rightFol);
		
		//init PID on masters
		initDriveLeads();
		
		//right side is reversed
		rightMast.setInverted(true);
		rightFol.setInverted(true);
		
		//slaves are set to follow
		leftFol.follow(leftMast);
		rightFol.follow(rightMast);
		
		//inverts encoders
		rightMast.setSensorPhase(true);
		leftMast.setSensorPhase(true);
		


		//get the gyro
		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
			/* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or S     */
			/* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
			ahrs = new AHRS(SerialPort.Port.kUSB); 
		//hope it doesnt crash
		} catch (RuntimeException ex ) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}

		Timer.delay(1);
		System.out.println("boot angle:" + ahrs.getAngle());

		gyroTurnController = new PIDController(turnConstants.gyroP, turnConstants.gyroI, turnConstants.gyroD, ahrs, this);
		gyroTurnController.setInputRange(0f,  360.0f);
		gyroTurnController.setOutputRange(-1.0, 1.0);
		gyroTurnController.setAbsoluteTolerance(gyroTurnTolerance);
		gyroTurnController.setContinuous(true);
		gyroTurnController.setAbsoluteTolerance(.5);

		

	}

	
	//method takes inpit of control mode and a left and right vlaue
	public void set(ControlMode mode, double leftVal, double rightVal) {
		//sets them, usign the control mode provided
		leftMast.set(mode, leftVal);
		rightMast.set(mode, rightVal);
		// System.out.println("left power: " + leftVal +  "|| left encoder " + leftMast.getSelectedSensorVelocity(0));
		// System.out.println("right power: " + rightVal +  "|| right encoder " + rightMast.getSelectedSensorVelocity(0));
	}
	
	

	
	public void goInchesGyroEncoder(double input) {
		straight.goInchesGyroEncoder(input, ahrs, leftMast, rightMast);
	}

	public void goInchesEncoderOnly(double input) {
		straight.goInchesEncoderOnly(input, leftMast, rightMast);
		
	}

	public void turnEncodersOnly(double degrees, boolean clockwise) {
		turn.turnEncodersOnly(degrees, clockwise, leftMast, rightMast);
	}

	public void turnDegreesGyro(double deg){
		turn.SLOWturnAbsoluteDegreesGyro(deg, gyroTurnController, ahrs, leftMast, rightMast);
	}
	

	//this method sets the pidf and configs the encoder
	public void initDriveLeads() {
		initers.initDriveLeads(leftMast, rightMast);
	}

	public void setPIDFLeftSide(double p1, double i1, double d1, double f1) {
		initers.setPIDFLeftSide(leftMast, p1, i1, d1, f1);
	}
	public void setPIDFRightSide(double p1, double i1, double d1, double f1) {
		initers.setPIDFRightSide(rightMast, p1, i1, d1, f1);
	}

	public void setPIDFBothSides(double p1, double i1, double d1, double f1) {
		initers.setPIDFBothSides(leftMast, rightMast, p1, i1, d1, f1);
	}

	//wraper for later use
	public void setGyroPid(double gp, double gi, double gd) {
		initers.setGyroPid(gyroTurnController, gp, gi, gd);
	}

	//the return of @override!!
	@Override
	public void pidWrite(double output) {
		gyroTurnSpeed = output;
	}

	//deflaut command runs when this class is made in robot.java
	@Override
	protected void initDefaultCommand() {
		//runs arcade drive
		setDefaultCommand(new manualMode()); //For Drvier
		// setDefaultCommand(new codeTest()); //for programmer

	}


	
	public static void initVictor(VictorSPX mot) {
		initers.initVictor(mot);
	}

	

}

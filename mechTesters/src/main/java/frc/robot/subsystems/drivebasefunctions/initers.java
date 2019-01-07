package frc.robot.subsystems.drivebasefunctions;

import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.PIDController;


public class initers{
    

	//this method sets the pidf and configs the encoder
	public static void initDriveLeads(TalonSRX leftMast, TalonSRX rightMast) {
        //genreal talon stuff
        leftMast.neutralOutput();
		leftMast.setSensorPhase(false);
        leftMast.configNominalOutputForward(0, 0);
		leftMast.configNominalOutputReverse(0, 0);
        leftMast.configClosedloopRamp(.2, 0);
        
        rightMast.neutralOutput();
		rightMast.setSensorPhase(false);
        rightMast.configNominalOutputForward(0, 0);
		rightMast.configNominalOutputReverse(0, 0);
		rightMast.configClosedloopRamp(.2, 0);

		//makes the motor see the onbaord encoder
        leftMast.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightMast.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

		//sets motion profling prameters
		leftMast.configMotionCruiseVelocity(mathFuncs.metersPerSecToTicksPer100MsINT(straightConstants.driveMaxSpeed), Constants.kTimeoutMs);
		leftMast.configMotionAcceleration(mathFuncs.metersPerSecToTicksPer100MsINT(straightConstants.driveMaxAcc), Constants.kTimeoutMs);

        rightMast.configMotionCruiseVelocity(mathFuncs.metersPerSecToTicksPer100MsINT(straightConstants.driveMaxSpeed), Constants.kTimeoutMs);
		rightMast.configMotionAcceleration(mathFuncs.metersPerSecToTicksPer100MsINT(straightConstants.driveMaxAcc), Constants.kTimeoutMs);


		/* set closed loop gains in slot0 */
		setPIDFBothSides(leftMast, rightMast, straightConstants.driveP, straightConstants.driveI, straightConstants.driveD, straightConstants.driveF);
	}


	public static void setPIDFLeftSide(TalonSRX leftMast, double p1, double i1, double d1, double f1) {
		leftMast.config_kP(Constants.kPIDLoopIdx, p1, Constants.kTimeoutMs);
		leftMast.config_kI(Constants.kPIDLoopIdx, i1, Constants.kTimeoutMs);
		leftMast.config_kD(Constants.kPIDLoopIdx, d1, Constants.kTimeoutMs);
		leftMast.config_kF(Constants.kPIDLoopIdx, f1, Constants.kTimeoutMs);
	}
	public static void setPIDFRightSide(TalonSRX rightMast, double p1, double i1, double d1, double f1) {
		rightMast.config_kP(Constants.kPIDLoopIdx, p1, Constants.kTimeoutMs);
		rightMast.config_kI(Constants.kPIDLoopIdx, i1, Constants.kTimeoutMs);
		rightMast.config_kD(Constants.kPIDLoopIdx, d1, Constants.kTimeoutMs);
		rightMast.config_kF(Constants.kPIDLoopIdx, f1, Constants.kTimeoutMs);
	}

	public static void setPIDFBothSides(TalonSRX leftMast, TalonSRX rightMast, double p1, double i1, double d1, double f1) {
		leftMast.config_kP(Constants.kPIDLoopIdx, p1, Constants.kTimeoutMs);
		leftMast.config_kI(Constants.kPIDLoopIdx, i1, Constants.kTimeoutMs);
		leftMast.config_kD(Constants.kPIDLoopIdx, d1, Constants.kTimeoutMs);

		rightMast.config_kP(Constants.kPIDLoopIdx, p1, Constants.kTimeoutMs);
		rightMast.config_kI(Constants.kPIDLoopIdx, i1, Constants.kTimeoutMs);
		rightMast.config_kD(Constants.kPIDLoopIdx, d1, Constants.kTimeoutMs);
	}

	//wraper for later use
	public static void setGyroPid(PIDController gyroTurnController, double gp, double gi, double gd) {
		gyroTurnController.setPID(gp, gi, gd);
	}


	public static void initVictor(VictorSPX mot) {
		mot.neutralOutput();
		mot.setSensorPhase(false);
		mot.configNominalOutputForward(0, 0);
		mot.configNominalOutputReverse(0, 0);
		mot.configClosedloopRamp(.2, 0);
	}
}
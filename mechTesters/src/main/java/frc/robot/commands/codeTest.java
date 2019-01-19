package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class codeTest extends Command {

	//VictorSPX mechis = new VictorSPX(11);
	VictorSPX intake = new VictorSPX(12);

	public codeTest() {
		requires(Robot.DriveBaseSub);
	}
	
	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	NetworkTableEntry tx = table.getEntry("tx");
	NetworkTableEntry ty = table.getEntry("ty");


	
	// private double speedMultiplier = 1;
	// private double maxMPH = 8 /*top speed- joystick will express percent of this
	 							// the therotical top speed is 8.9 but im playing on the safe side here
	 							// also will mean we can drive stragin even if one motor is jammed*/;

	protected void initialize() {

	}
		
	protected void execute() {
		
		tx = table.getEntry("tx");
		ty = table.getEntry("ty");

		//read values periodically
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);

		System.out.println("This is tx: " + x);

		System.out.println("This is ty: " + y);
		
		
		/*
		 * gets the perfecnt of power from the joystick
		 * gets the y from the left and turning from the right, normal aracde drive
		 * then makes the whole thing on a cubic function to give driver finer control in low power
		 */
		double rightPercent = (Robot.oi.getOneLeftY())
				- (Robot.oi.getOneRightX());
		
		double leftPercent = (Robot.oi.getOneLeftY())
				+ (Robot.oi.getOneRightX());
		
				while(Robot.oi.getOneLeftShoulder()) {
					leftPercent = (-Robot.oi.getOneLeftY())
						   + (Robot.oi.getOneRightX());
				   
					rightPercent = (-Robot.oi.getOneLeftY())
						   - (Robot.oi.getOneRightX());
				   
					Robot.DriveBaseSub.set(ControlMode.PercentOutput, leftPercent*.30, rightPercent*.30);
	   
					}
					if(!Robot.oi.getOneLeftShoulder()){
						Robot.DriveBaseSub.set(ControlMode.PercentOutput, 0, 0);
				}


//**********************************************************
	// DO NOT TOUCH STUFF ABOVE THIS LINE! //


	// Make your code testing/modifications in this area:


		if(Robot.oi.getOneX()) {
			//mechis.set(ControlMode.PercentOutput, 0);
			intake.set(ControlMode.PercentOutput, 0);
			Robot.DriveBaseSub.henrysTalon.set(ControlMode.PercentOutput, 0);



			
			//Robot.DriveBaseSub.henrysTalon.set(ControlMode.Position, -5000);

			
		}

		if(Robot.oi.getOneA()) {
			//mechis.set(ControlMode.PercentOutput, 1);
			intake.set(ControlMode.PercentOutput, -1);
			Robot.DriveBaseSub.henrysTalon.set(ControlMode.PercentOutput, -1);


			//System.out.println(Robot.DriveBaseSub.henrysTalon.getSelectedSensorPosition(0));


		}


		if(Robot.oi.getOneY()) {
			// mechis.set(ControlMode.PercentOutput, .5);
		}

		if(Robot.oi.getOneB()){
			//mechis.set(ControlMode.PercentOutput, -1);
			intake.set(ControlMode.PercentOutput, 1);
			Robot.DriveBaseSub.henrysTalon.set(ControlMode.PercentOutput, 1);



			//Robot.DriveBaseSub.henrysTalon.set(ControlMode.Position, 5000);
		}

		if(Robot.oi.getTwoA()){
			
		}

	
		
				
	}


//**********************************************************
	// DO NOT TOUCH STUFF BELOW THIS LINE! //
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void interrupted() {
		end();
	}

}

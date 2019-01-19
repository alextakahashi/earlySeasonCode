/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class manualMode extends Command {
  public manualMode() {
		requires(Robot.DriveBaseSub);
	}
	
	
	// private double speedMultiplier = 1;
	// private double maxMPH = 8 /*top speed- joystick will express percent of this
	 							// the therotical top speed is 8.9 but im playing on the safe side here
	 							// also will mean we can drive stragin even if one motor is jammed*/;

	protected void initialize() {
		
		
	}
  
  double speedCoeff = .7;
	
	protected void execute() {
		
		/*
		 * gets the perfecnt of power from the joystick
		 * gets the y from the left and turning from the right, normal aracde drive
		 * then makes the whole thing on a cubic function to give driver finer control in low power
		 */
		double leftPercent = -Robot.oi.getOneRightY();
	double rightPercent = -Robot.oi.getOneLeftY();
	
	SmartDashboard.putNumber("left", leftPercent);
    
    
		
    Robot.DriveBaseSub.set(ControlMode.PercentOutput, 
      speedCoeff*leftPercent, -speedCoeff*rightPercent);


		

		if(Robot.oi.getOneB()) {
      speedCoeff =1;
      System.out.println("Damage Mode Power at 100%");
		}
		if(Robot.oi.getOneA()) {
      speedCoeff =.7;
      System.out.println("Normal Mode Power at 70%");
		}
		if(Robot.oi.getOneX()) {
      speedCoeff =.3;
      System.out.println("Slow Mode Power at 30%");
		}
		if(Robot.oi.getOneY()) {
      speedCoeff = .15;
      System.out.println("Precsion Mode Power at 15%");
    }
		
	}
	
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void interrupted() {
		end();
	}

}
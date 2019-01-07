/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.codeTest;
import frc.robot.commands.autoCircle;
import frc.robot.subsystems.DriveBaseSub;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.networktables.*;


public class Robot extends TimedRobot {
	Command autoCircle;
	Command testmode;
	public static OI oi;
	public static DriveBaseSub DriveBaseSub;
	public static codeTest arcDrive;
	
	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	NetworkTableEntry tx = table.getEntry("tx");
	NetworkTableEntry ty = table.getEntry("ty");

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		DriveBaseSub = new DriveBaseSub();
		// chooser.addObject("My Auto", new MyAutoCommand());
		autoCircle = new autoCircle();

		
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	@Override
	public void autonomousInit() {
		autoCircle.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
		
		
	}

	@Override
	public void teleopPeriodic() {
		
		Scheduler.getInstance().run();
	}


	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}

	

	
}

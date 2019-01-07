package frc.robot.commands;

import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

	public TankDrive() {
		requires(Robot.DriveBaseSub);
	}
	

	protected void initialize() {
		
	}
	
	
	protected void execute() {
		double leftPow = Robot.oi.getOneLeftY()
				- Robot.oi.getOneLeftTrig() + Robot.oi.getOneLeftTrig();
		
		double rightPow = Robot.oi.getOneRightY() 
				- Robot.oi.getOneLeftTrig() + Robot.oi.getOneLeftTrig();
		Robot.DriveBaseSub.set(ControlMode.PercentOutput, leftPow, rightPow);
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void interrupted() {
		end();
	}

}

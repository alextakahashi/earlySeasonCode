package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class goForward extends Command {

	boolean done = false;
    public goForward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.DriveBaseSub);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
    
    boolean fristTime =false;

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(fristTime == false) {
          
        Robot.DriveBaseSub.goInchesGyroEncoder(50);
    	fristTime = true;
    	}
    	
    	SmartDashboard.putBoolean("done", done);
        double start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 100) {}
    	double leftSpeed = Robot.DriveBaseSub.leftMast.getSelectedSensorVelocity(Constants.kPIDLoopIdx);
    	//sets done to true 250 milliseconds after motors stop moving
    	if(leftSpeed == 0) {
    		start = System.currentTimeMillis();
    		while(System.currentTimeMillis() - start < 250) {}
    		done = true;
    	}   	
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

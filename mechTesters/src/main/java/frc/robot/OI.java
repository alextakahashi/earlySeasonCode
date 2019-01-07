/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public XboxController playerOne = new XboxController(RobotMap.f310Main.value);
	public XboxController playerTwo = new XboxController(RobotMap.f310Secondary.value);
	
	
	public double getOneRightX() {
		double out = playerOne.getRawAxis(RobotMap.kGamepadAxisRightStickX.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return out;
	}
	public double getOneLeftX() {
		double out = playerOne.getRawAxis(RobotMap.kGamepadAxisLeftStickX.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return out;
	}
	public double getOneLeftY() {
		double out = -playerOne.getRawAxis(RobotMap.kGamepadAxisLeftStickY.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return -out;
	}
	public double getOneRightY() {
		return -playerOne.getRawAxis(RobotMap.kGamepadAxisRightStickY.value);
	}
	public double getOneLeftTrig() {
		double out = playerOne.getRawAxis(RobotMap.kGamepadAxisLeftTrigger.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return -out;
	}
	public double getOneRightTrig() {
		double out = playerOne.getRawAxis(RobotMap.kGamepadAxisRightTrigger.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return out;
	}
	
	public boolean getOneB() {
		return playerOne.getRawButton(RobotMap.kGamepadButtonB.value);
	}

	
	public boolean getOneA() {
		return playerOne.getRawButton(RobotMap.kGamepadButtonA.value);
	}
	
	public boolean getOneY() {
		return playerOne.getRawButton(RobotMap.kGamepadButtonY.value);
	}
	
	public boolean getOneX() {
		return playerOne.getRawButton(RobotMap.kGamepadButtonX.value);
	}

	public boolean getOneRightShoulder(){
		return playerOne.getRawButton(RobotMap.kGamepadButtonShoulderR.value);
	}

	public boolean getOneLeftShoulder(){
		return playerOne.getRawButton(RobotMap.kGamepadButtonShoulderL.value);
	}

	public double getTwoRightX() {
		double out = playerTwo.getRawAxis(RobotMap.kGamepadAxisRightStickX.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return out;
	}
	public double getTwoLeftX() {
		double out = playerTwo.getRawAxis(RobotMap.kGamepadAxisLeftStickX.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return out;
	}
	public double getTwoLeftY() {
		double out = playerTwo.getRawAxis(RobotMap.kGamepadAxisLeftStickY.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return -out;
	}
	public double getTwoRightY() {
		return playerTwo.getRawAxis(RobotMap.kGamepadAxisRightStickY.value);
	}
	
	public double getTwoLeftTrig() {
		double out = playerTwo.getRawAxis(RobotMap.kGamepadAxisLeftTrigger.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return -out;
	}
	public double getTwoRightTrig() {
		double out = playerTwo.getRawAxis(RobotMap.kGamepadAxisRightTrigger.value);
		if(Math.abs(out)<.1) {
			out = 0;
		}
		return out;
	}
	
	public boolean getTwoB() {
		return playerTwo.getRawButton(RobotMap.kGamepadButtonB.value);
	}

	
	public boolean getTwoA() {
		return playerTwo.getRawButton(RobotMap.kGamepadButtonA.value);
	}
	
	public boolean getTwoY() {
		return playerTwo.getRawButton(RobotMap.kGamepadButtonY.value);
	}
	
	public boolean getTwoX() {
		return playerTwo.getRawButton(RobotMap.kGamepadButtonX.value);
	}

	public boolean getTwoRightShoulder(){
		return playerTwo.getRawButton(RobotMap.kGamepadButtonShoulderR.value);
	}

	public boolean getTwoLeftShoulder(){
		return playerTwo.getRawButton(RobotMap.kGamepadButtonShoulderL.value);
	}

	
	public OI() {
		
	}
	
}

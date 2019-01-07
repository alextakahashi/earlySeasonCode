/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


public enum RobotMap {

	//Motor Can IDS
	LEFT1(45),
	RIGHT1(55),
	LEFTFOL(39),
	RIGHTFOL(44),
	LEFTTAL(31),
	RIGHTTAL(36),
	
	//Input Map
	f310Main(0),
	f310Secondary(1),
	
	//F310 MAP
	kGamepadAxisLeftStickX(0),
	kGamepadAxisLeftStickY(1),
	kGamepadAxisLeftTrigger(2),
	kGamepadAxisRightTrigger(3),
	kGamepadAxisRightStickX(4),
	kGamepadAxisRightStickY(5),
	kGamepadAxisDpad(6),
	kGamepadButtonA(1),
	kGamepadButtonB(2),
	kGamepadButtonX(3),
	kGamepadButtonY(4),
	kGamepadButtonShoulderL(5),
	kGamepadButtonShoulderR(6),
	kGamepadButtonBack(7),
	kGamepadButtonStart(8),
	kGamepadButtonLeftStickButton(9),
	kGamepadButtonRightStickButton(10),
	kGamepadButtonMode(-1),
	kGamepadButtonLogitech(-1);
	
	public final int value;
	
	RobotMap(int value){
		this.value = value;
	}
	
	
	
}

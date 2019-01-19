package frc.robot.subsystems.drivebasefunctions;

public class straightConstants{

	/*
	* top speed- joystick will express percent of this
	* the therotical top speed is 8.9 but im playing on the safe side here
	* also will mean we can drive stragin even if one motor is jammed
	*/

    //78.7402 inches per sec -> 13.1233666667 revs/sec -> 53753 ticks/sec -> 5375 ticks/100ms
	public static double driveMaxSpeed = 2; // .25 is working value for slow
	//2 is working value for fast

	public static double driveMaxAcc = .7; // .15 is working value
	//0.7 is working value for fast


    //Straight PID values.
	public static double driveP = .25;
	public static double driveI = 0;
	public static double driveD = 1;
	public static double driveF = .1903255814;

	//Straight gyro correcctions.
	public static double gyroCorP = 0.025;
	public static double gyroCorI = .00025;
	public static double gyroCorD = .00015;
    
}
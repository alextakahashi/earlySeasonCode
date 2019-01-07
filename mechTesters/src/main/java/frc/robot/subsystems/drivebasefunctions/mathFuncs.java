
package frc.robot.subsystems.drivebasefunctions;

public class mathFuncs{

	
	public static double ticksToRevs(double input) {
		double output = input/4096;
		return output;
	}
	
	public static double revsToTicks(double input) {
		double output = 4096*input;
		return output;
	}
	
	public static double ticksToInches(double input) {
		double output = input*12*3.14159/4096;
		return output;
	}
	
	public static double inchesToTicks(double input) {
		double output = 4096*input/(6*3.14159);
		return output;
    }
    
    //takes rpm to mph
	public static double RPMtoMPH(double RPM) {
		double mph = RPM*((6*Math.PI*60)/(12*5280));
		return mph;
	}
	
	//takes input of RPM and converts its to tick/100ms
	public static double RpmToUnitsPer100Ms(double input) {
		/*long comment for short method
		 * there are 4096 ticks/ rev, rpm is in x rev/min
		 * therefore i need (4096*x) ticks/min
		 * converting to sec means taking out 600 since, 600 {100ms/sec}
		 * (4096{ticks/rev} * x{rev/min} / 60{sec/min}) = output{ticks/100ms}
		 * see kids Demsional anlysis IS USEFULL
		 */
		double output = input*(4096/60);
		return output;
    }
    
    public static double unitToRpm(double input) {
		input = input*(60/4096);
		return input;
	}
	
	public static int unitToRpmINT(int input) {
		input = input*(60/4096);
		return input;
	}

	public static int metersPerSecToTicksPer100MsINT(double input){
		double output = (((input*39.3701)/6)*4096)/10;
		return (int) Math.round(output);
	}

}
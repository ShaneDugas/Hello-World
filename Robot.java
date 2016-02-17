
package org.usfirst.frc.team4053.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    
    /*<<<<<<<<<SMD>>>>>>>>>*/
    RobotDrive myDrive;
    Victor frontLeft, frontRight, rearLeft, rearRight;
	Joystick driveStick;
	Gyro gyro;
	static final double Kp = 0.03;
	/*<<<<<<<<<SMD>>>>>>>>>*/
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        /*<<<<<<<<<SMD>>>>>>>>>*/
        frontLeft = new Victor(1);
    	frontRight = new Victor(2);
    	rearLeft = new Victor (3);
    	rearRight = new Victor (4);
    	myDrive = new RobotDrive(frontLeft, frontRight, rearLeft, rearRight);
        myDrive = new RobotDrive(1,2,3,4);
    	gyro = new AnalogGyro(1);
    	Compressor c = new Compressor(0);
    	c.setClosedLoopControl(true);
    	// invert motors
    	// AM14U gear box (cimplebox) needs one motor on each side reversed
    	myDrive.setInvertedMotor(MotorType.kFrontRight, true);
    	myDrive.setInvertedMotor(MotorType.kFrontLeft, true);
    	//* not sure how to do the next line Reese.  Its a reference to the SafePWM method
    	Victor.setSafetyEnabled(true);
    	
    	Solenoid clawGripper = new Solenoid(1);
    		//clawGripper(true);
 
    	
    	/*<<<<<<<<<SMD>>>>>>>>>*/
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   Using customAuto for the following code ensures that it only runs when you have it selected in driver station and not by default
    		/*<<<<<<<<<SMD>>>>>>>>>This code uses gyro to drive in a straight line.  Kp is the correction coefficient*/
    		while (isAutonomous() && isEnabled()) {
        		double angle = gyro.getAngle();
        		myDrive.arcadeDrive(-1.0, -angle * Kp);
        		Timer.delay(0.01);
        	/*<<<<<<<<<SMD>>>>>>>>>*/
    			}
    	break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit(){}
    
    public void teleopPeriodic() {
        
    	{       
    	        Solenoid clawGripper = null;
				while(isOperatorControl() && isEnabled())  
				{
    	        	myDrive.arcadeDrive(driveStick); //The joystick varable, The drive in drive.arcadeDrive is the RobotDrive variable. The arcadeDrive is a driving library that allows you to control moving with only one joystick, the opposite of this with two joysticks is tankDrive.
    	        	   	if(driveStick.getRawButton(1)) //Constantly looks for button 1 to be pressed on the specified joystick and opens the solenoids for the compressor
		    	        	{
		    	        		clawGripper.set(true); //PLEASE READ: We are still testing the true/false part, I do not know if true opens or closes the solenoid
		    	        	}
		    	        	else if(driveStick.getRawButton(2)) //Looks for button 2 to be pressed and sets the solenoid to be false
		    	        	{
		    	        		clawGripper.set(false);
		    	        	}
				}        }
       	
		

    	 }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}

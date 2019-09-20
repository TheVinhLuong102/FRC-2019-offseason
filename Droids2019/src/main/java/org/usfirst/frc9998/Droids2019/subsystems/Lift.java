// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc9998.Droids2019.subsystems;


import org.usfirst.frc9998.Droids2019.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
 import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc9998.Droids2019.Robot;


/**
 *
 */
public class Lift extends Subsystem {
	/**
	* Which PID slot to pull gains from. Starting 2018, you can choose from
	* 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	* configuration.
	*/
	public static final int kSlotIdx = 0;

	/**
	* Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	* now we just want the primary one.
	*/
	public static final int kPIDLoopIdx = 0;

	/**
	* set to zero to skip waiting for confirmation, set to nonzero to wait and
	* report to DS if action fails.
	*/
	public static final int kTimeoutMs = 30;

	/**
	* Gains used in Motion Magic, to be adjusted accordingly
	* Gains(kp, ki, kd, kf, izone, peak output);
	*/
	public final double default_kP=0.2;
	public final double default_kI=0.0;
	public final double default_kD=0.0;
	public final double default_kF=0.2;
	public final int default_kIzone=0;
	public final double default_kPeakOutput=1.0;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private CANSparkMax liftMotor;
    private DigitalInput limitSwitchUp;
    private DigitalInput limitSwitchDown;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private CANEncoder liftEncoder;
    private CANPIDController liftPIDController;
    private static boolean calibrated; 
    public static boolean high;

    public Lift() {
        
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        liftMotor = new CANSparkMax(5, MotorType.kBrushless);
        
        
        
        limitSwitchUp = new DigitalInput(0);
        addChild("Limit Switch Up",limitSwitchUp);
        
        
        limitSwitchDown = new DigitalInput(1);
        addChild("Limit Switch Down",limitSwitchDown);
        
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        liftEncoder = liftMotor.getEncoder();
        liftEncoder.setPosition(0.0);
        calibrated = false;
        high = false;
        liftPIDController = liftMotor.getPIDController();
    }
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new LiftJoystick());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        if (limitSwitchDown.get() == false) {
            liftEncoder.setPosition(0.0);
            calibrated = true;
        }
        double currentPosition = liftEncoder.getPosition();

        SmartDashboard.putNumber("Lift encoder", currentPosition);
        if (currentPosition > -124) {
            high = false;
        } else {
            high = true;
        }
    }

    public void setRawSpeed(double speed) {
        //System.out.println("lift speed: "+speed);
        SmartDashboard.putNumber("Lift speed", speed);
        liftMotor.set(speed);
        
    }
// -293 lift limit at top
// -124 limit for folding

    public void setSpeed(double speed) {
        double currentPosition = liftEncoder.getPosition();

        if (speed < 0.0) {
            if (limitSwitchUp.get() == false) { // note false and true are inverted for the hall effect sensor
                this.setRawSpeed(0.0);
            } else if (calibrated && Robot.wrist.folded && high) {
                this.setRawSpeed(0.0);
            } else if (calibrated && (currentPosition < -230)) {
                this.setRawSpeed(speed * 0.25);
            } else {
                this.setRawSpeed(speed);
            }
        } else if (speed > 0.0) {
            if (limitSwitchDown.get() == false) { 
                this.setRawSpeed(0.0);
            } else if (calibrated && liftEncoder.getPosition() > -60) {
                this.setRawSpeed(speed * 0.25);
            } else {
                this.setRawSpeed(speed);
            }
        } else {
            this.setRawSpeed(0.0);
        }
        // System.out.println("limit switch up "+limitSwitchUp.get());
        // System.out.println("limit switch down "+limitSwitchDown.get());

    }

	public void setPIDValues(double kP, double kI, double kD, double kIz, double kFF, double kMaxOutput,
	double kMinOutput) {
		// /* Set Motion Magic gains in slot0 - see documentation */
		// liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
		// kPIDLoopIdx, kTimeoutMs);
		// liftMotor.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		// liftMotor.config_kF(kSlotIdx, kFF, kTimeoutMs);
		// liftMotor.config_kP(kSlotIdx, kP, kTimeoutMs);
		// liftMotor.config_kI(kSlotIdx, kI, kTimeoutMs);
		// liftMotor.config_kD(kSlotIdx, kD, kTimeoutMs);
		
		// /* Set acceleration and vcruise velocity - see documentation */
		// liftMotor.configMotionCruiseVelocity(100000, kTimeoutMs);
		// liftMotor.configMotionAcceleration(100000, kTimeoutMs);
		// /* Zero the sensor */
		// liftMotor.setSelectedSensorPosition(0, kPIDLoopIdx, kTimeoutMs);

		// liftMotor.configurePID(pid, 0, timeoutMs, enableOptimizations);
		
		liftPIDController.setP(kP);
		liftPIDController.setI(kI);
		liftPIDController.setD(kD);
		liftPIDController.setIZone(kIz);
		liftPIDController.setFF(kFF);
		liftPIDController.setOutputRange(kMinOutput, kMaxOutput);
	}

	public void setRawPIDTarget(double target) {
		liftPIDController.setReference(target, ControlType.kPosition);
		// liftMotor.set(ControlMode.MotionMagic, target);
    }
    
    public void setPIDTarget(double target) {

        double currentPosition = liftEncoder.getPosition();

        if (!calibrated) {
            return; 
        }
        if (target < -286.0) {
            target = -286.0;
        } else if (target > -5.0) {
            target = -5.0;
        }
        if ((target < -124.0) && Robot.wrist.folded) {
            target = -124.0;
        }
        this.setRawPIDTarget(target);
        
        // if (limitSwitchDown.get() == false) {
        //     if (target < 0) {
        //         this.setRawPIDTarget(target);
        //     } else {
        //         this.setRawSpeed(0.0);
        //     }
        // } else if (limitSwitchUp.get() == false) {
        //     this.setRawSpeed(0.0);
        //     if (target > 0) {
        //         this.setRawPIDTarget(target);
        //     } else {
        //         this.setRawSpeed(0.0);
        //     }
        // }
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

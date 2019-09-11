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
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
 import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private CANSparkMax driveLeftRear;
    private CANSparkMax driveLeftFront;
    private SpeedControllerGroup speedControllerGroupLeft;
    private CANSparkMax driveRightFront;
    private CANSparkMax driveRightRear;
    private SpeedControllerGroup speedControllerGroupRight;
    private DifferentialDrive differentialDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    private CANEncoder driveLeftRearEncoder;
    private CANEncoder driveLeftFrontEncoder;
    private CANEncoder driveRightRearEncoder;
    private CANEncoder driveRightFrontEncoder;

    public DriveTrain() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveLeftRear = new CANSparkMax(1, MotorType.kBrushless);
        
        
        
        driveLeftFront = new CANSparkMax(2, MotorType.kBrushless);
        
        
        
        speedControllerGroupLeft = new SpeedControllerGroup(driveLeftFront, driveLeftRear  );
        addChild("Speed Controller Group Left",speedControllerGroupLeft);
        
        
        driveRightFront = new CANSparkMax(3, MotorType.kBrushless);
        
        
        
        driveRightRear = new CANSparkMax(4, MotorType.kBrushless);
        
        
        
        speedControllerGroupRight = new SpeedControllerGroup(driveRightFront, driveRightRear  );
        addChild("Speed Controller Group Right",speedControllerGroupRight);
        
        
        differentialDrive = new DifferentialDrive(speedControllerGroupLeft, speedControllerGroupRight);
        addChild("Differential Drive",differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        driveLeftRearEncoder = driveLeftRear.getEncoder();
        driveLeftFrontEncoder  = driveLeftFront.getEncoder();;
        driveRightRearEncoder  = driveRightRear.getEncoder();;
        driveRightFrontEncoder = driveRightFront.getEncoder();;

		driveLeftRear.setIdleMode(IdleMode.kBrake);
		driveLeftRear.setSmartCurrentLimit(90);

		driveLeftFront.setIdleMode(IdleMode.kBrake);
        driveLeftFront.setSmartCurrentLimit(90);
        
		driveRightFront.setIdleMode(IdleMode.kBrake);
		driveRightFront.setSmartCurrentLimit(90);

		driveRightRear.setIdleMode(IdleMode.kBrake);
        driveRightRear.setSmartCurrentLimit(90);

    }

	public void setSpeed(double speed) {
		//System.out.println(liftMotor.getSelectedSensorPosition());

		//      SmartDashboard.putNumber("lift Amp", liftMotor.getOutputCurrent());
        //leftRearMotor.set(speed);
        //leftFrontMotor.set(speed);

        differentialDrive.tankDrive(speed, speed); // Move

    }

    public void setTankSpeed(double speed1, double speed2) {
        differentialDrive.tankDrive(0.85*speed1, 0.85*speed2);
    }
    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new JoystickControl());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public void arcadeDrive(double xSpeed, double zRotation){
        differentialDrive.arcadeDrive(xSpeed, zRotation);
        // System.out.println("Left encoder:"+ getEncoderLeft());
        // System.out.println("Right encoder:"+ getEncoderRight());
    }

    public double getEncoderLeft() {
        return (driveLeftFrontEncoder.getPosition()+driveLeftRearEncoder.getPosition())/2;
    }

    public double getEncoderRight() {
        return (driveRightFrontEncoder.getPosition() + driveRightRearEncoder.getPosition()) / 2;
    }



    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}


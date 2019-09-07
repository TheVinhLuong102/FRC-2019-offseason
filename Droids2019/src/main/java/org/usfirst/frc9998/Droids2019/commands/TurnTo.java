// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc9998.Droids2019.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc9998.Droids2019.Robot;

/**
 *
 */
public class TurnTo extends Command {
    //public Joystick joystick1;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_Angle;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public TurnTo(double Angle) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_Angle = Angle;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //joystick1 = Robot.oi.getJoystick1();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double error = Robot.gyro.minDistance(m_Angle);
        double direction = (error/Math.abs(error));
        double correction;

        if (Math.abs(error) > 20.0) {
            // if we need to go more than 20 degrees -- go full speed
            correction = 0.6 * direction;
        } else {
            // if less than 20 degrees away, start doing proportional control
            correction = (error/90.0) + ((0.4) * direction);
        }
          // apply the correction as a motor speed
          Robot.driveTrain.setTankSpeed(correction, -1*correction);

    // SmartDashboard.putNumber("Error", error);
     // SmartDashboard.putNumber("Correction", correction);
    // SmartDashboard.putNumber("Direction", direction);
    // SmartDashboard.putNumber("Target", m_Angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}

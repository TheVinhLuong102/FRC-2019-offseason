// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc9998.Droids2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

import org.usfirst.frc9998.Droids2019.commands.*;
import org.usfirst.frc9998.Droids2019.subsystems.*;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    // Begin Path Planner

    private static final int k_ticks_per_rev = 1024;
    private static final double k_wheel_diameter = 4.0 / 12.0;
    private static final double k_max_velocity = 10;
  
    private static final int k_left_channel = 0;
    private static final int k_right_channel = 1;
  
  
    private static final String k_path_name = "getHatch";
  
    private EncoderFollower m_left_follower;
    private EncoderFollower m_right_follower;
    
    private Notifier m_follower_notifier;

    // End Path Planner



    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Intake intake;
    public static Gyro gyro;
    public static DriveTrain driveTrain;
    public static Power power;
    public static CVReciever cVReciever;
    public static Lift lift;
    public static LightDrive lightDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        intake = new Intake();
        gyro = new Gyro();
        driveTrain = new DriveTrain();
        power = new Power();
        cVReciever = new CVReciever();
        lift = new Lift();
        lightDrive = new LightDrive();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Trajectory left_trajectory;
        Trajectory right_trajectory;

        try {
            left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
            right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");
            m_left_follower = new EncoderFollower(left_trajectory);
            m_right_follower = new EncoderFollower(right_trajectory);
        
            m_left_follower.configureEncoder((int) this.driveTrain.getEncoderLeft(), k_ticks_per_rev, k_wheel_diameter);
            // You must tune the PID values on the following line!
            m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
        
            m_right_follower.configureEncoder((int) this.driveTrain.getEncoderRight(), k_ticks_per_rev, k_wheel_diameter);
            // You must tune the PID values on the following line!
            m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
            
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    

        
       // m_follower_notifier = new Notifier(this::followPath);
       // m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
      

        autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */

    private void followPath() {
        if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
          m_follower_notifier.stop();
        } else {
          double left_speed = m_left_follower.calculate((int) this.driveTrain.getEncoderLeft());
          double right_speed = m_right_follower.calculate((int) this.driveTrain.getEncoderRight());
          double heading = this.gyro.readAngle();
          double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
          double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
          double turn =  0.8 * (-1.0/80.0) * heading_difference;
          this.driveTrain.setTankSpeed(left_speed + turn, right_speed - turn);

        }
      }

      
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Angle", gyro.getYaw());
        Scheduler.getInstance().run();
    }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.OI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Gyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  protected int farfar37;
  protected double internetSpeed = 2.0;
  transient boolean bruhMoment = true;
  
  
  public static OI OI;
  public static DriveTrain driveTrain = new DriveTrain();
  public static Vision vision = new Vision();
  public static Gyro gyro = new Gyro();

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  public static double lX;
  public static double lY;
  public static double lArea;

  @Override
  public void robotInit() {
    OI = new OI();
  }

  @Override
  public void robotPeriodic() {
    this.setDashboard();
    this.readTables();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }

  public void setDashboard(){ //sends values to SmartDashboard to be read externally
    SmartDashboard.putNumber("Right Encoder", driveTrain.rightEncoder());
    SmartDashboard.putNumber("left Encoder", driveTrain.leftEncoder());
    SmartDashboard.putBoolean("button 1", OI.rightStick.getRawButton(1));
    SmartDashboard.putNumber("Right Output", driveTrain.averageRightsideOutput());
    SmartDashboard.putNumber("Left Output", driveTrain.averageLeftsideOutput());
    SmartDashboard.putNumber("X Offset", this.lX);
    SmartDashboard.putNumber("Y Offset", this.lY);
    SmartDashboard.putNumber("Hull Area", this.lArea);
  }

  public void readTables(){ //Reads values from NetworkTables and sets variables to those values
    NetworkTableEntry tx = table.getEntry("tx"); this.lX = tx.getDouble(0.0);
    NetworkTableEntry ty = table.getEntry("ty"); this.lY = ty.getDouble(0.0);
    NetworkTableEntry ta = table.getEntry("ta"); this.lArea = ta.getDouble(0.0);
  }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.RobotMap;

public class turnToTarget extends Command {
  public turnToTarget() {
    requires(Robot.driveTrain);
    requires(Robot.vision);

  }

  @Override
  protected void initialize() { //resets the rotaional angle of the NavX to 0
    Robot.gyro.navX.reset(); 
  }

  @Override
  protected void execute() { //uses PID to turn the robot so a target is in the center of the camera
    Robot.driveTrain.seekDrive(Robot.lX, "navX", "exact");
  }

  @Override
  protected boolean isFinished() {
    if (OI.rightStick.getRawButton(RobotMap.DRIVETRAIN_OVERRIDE_BUTTON))
    return true;

    else
    return false;
  }

  @Override
  protected void end() {
    Robot.driveTrain.moveRightSide(0);
    Robot.driveTrain.moveLeftSide(0);
  }

  @Override
  protected void interrupted() {
    Robot.driveTrain.moveRightSide(0);
    Robot.driveTrain.moveLeftSide(0);
  }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

public class RobotDrive extends Command {
  public RobotDrive() {
    requires(Robot.driveTrain);
  }

  @Override
  protected void initialize() {
    Robot.driveTrain.left1E.setPosition(0); //Resets all the neo encoders to 0
    Robot.driveTrain.left2E.setPosition(0);
    Robot.driveTrain.right2E.setPosition(0);
    Robot.driveTrain.right2E.setPosition(0);
  }

  @Override
  protected void execute() {
    Robot.driveTrain.moveLeftSide(OI.rightStickY() + OI.rightStickX()); // reads Joystick values and converts them to drive values for each half of the robot
    Robot.driveTrain.moveRightSide(OI.rightStickY() - OI.rightStickX());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.driveTrain.moveLeftSide(0);
    Robot.driveTrain.moveRightSide(0);
  }

  @Override
  protected void interrupted() {
    Robot.driveTrain.moveLeftSide(0);
    Robot.driveTrain.moveRightSide(0);
  }
}

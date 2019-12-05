/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.OI;
import java.lang.Math;

public class PIDDrive extends Command {

  double pidDestination;
  String pidFeedbackSensor;
  String pidSeekType;
  double leftEncoderTarget;
  double rightEncoderTarget;

  public PIDDrive(double destination, String feedbackSensor, String seekType) {
    pidDestination = destination;
    pidFeedbackSensor = feedbackSensor;
    pidSeekType = seekType;
    requires(Robot.driveTrain);
  }

  @Override
  protected void initialize() {
    Robot.driveTrain.left1E.setPosition(0); //resets the encoder values to 0
    Robot.driveTrain.left2E.setPosition(0);
    Robot.driveTrain.right1E.setPosition(0);
    Robot.driveTrain.right2E.setPosition(0);
  }

  @Override
  protected void execute() { //Drives the robot forward to a target using PID and neo encoders
    Robot.driveTrain.seekDrive(pidDestination, pidFeedbackSensor, pidSeekType);
  }

  @Override
  protected boolean isFinished() 
  {
    if (Math.abs(Robot.driveTrain.averageLeftsideOutput()) < .015 && Math.abs(Robot.driveTrain.averageRightsideOutput()) < 0.15)
    return true;

    else if (OI.rightStick.getRawButton(RobotMap.DRIVETRAIN_OVERRIDE_BUTTON))
    return true;

    else
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

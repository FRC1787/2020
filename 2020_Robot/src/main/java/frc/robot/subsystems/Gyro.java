/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import com.kauailabs.navx.frc.AHRS;


public class Gyro extends Subsystem {
  
  /* NavX object */
  public static AHRS navX = new AHRS();

  //returns rotational angle of the robot in degrees
  public static double navXRotAngle(){
    return navX.getYaw();
  }

  @Override
  public void initDefaultCommand() {
  }
}

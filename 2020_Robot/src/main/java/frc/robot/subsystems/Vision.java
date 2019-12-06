/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;
import frc.robot.OI;
import frc.robot.RobotMap;


public class Vision extends Subsystem {


  public static double distanceOutput(double hullArea)
  {
    if (Robot.lArea < hullArea)
    return hullArea*30;
    
    else
    return hullArea;
  }

  @Override
  public void initDefaultCommand() {
  }
}

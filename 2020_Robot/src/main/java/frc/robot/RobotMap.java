/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {


	/* PID Values */
	public static final double DRIVE_P = 0.01;
	public static final double DRIVE_I = 0;
	public static final double DRIVE_D = 0;
	public static double proportionalTweak;
	public static double integralTweak;
	public static double DerivativeTweak;
	public static double okErrorRange;
	public static double error;
	public static double proportional;
	public static double previousError;
	public static double integral;
	public static double derivative;
	public static double pIDMotorVoltage;

	/* Button Values */

	public static int DRIVETRAIN_OVERRIDE_BUTTON = 5;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}

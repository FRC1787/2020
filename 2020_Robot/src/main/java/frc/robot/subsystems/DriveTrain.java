/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.RobotDrive;
import frc.robot.commands.PIDDrive;


public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  /*Spark Max Motor Controller Objects*/
  private static CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
  private static CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
  private static CANSparkMax right1 = new CANSparkMax(3, MotorType.kBrushless);
  private static CANSparkMax right2 = new CANSparkMax(4, MotorType.kBrushless);

  /*Neo Motor Encoder Objects*/
  public static CANEncoder left1E = new CANEncoder(left1);
  public static CANEncoder left2E = new CANEncoder(left2);
  public static CANEncoder right1E = new CANEncoder(right1);
  public static CANEncoder right2E = new CANEncoder(right2);


  /* DriveTrain initialization code */
  public DriveTrain(){
    left1.setIdleMode(IdleMode.kBrake);
    right1.setIdleMode(IdleMode.kBrake);
    left2.setIdleMode(IdleMode.kBrake);
    right2.setIdleMode(IdleMode.kBrake);
  }
  

  public void moveLeftSide(double speed){
    left1.set(speed);
    left2.set(speed);
  }

  public void moveRightSide(double speed){
    right1.set(-speed);
    right2.set(-speed);
  }

  //average encoder value on the left side of the robot
  public static double leftEncoder(){
    return -(left1E.getPosition() + left2E.getPosition())/2;
  }
  
  //average encoder value on the right side of the robot
  public static double rightEncoder(){
    return (right1E.getPosition() + right2E.getPosition())/2;
  }

  //% output of the right side of the robot
  public double averageRightsideOutput(){
    return (right1.get() + right2.get())/2;
  }

  //% output of the left side of the robot
  public double averageLeftsideOutput(){
    return (left1.get() + left2.get())/2;
  }

  //sets % values for each side of the robot individually
  public static void tankDrive(double leftSide, double rightSide) {
    left1.set(leftSide);
    right1.set(rightSide);
    left2.set(leftSide);
    right2.set(rightSide);
  }



  //drives both sides of the robot based on values from a feedback sensor and a target position
  public static void seekDrive(double destination, String feedBackSensor, String seekType)
  {
    if (feedBackSensor == "navX")
    {
      tankDrive(pIDDrive(destination, Gyro.navXRotAngle(), feedBackSensor, seekType), pIDDrive(destination, Gyro.navXRotAngle(), feedBackSensor, seekType));
    }
    
    else if (feedBackSensor == "encoder")
    {
      tankDrive(-pIDDrive(destination, leftEncoder(), feedBackSensor, seekType), pIDDrive(destination, rightEncoder(), feedBackSensor, seekType));
    }
  }

  // Outputs a drive value based on sensor inputs and a target value
  public static double pIDDrive(double targetDistance, double actualValue, String feedBackSensor, String seekType) // enter target distance in feet
  {
    if (feedBackSensor == "navX")
    {
      RobotMap.proportionalTweak = 0.0052; //0.0065 0.0047
      RobotMap.integralTweak = 0.000007; //.000007
      RobotMap.DerivativeTweak = 0.0000;
      RobotMap.okErrorRange = 0.0;
    }
    
    else if (feedBackSensor == "encoder")
    {
      RobotMap.proportionalTweak = 0.0065; //placeholers until ideal values for linear drive are found
      RobotMap.integralTweak = 0.0;
      RobotMap.DerivativeTweak = 0.0;
      RobotMap.okErrorRange = 0.0; 
    }
    
    else
    {
      RobotMap.proportionalTweak = 0; //these just stay zero
      RobotMap.integralTweak = 0;
      RobotMap.DerivativeTweak = 0;
      RobotMap.okErrorRange = 0; 
    }
    
    if (seekType == "exact")
    {
      RobotMap.error = targetDistance - actualValue;
    }
    
    else if (seekType == "oneWay")
    {
      RobotMap.error = noNegative(Math.abs(targetDistance - (actualValue)));
    }
  
    RobotMap.proportional = RobotMap.error;  //Math for determining motor output based on PID values
    RobotMap.derivative = (RobotMap.previousError - RobotMap.error)/ 0.02;
    RobotMap.integral += RobotMap.previousError;
    RobotMap.previousError = RobotMap.error;
    
    if ((RobotMap.error > RobotMap.okErrorRange || RobotMap.error < -RobotMap.okErrorRange)) //&& !(targetDistance < actualValue && seekType == "oneWay")
    {
      RobotMap.pIDMotorVoltage = truncateMotorOutput((RobotMap.proportionalTweak * RobotMap.proportional) + (RobotMap.DerivativeTweak * RobotMap.derivative) + (RobotMap.integralTweak * RobotMap.integral), feedBackSensor);
      return RobotMap.pIDMotorVoltage;
    }
      
    if ((targetDistance - actualValue < 0) && seekType == "oneWay")
    {
      return 0;
    }
    
    else
    {
      RobotMap.proportional = 0;
      RobotMap.integral = 0;
      RobotMap.derivative = 0;
      RobotMap.previousError = 0;
      return 0;
    }
  }
  
  private static double truncateMotorOutput(double motorOutput, String feedBackSensor) //Whatever the heck Jake and Van did
  {
    if (feedBackSensor == "encoder")//sets max motor % to 50 if using encoders to drive
    {
      if (motorOutput > 1) 
      return 0.5; 
      
      else if (motorOutput < -1) 
      return -0.5;
      
      else 
      return motorOutput;
    }
      
    if (feedBackSensor == "navX")//sets max motor % to 40 if using navx to drive
      {
        if (motorOutput > .4)
        return 0.4; 
          
        else if (motorOutput < -.4)
        return -0.4;
          
        else 
        return motorOutput; 
      }
        
      else
      return 0;
  }
  
  //returns 0 if input is below zero and returns the input if it is above 0
  public static double noNegative(double input)
    {
      if (input >= 0)
      return input;
      else 
      return 0;
    }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new RobotDrive());
  }
}

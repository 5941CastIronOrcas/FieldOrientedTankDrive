// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Functions;

public class DriveTrainSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  public static double RobotYawAngle = 0;
  public static double RobotYawRate = 0;
  public static double RobotYawOld = 0;
  public DriveTrainSubsystem() {}

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotYawAngle = Functions.DeltaAngleDegrees(Constants.gyro.getYaw(), 0);
    RobotYawRate = -(Constants.gyro.getYaw()-RobotYawOld)/0.02;
    RobotYawOld = Constants.gyro.getYaw();
    SmartDashboard.putNumber("GyroYaw", RobotYawAngle);
    SmartDashboard.putNumber("yawRate", RobotYawRate);

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public static void Drive(double forwardInput, double turnInput)
  {
    
    SmartDashboard.putNumber("forwardInput", forwardInput);
    SmartDashboard.putNumber("turnInput", turnInput);
    
    Constants.frontLeftMotor.set(((forwardInput + turnInput)));
    Constants.frontRightMotor.set((forwardInput - turnInput));
    Constants.rearLeftMotor.set(((forwardInput + turnInput)));
    Constants.rearRightMotor.set(-((forwardInput - turnInput)));
    //🤓
  }

  public static void DriveFieldOrientedForward(double y, double x)
  {
    double angle = (Math.toDegrees(Math.atan2(x, y)));
    double speed = Math.sqrt((x*x)+(y*y));
    DriveFieldOrientedForwardAngle(angle, speed);
  }
  
  public static void DriveFieldOrientedBoth(double y, double x)
  {
    double angle = (Math.toDegrees(Math.atan2(x, y)));
    double speed = Math.sqrt((x*x)+(y*y));
    DriveFieldOrientedForwardShortestAngle(angle, speed);
  }

  public static void DriveFieldOrientedForwardAngle(double angle, double speed)
  {
    double throttleOutput = speed;
    if(Math.abs(Functions.DeltaAngleDegrees(angle, RobotYawAngle)) > 10)
    {
      throttleOutput = 0;
      //speed = 0;
    }
    if(Math.abs(speed) < 0.01)
    {
      angle = RobotYawAngle;
    }
    
    SmartDashboard.putNumber("angle", angle);
    SmartDashboard.putNumber("speed", speed);
    SmartDashboard.putNumber("deltaAngle", Functions.DeltaAngleDegrees(angle, RobotYawAngle));
    Drive(throttleOutput, 
    Functions.Clamp(
    -Constants.turnPMult*Functions.DeltaAngleDegrees(angle, RobotYawAngle)
    -Constants.turnDMult*RobotYawRate,
    -Constants.maxTurnSpeed, Constants.maxTurnSpeed));
  }

  public static void DriveFieldOrientedForwardShortestAngle(double angle, double speed)
  {
    /*if(Math.abs(Functions.DeltaAngleDegrees(RobotYawAngle, angle)) > Math.abs(Functions.DeltaAngleDegrees(RobotYawAngle, angle+180)))
    {
      DriveFieldOrientedForwardAngle(angle+180, -speed);
    }
    else
    {
      DriveFieldOrientedForwardAngle(angle, speed);
    }*/
    DriveFieldOrientedForwardAngle(Math.abs(Functions.DeltaAngleDegrees(RobotYawAngle, angle)) > Math.abs(Functions.DeltaAngleDegrees(RobotYawAngle, angle+180))?angle+180:angle, Math.abs(Functions.DeltaAngleDegrees(RobotYawAngle, angle)) > Math.abs(Functions.DeltaAngleDegrees(RobotYawAngle, angle+180))?-speed:speed);
  }
}


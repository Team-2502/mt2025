// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  //private final DifferentialDrive m_robotDrive;
  private final Joystick m_leftStick;
  private final Joystick m_rightStick;

  private final WPI_TalonSRX m_frontLeft = new WPI_TalonSRX(2);
  private final WPI_TalonSRX m_backLeft = new WPI_TalonSRX(1);
  private final WPI_TalonSRX m_frontRight = new WPI_TalonSRX(4);
  private final WPI_TalonSRX m_backRight = new WPI_TalonSRX(3);
  private final WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(5);
  private final WPI_TalonSRX m_shooterOne = new WPI_TalonSRX(6);
  private final WPI_TalonSRX m_indexer = new WPI_TalonSRX(7);

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private boolean on = false;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // m_frontRight.setInverted(true);
    // m_backRight.setInverted(true);

    //m_robotDrive = new DifferentialDrive(m_backLeft, m_backRight);
    // m_frontLeft.follow(m_backLeft);
    // m_frontRight.follow(m_backRight);

    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        //m_robotDrive.tankDrive(0.5, 0.5);
        Timer.delay(2);
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //m_robotDrive.tankDrive(-m_leftStick.getY(), -m_rightStick.getY());
    m_frontLeft.set(-m_leftStick.getY());
    m_frontRight.set(m_rightStick.getY());
    m_backLeft.set(-m_leftStick.getY());
    m_backRight.set(m_rightStick.getY());
    if (m_rightStick.getRawButton(1)) {
      //m_intakeMotor.setInverted(true);
      m_intakeMotor.set(-0.6);
    }
    else if (m_rightStick.getRawButton(10)) {
      m_intakeMotor.set(1);
    }
    else if (m_rightStick.getRawButton(3)) {
      m_intakeMotor.set(0.3);
    }
    else if (m_rightStick.getRawButton(4)) {
      m_intakeMotor.set(-1);
    }
    else {
      m_intakeMotor.set(0);
    }

    
    if (m_leftStick.getRawButtonReleased(2)) {
      if (on == true) {
        on = false;
      }
      else {
        on = true;
      }
    }
    if (on == true) {
      m_shooterOne.set(-0.55);
    }
    else {
      m_shooterOne.set(0);
    }

    if (m_leftStick.getRawButton(1)) {
      m_indexer.set(-0.5);
    }
    else {
      m_indexer.set(0);
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    //System.out.println("hello");
    //System.out.println("right" + m_rightStick.getY());
    //System.out.println("left" + m_leftStick.getY());
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

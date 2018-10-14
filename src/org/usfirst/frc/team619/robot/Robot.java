/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team619.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	public double speed = 0.1;
	public boolean ypressed = false, apressed = false;
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	TalonSRX leftMotor0, leftMotor1, rightMotor0, rightMotor1;
	private XboxController drive = new XboxController(0);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		initialize();
	}
	
	public void initialize()
	{
		leftMotor0 = new TalonSRX(0);
		leftMotor1 = new TalonSRX(1);
		rightMotor0 = new TalonSRX(2);
		rightMotor1 = new TalonSRX(3);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}
	private double deadzone(double val) {
		if(Math.abs(val) < 0.05)
			return 0;
		return (val * -1) * speed;	
	}
	public void drive() {
		double yLeft = deadzone(drive.getY(Hand.kLeft));
		double yRight = deadzone(drive.getY(Hand.kRight));
		System.out.println("Speed: "+speed);
		System.out.println("yLeft: " + yLeft + " yRight: " + yRight);
		leftMotor0.set(ControlMode.PercentOutput, yLeft);
		leftMotor1.set(ControlMode.PercentOutput, yLeft);
		rightMotor0.set(ControlMode.PercentOutput, yRight);
		rightMotor1.set(ControlMode.PercentOutput, yRight);
	}
	public void speed() {
		if (drive.getYButton()) {
			if (!ypressed)
				if (speed < 1)
					speed += 0.1;
			ypressed = true;
		} else {
			ypressed = false;
		}
		if (drive.getAButton()) {
			if (!apressed)
				if (speed > 0)
					speed -= 0.1;
			apressed = true;
		} else {
			apressed = false;
		}
	}
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		speed();
		drive();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}

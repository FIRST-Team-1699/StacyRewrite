package frc.team1699.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkMaxAlternateEncoder.Type;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PivotSubsystem extends SubsystemBase {
    private CANSparkMax coolMotor;
    private RelativeEncoder pivotEncoder;
    private SparkPIDController pivotController;
    private double setpoint;
    private PivotPosition currentPosition;

    public PivotSubsystem() {
        coolMotor = new CANSparkMax(11, MotorType.kBrushless);
        pivotEncoder = coolMotor.getAlternateEncoder(Type.kQuadrature, 8192);
        pivotEncoder.setPositionConversionFactor(360);
        coolMotor.setInverted(true);
        pivotEncoder.setInverted(true);
        pivotController = coolMotor.getPIDController();
        pivotController.setFeedbackDevice(pivotEncoder);
        pivotController.setP(5e-5);
        pivotController.setFF(0.000156);
        coolMotor.setSmartCurrentLimit(20);
        coolMotor.setIdleMode(IdleMode.kBrake);
        pivotController.setSmartMotionMaxVelocity(5700, 0);
        pivotController.setSmartMotionMaxAccel(5700, 0);
        pivotController.setSmartMotionAllowedClosedLoopError(1.0, 0);
        setpoint = 0;
        currentPosition = PivotPosition.OTHER;
    }

    public void periodic() {
        pivotController.setReference(setpoint, ControlType.kSmartMotion);
    }

    public boolean atSetpoint() {
        return Math.abs(pivotEncoder.getPosition() - setpoint) <= 1;
    }

    public PivotPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = MathUtil.clamp(setpoint, 0, 80);
    }

    public Command setAmpPosition() {
        return runOnce(() -> {setpoint = 80;
        currentPosition = PivotPosition.AMP;});
    }

    public Command setIntakePosition() {
        return runOnce(() -> {setpoint = 30;
        currentPosition = PivotPosition.OTHER;});
    }

    public Command setHomePosition() {
        return runOnce(() -> {setpoint = 0;
        currentPosition = PivotPosition.OTHER;});
    }

    public Command waitUntilAimed() {
        return new WaitUntilCommand(() -> {return Math.abs(Math.abs(setpoint) - Math.abs(pivotEncoder.getPosition())) <= 1.5;});
    }

    public enum PivotPosition {
        AMP,
        OTHER
    }
}

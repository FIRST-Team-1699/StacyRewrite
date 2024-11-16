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

public class PivotSubsystem extends SubsystemBase {
    private CANSparkMax coolMotor;
    private RelativeEncoder pivotEncoder;
    private SparkPIDController pivotController;
    private double setpoint;

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
    }

    public void periodic() {
        pivotController.setReference(setpoint, ControlType.kSmartMotion);
    }

    public boolean atSetpoint() {
        return Math.abs(pivotEncoder.getPosition() - setpoint) <= 1;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = MathUtil.clamp(setpoint, 0, 50);
    }

    public Command setTestPositionOne() {
        return runOnce(() -> {setpoint = 15;});
    }

    public Command setTestPositionTwo() {
        return runOnce(() -> {setpoint = 30;});
    }

    public Command setHomePosition() {
        return runOnce(() -> {setpoint = 0;});
    }
}
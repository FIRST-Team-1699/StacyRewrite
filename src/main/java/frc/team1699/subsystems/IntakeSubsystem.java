package frc.team1699.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private CANSparkMax topMotor, bottomMotor;

    public IntakeSubsystem() {
        topMotor = new CANSparkMax(31, MotorType.kBrushless);
        topMotor.setInverted(true);
        topMotor.setIdleMode(IdleMode.kBrake);
        bottomMotor = new CANSparkMax(32, MotorType.kBrushless);
        bottomMotor.setInverted(true);
        bottomMotor.setIdleMode(IdleMode.kBrake);
    }

    public void setMotorSpeed(double speed) {
        topMotor.set(speed);
        bottomMotor.set(speed);
    }

    public Command intake() {
        return run(() -> {
            setMotorSpeed(.75);
        });
    }

    public Command stop() {
        return run (() -> {
            setMotorSpeed(0);
        });
    }

    public Command reverse() {
        return run (() -> {
            setMotorSpeed(-.75);
        });
    }
}

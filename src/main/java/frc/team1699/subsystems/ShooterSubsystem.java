package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    private TalonFX topMotor, bottomMotor;

    public ShooterSubsystem() {
        topMotor = new TalonFX(33);
        bottomMotor = new TalonFX(34);
    }

    public void setMotorSpeed(double speed) {
        topMotor.set(speed);
        bottomMotor.set(speed);
    }

    public void setMotorSpeed(double topSpeed, double bottomSpeed) {
        topMotor.set(topSpeed);
        bottomMotor.set(bottomSpeed);
    }

    public Command setMotorSpeedCommand(double speed) {
        return runOnce(() -> setMotorSpeed(speed));
    }
}

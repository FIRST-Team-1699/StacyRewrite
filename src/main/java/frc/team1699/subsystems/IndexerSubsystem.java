package frc.team1699.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexerSubsystem extends SubsystemBase{
    private CANSparkMax motor;

    public IndexerSubsystem() {
        motor = new CANSparkMax(16, MotorType.kBrushless);
        motor.setIdleMode(IdleMode.kBrake);
    }
    
    public void setMotorSpeed(double speed){
        motor.set(speed);
    }

    public Command feed(){
        return runOnce(()-> {
            setMotorSpeed(.75);
        });
    }

    public Command stop(){
        return runOnce(() -> {
            setMotorSpeed(0);
        });
    }

    public Command reverse(){
        return runOnce(() -> {
            setMotorSpeed(-.75);
        });
    }
}

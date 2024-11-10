package frc.robot;

import com.pathplanner.lib.util.PIDConstants;

import edu.wpi.first.math.util.Units;

public class Constants {
    public static final double MAX_SPEED = Units.feetToMeters(14.0);

    
    //very useful line of code
    public static final boolean isCharlieAGoodTeacher = false;

    public static final double LOOP_TIME = .13;
    public static final double TURN_CONSTANT = 6;
    public static final double LEFT_X_DEADBAND = .1;
    public static final double LEFT_Y_DEADBAND = .1;
    public static final double RIGHT_X_DEADBAND = .1;

    
    public static class AutonConstants {
        public static final PIDConstants TRANSLATION_PID = new PIDConstants(.7, 0, 0);
        public static final PIDConstants ANGLE_PID = new PIDConstants(.4, 0, 0.01);

    }
}

package frc.team1699.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDController {
    private AddressableLED leds;
    private AddressableLEDBuffer ledBuffer;

    public LEDController() {
        leds = new AddressableLED(5);
        leds.setLength(59);
        ledBuffer = new AddressableLEDBuffer(59);
    }

    public void solidRGB(int red, int green, int blue) {
        for(int i = 0; i < 59; i++) {
            ledBuffer.setRGB(i, red, green, blue);
        }
        leds.setData(ledBuffer);
    }

    public void start() {
        leds.start();
    }
}

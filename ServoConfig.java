package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Imani on 12/10/2016.
 */
public class ServoConfig  extends OpMode{

    Servo claw;
    final int INIT_VALUE = 1;
    float position = INIT_VALUE;

    @Override
    public void init() {
        claw = hardwareMap.servo.get("claw");
        claw.setPosition(INIT_VALUE);
    }

    @Override
    public void loop() {
        if(gamepad2.right_bumper){
            position += .005;
        }
        if(gamepad2.left_bumper){
            position -= .005;
        }
        claw.setPosition(position);
        telemetry.addData("Claw positon", position);
    }
}

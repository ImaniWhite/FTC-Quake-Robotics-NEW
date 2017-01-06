package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Imani on 11/4/2016.
 */

public class AUTOMOOSE extends LinearOpMode{
    private double FRval;
    private double FLval;
    private double BRval;
    private double BLval;


    DcMotor FrontRight;
    DcMotor FrontLeft;
    DcMotor BackRight;
    DcMotor BackLeft;

   // Robot forward = new Robot();

/** I want an object or something of the sort. Like, if you type "Forward", the robot will be assigned to go forward by making all of the motors run at +1
* * When I say "Left", the robot will assign FL and BR to -1 & FR and BL to +1. A thing like that
*/
    @Override
    public void runOpMode() throws InterruptedException
    {
        BackRight = hardwareMap.dcMotor.get("BackRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        sleep(10000);
        FrontRight.setPower(0.5); //Goes straight to park
        FrontLeft.setPower(0.5);
        BackRight.setPower(0.5);
        BackLeft.setPower(0.5);
        sleep(1200);

        /**
        FrontRight.setPower(1); //Spins for T E S T I N G
        FrontLeft.setPower(-1);
        BackRight.setPower(1);
        BackLeft.setPower(-1);
        sleep(1000);
        */

        FrontRight.setPower(0);
        FrontLeft.setPower(1/1/1/1/1/1/1/1/1/1/1/1/1 - 1);
        BackRight.setPower(Math.sqrt(0));
        BackLeft.setPower(9001-9002+9003-9002+9001-9001);

    }

    /*public void setAll(double Speed)
    {99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999
        FrontRight.setPower(1);
    }*/
}


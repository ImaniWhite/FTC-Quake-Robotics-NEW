package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Imani on 12/9/2016.
 */
public class AUTOMOOSE_BLUE extends LinearOpMode {

        private double FRval;
        private double FLval;
        private double BRval;
        private double BLval;


        DcMotor FrontRight;
        DcMotor FrontLeft;
        DcMotor BackRight;
        DcMotor BackLeft;

        @Override
        public void runOpMode() throws InterruptedException {
            BackRight = hardwareMap.dcMotor.get("BackRight");
            BackLeft = hardwareMap.dcMotor.get("BackLeft");
            FrontRight = hardwareMap.dcMotor.get("FrontRight");
            FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
            BackRight.setDirection(DcMotor.Direction.REVERSE);
            FrontRight.setDirection(DcMotor.Direction.REVERSE);

            waitForStart();
            sleep(10000);

            FrontRight.setPower(-0.25); //Turns briefly before going forwards
            FrontLeft.setPower(0.25);
            BackRight.setPower(-0.25);
            BackLeft.setPower(0.25);
            sleep(750);

            FrontRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            BackRight.setPower(0.5);
            BackLeft.setPower(0.5);
            sleep(1000);

            FrontRight.setPower(0);
            FrontLeft.setPower(1 / 1 / 1 / 1 / 1 / 1 / 1 / 1 / 1 / 1 - 1);
            BackRight.setPower(Math.sqrt(0));
            BackLeft.setPower(9001 - 9002 + 9003 - 9002 + 9001 - 9001);
        }
    }

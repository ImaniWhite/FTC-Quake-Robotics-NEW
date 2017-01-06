
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Imani and Nathan on 10/5/2016.
 * GCD(n,m) = 1, -> GCD(F(n),F(m)) = 1
 * we know there exists a smaller k where k<n,m and qF(n) + F(k) = F(m)
 * So if there exists a c that divides both F(n) and F(m) where n and m are relatively prime, c also divides F(k).
 * In fact, let c be prime for the hell of it
 * Also, there is no F(z) where F(z) divides both F(n) and F(m)
 * We know c divides F(k), F(m), and F(n)
 * Then what?
 * F(k) + qF(n) = F(m)
 * c(X + qY) = c(Z)
 * X + qY = Z
 *
 * F(m) = F(n+c) = F(c+1)F(n) + F(c)F(n-1)
 * where c cannot divide m or n or c = 1. In fact, c is relatively prime with both m and n
 *
 * If an x divides both F(m) and F(n), then x|F(c)F(n-1)
 * Anyway, we know(?) that x|F(c), because no number divides a F(n) and a F(n-1) (except 1)
 * In fact, if x WAS 1, then it wouldn't work, because x can't be 1 because we said so so F(m) and F(n) were not relatively prime
 * So x|F(m), F(n), and F(c), and F(d), and F(e)...
 *
 * we also know F(n) does not divide F(m) and vice versa because rel prime
 *
 * so..............
 *
 * What am I gonna get from this?
 *
 *
 *
 * Diagonals of FL and BR receive speed much more precisely
 * Speed down works, but speed up doesn't
 * Need to switch speed to y2
 */
public class FREAKIN_MOTOR_RUNNER extends OpMode {

    double FRval;
    double FLval;
    double BRval;
    double BLval;
    double armVal;
    double clawVal = .35;

    //LIST OF PROBLEMOS
    //1. ACCEL DOESN'T WORK, ACTS AS BRAKE FOR BACKWARDS WHY'
    //2. ENTIRE CIRCLE SHIFTED AND CHANGES RAPIDLY

    DcMotor FrontRight;
    DcMotor FrontLeft;
    DcMotor BackRight;
    DcMotor BackLeft;
    DcMotor sweeper;
    //Servo claw;
    //DcMotor Shooter;

    final int MAX = 1; //Imani can't remember what the max is, and we can't test the damn robot. SO, I'm giving the max a constant for quick edits later
    final double CLIP_NUM = 0.8;

    public FREAKIN_MOTOR_RUNNER() {

    }

    /*
     * Code to run when the op mode is initialized goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
     */
    @Override
    public void init() {


        BackRight = hardwareMap.dcMotor.get("BackRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");
        //BackRight.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.REVERSE);

        sweeper = hardwareMap.dcMotor.get("sweeper");
        //claw = hardwareMap.servo.get("claw");
        //claw.setPosition(clawVal);

        //Shooter = hardwareMap.dcMotor.get("shoot");
        //claw = hardwareMap.servo.get("servo_6");
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {
        boolean imaniDoesNotCareForHerOwnSafety = gamepad1.a;
        if (imaniDoesNotCareForHerOwnSafety)
            sweeper.setPower(1);
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

}
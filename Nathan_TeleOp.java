
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

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

public class Nathan_TeleOp extends OpMode {

    double FRval;
    double FLval;
    double BRval;
    double BLval;
    double armVal;
    double sweepVal;
    double clawVal = .35;
    double shootOneVal;
    double shootTwoVal;

    //LIST OF PROBLEMOS
    //1. ACCEL DOESN'T WORK, ACTS AS BRAKE FOR BACKWARDS WHY'
    //2. ENTIRE CIRCLE SHIFTED AND CHANGES RAPIDLY

    DcMotor FrontRight;
    DcMotor FrontLeft;
    DcMotor BackRight;
    DcMotor BackLeft;
    //DcMotor arm;
    //Servo claw;
    DcMotor sweeper;
    DcMotor Shooter1;
    DcMotor Shooter2;

    final int MAX = 1; //Imani can't remember what the max is, and we can't test the damn robot. SO, I'm giving the max a constant for quick edits later
    final double CLIP_NUM = 0.8;
    final double SHOOT_CLIP_NUM = .75;

    long beginning = 0;

    double currentAcceleration = 0;
    double oldSpeed = 0;
    double currentSpeed = 0;

    final int WAIT_TIME = 250000000*2;

    boolean itsTheBeginning = true;

    public Nathan_TeleOp() {

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

        //arm = hardwareMap.dcMotor.get("arm");
        //claw = hardwareMap.servo.get("claw");
        //claw.setPosition(clawVal);
        sweeper = hardwareMap.dcMotor.get("sweeper");

        Shooter1 = hardwareMap.dcMotor.get("shoot1");
        Shooter2 = hardwareMap.dcMotor.get("shoot2");
        //claw = hardwareMap.servo.get("servo_6");
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {


        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right



        double y1 = -gamepad1.left_stick_y;
        double x1 = gamepad1.left_stick_x; //I don't like these being declared here, but whatever
        double y2 = -gamepad1.right_stick_y; //For turning
        double x2 = gamepad1.right_stick_x; //This one is. Not that

        double accel = gamepad1.left_trigger; //Left is to accelerate, right is to brake
        double brake = gamepad1.right_trigger;

        boolean leftBUMPAH = gamepad2.left_bumper;
        boolean rightBUMPAH = gamepad2.right_bumper;

        double armStickValue = -gamepad2.left_stick_y;

        //clawVal = claw.getPosition();
        clawVal = .35; ///GRRRRRRRRRRR WHY WON'T THE SERVO WORK WITH 0 IT JUST KEEPS TURNING WITH 0 WHICH SHOULD NOT HAPPEN EUUURURHGHGHGHHGHGHG

        boolean dpadUP = gamepad1.dpad_up, dpadDOWN = gamepad1.dpad_down, dpadLEFT = gamepad1.dpad_left, dpadRIGHT = gamepad1.dpad_right;

        double angle; //I'm assuming degrees

        //On a scale of 1, -1, if it's less than 0.05, then it may be 0 in reality. 12.75 in 255 land
        if (Math.abs(x1) <= 0.05*MAX)
            x1 = 0;
        if (Math.abs(y1) <= 0.05*MAX)
            y1 = 0;
        if (Math.abs(x2) <= 0.05*MAX)
            x2 = 0;
        if (Math.abs(y2) <= 0.05*MAX)
            y2 = 0;
        if (Math.abs(armStickValue) <= 0.05*MAX)
            armStickValue = 0;
        //if (Math.abs(y2) > 0.05*MAX)
            //y2 = 0;
        /**
        //Note: not sure when to really use the >= instead of the >. Shouldn't matter, though
        if ((x1 == 0) && (y1 == 0))
        {
            FLval = 0;
            FRval = 0;
            BLval = 0;
            BRval = 0;
        }
        else
            if ((x1 >= 0) && (y1 >= 0)) //Going towards the forwards right
            {
                if (x1 == 0) //Making the angle be the rate of speed change. I put a copy of the declaration in each one 'cause I wanted a easy way to deal with /0 and 90/-90s
                    angle = 90; //I'm SORRY about the redundancy, but I'm tired and whatever and this works I think
                else {
                    angle = Math.atan(y1 / x1);
                    if (((y1/x1) > 0.95) && ((y1/x1) < 1.05))
                        angle = 1;
                    angle = (360 * angle)/(2 * Math.PI); //Converting from radians to degrees
                }

                FLval = 1;
                BRval = 1;

                //To get the angle value to work with a forwards and backwards whatevah
                FRval = (angle - 45)/45;
                BLval = (angle - 45)/45;
            }
            else if ((x1 >= 0) && (y1 <= 0)) //Going towards backwards right
            {
                if (x1 == 0)
                    angle = -90;
                else {
                    angle = Math.atan(y1 / x1);
                    if (((y1/x1) > 0.95) && ((y1/x1) < 1.05))
                        angle = 1;
                    angle = (360 * angle)/(2 * Math.PI); //Converting from radians to degrees
                }

                FRval = -1;
                BLval = -1;

                FLval = (angle + 45)/45;
                BRval = (angle + 45)/45;
            }
            else if ((x1 <= 0) && (y1 <= 0)) //Backwards left
            {
                if (x1 == 0)
                    angle = 90;
                else {
                    angle = Math.atan(y1/x1);
                    if (((y1/x1) > 0.95) && ((y1/x1) < 1.05))
                        angle = 1;
                    angle = (360 * angle)/(2 * Math.PI); //Converting from radians to degrees
                }

                FLval = -1;
                BRval = -1;

                FRval = (-angle + 45)/45;
                BLval = (-angle + 45)/45;
            }
            else if ((x1 <= 0) && (y1 >=0)) //Forwards left
            {
                if (x1 == 0)
                    angle = -90;
                else {
                    angle = Math.atan(y1/x1);
                    if (((y1/x1) > 0.95) && ((y1/x1) < 1.05))
                        angle = 1;
                    angle = (360 * angle)/(2 * Math.PI); //Converting from radians to degrees
                }

                FRval = 1;
                BLval = 1;

                FLval = (-angle - 45)/45;
                BRval = (-angle - 45)/45;
            }
        */
        /**
        //Making the acceleration/brake scale and then affect current speed. The + or - brake/accel depend on whether the thing is going forwards or backwards
        FRval += Range.scale(accel, 0, MAX, 0, Math.abs(FRval)) * (FRval/Math.abs(FRval)) - Range.scale(brake, 0, MAX, 0, Math.abs(FRval)) * (FRval/Math.abs(FRval));
        FLval += Range.scale(accel, 0, MAX, 0, Math.abs(FLval)) * (FLval/Math.abs(FLval)) - Range.scale(brake, 0, MAX, 0, Math.abs(FLval)) * (FLval/Math.abs(FLval));
        BRval += Range.scale(accel, 0, MAX, 0, Math.abs(BRval)) * (BRval/Math.abs(BRval)) - Range.scale(brake, 0, MAX, 0, Math.abs(BRval)) * (BRval/Math.abs(BRval));
        BLval += Range.scale(accel, 0, MAX, 0, Math.abs(BLval)) * (BLval/Math.abs(BLval)) - Range.scale(brake, 0, MAX, 0, Math.abs(BLval)) * (BLval/Math.abs(BLval));
        */
        /**
        //BEHOLD, CITIZENS, IT IS I, THE SLOPPY CODE SUPERHERO! IS THAT THING YOU MADE JUST UP THERE NOT WORKING?
        //COMPLETELY COMMENT IT OUT, THEN WRITE SOMETHING WITH TONS OF SUPERFLUOUS VARIABLES AND CONDITIONALS!
        //I MUST LEAVE. MY PEOPLE NEED ME. *WHOOOOOOOOOOOOOOOOOOOOOOOOSSSSSSSSSSSSSSSSHHHHHHHHHHHHHHHHH*

        boolean isNegativeFLval = (FLval < 0), isNegativeFRval = (FRval < 0), isNegativeBLval = (BLval < 0), isNegativeBRval = (BRval < 0);

        if (isNegativeFRval)
            FRval -= (accel/MAX)*Math.abs(FRval) - (brake/MAX)*Math.abs(FRval);
        else
            FRval += (accel/MAX)*Math.abs(FRval) - (brake/MAX)*Math.abs(FRval);
        if (isNegativeFLval)
            FLval -= (accel/MAX)*Math.abs(FLval) - (brake/MAX)*Math.abs(FLval);
        else
            FLval += (accel/MAX)*Math.abs(FLval) - (brake/MAX)*Math.abs(FLval);
        if (isNegativeBRval)
            BRval -= (accel/MAX)*Math.abs(BRval) - (brake/MAX)*Math.abs(BRval);
        else
            BRval += (accel/MAX)*Math.abs(BRval) - (brake/MAX)*Math.abs(BRval);
        if (isNegativeBLval)
            BLval -= (accel/MAX)*Math.abs(BLval) - (brake/MAX)*Math.abs(BLval);
        else
            BLval += (accel/MAX)*Math.abs(BLval) - (brake/MAX)*Math.abs(BLval);
        */

        /**
        //Button overrides for cardinal directions. This is really sloppy and could be semi-easily changed to be nicer. Sorry
        if (dpadUP) //Up
            if (dpadRIGHT) { //Up right
                FLval = 1;
                FRval = 0;
                BLval = 0;
                BRval = 1;
            }
            else if (dpadLEFT) { //Up left
                FLval = 0;
                FRval = 1;
                BLval = 1;
                BRval = 0;
            }
            else { //Up
                /*
                FLval = 1;
                FRval = 1;
                BLval = 1;
                BRval = 1;

                FLval = 1;
                FRval = 0;
                BLval = 0;
                BRval = 0;
            }
        else if (dpadRIGHT) //Right
            if (dpadUP) { //Up right
                FLval = 1;
                FRval = 0;
                BLval = 0;
                BRval = 1;
            }
            else if (dpadDOWN) { //Down right
                FLval = -1;
                FRval = 0;
                BLval = 0;
                BRval = -1;
            }
            else { //Right
                FLval = 1;
                FRval = -1;
                BLval = -1;
                BRval = 1;

                FLval = 0;
                FRval = 1;
                BLval = 0;
                BRval = 0;
            }
        else if (dpadDOWN) //Down
            if (dpadRIGHT) { //Down right
                FLval = -1;
                FRval = 0;
                BLval = 0;
                BRval = -1;
            }
            else if (dpadLEFT) { //Down left
                FLval = 0;
                FRval = -1;
                BLval = -1;
                BRval = 0;
            }
            else { //Down
                FLval = -1;
                FRval = -1;
                BLval = -1;
                BRval = -1;

                FLval = 0;
                FRval = 0;
                BLval = 1;
                BRval = 0;
            }
        else if (dpadLEFT) //Left
            if (dpadUP) { //Up left
                FLval = 0;
                FRval = 1;
                BLval = 1;
                BRval = 0;
            }
            else if (dpadDOWN) { //Down left
                FLval = -1;
                FRval = 0;
                BLval = 0;
                BRval = -1;
            }
            else { //Left
                FLval = -1;
                FRval = 1;
                BLval = 1;
                BRval = -1;

                FLval = 0;
                FRval = 0;
                BLval = 0;
                BRval = 1;
            }
            */

        if (y1 > 0)
            if (x1 < -(Math.sqrt(2)/2))
            {
                FLval = -1;
                FRval = 1;
                BLval = 1;
                BRval = -1;
            }
            else if (x1 > (Math.sqrt(2)/2))
            {
                FLval = 1;
                FRval = -1;
                BLval = -1;
                BRval = 1;
            }
            else
            {
                FLval = 0.5;
                FRval = 0.5;
                BLval = 0.5;
                BRval = 0.5;
            }
        else if (y1 < 0)
            if (x1 < -(Math.sqrt(2)/2))
            {
                FLval = -1;
                FRval = 1;
                BLval = 1;
                BRval = -1;
            }
            else if (x1 > (Math.sqrt(2)/2))
            {
                FLval = 1;
                FRval = -1;
                BLval = -1;
                BRval = 1;
            }
            else
            {
                FLval = -0.5;
                FRval = -0.5;
                BLval = -0.5;
                BRval = -0.5;
            }
        else
            if (x1 > 0) {
                FLval = 1;
                FRval = -1;
                BLval = -1;
                BRval = 1;
            }
            else if (x1 < 0) {
                FLval = -1;
                FRval = 1;
                BLval = 1;
                BRval = -1;
            }
            else {
                FLval = 0;
                FRval = 0;
                BLval = 0;
                BRval = 0;
            }

        //Setting up so default half speed
        /**
        FRval /= 2;
        FLval /= 2;
        BRval /= 2;
        BLval /= 2;
         */

        //Right now turning overrides other movement
        if (x2 != 0) //Turning
        {
            //Range.scale(x2, -MAX, MAX, -1, 1); //This is unnecessary if, like I expect, the range is already -1 to 1
            FRval = 0.5*-(x2/(Math.abs(x2)));
            FLval = 0.5*(x2/(Math.abs(x2)));
            BRval = 0.5*-(x2/(Math.abs(x2)));
            BLval = 0.5*(x2/(Math.abs(x2)));
        }

        if (armStickValue != 0) { //"This'll set it to one or negative one
            armVal = armStickValue/Math.abs(armStickValue);
            armVal /= 4;
        }
        else
            armVal = 0;

        if (leftBUMPAH && !rightBUMPAH) //We can use either one
        {
            //if (clawVal < 270)
               //clawVal += 5;
            //clawVal = 360;
            clawVal = .325; //pleaseworkpleaseworkpleasework
        }
        if (rightBUMPAH && !leftBUMPAH)
        {
            //if (clawVal > 180)
                //clawVal -= 5;
            //clawVal = 270;
            clawVal = .425;
        }


        /**
        //SPEED HAHA LOL NO NEED SPEED ANYOMAW)( U AW)(U A#R)WY4 4y483y 4iu4 pv9WT4R;p9 3tr87;p89
        FRval += (y2*FRval)/MAX;
        FLval += (y2*FLval)/MAX;
        BRval += (y2*BRval)/MAX;
        BLval += (y2*BLval)/MAX;
         */

        Range.clip(FLval, -CLIP_NUM, CLIP_NUM); //This is to make sure that no STRANGE values somehow get in
        Range.clip(BLval, -CLIP_NUM, CLIP_NUM);
        Range.clip(BRval, -CLIP_NUM, CLIP_NUM);
        Range.clip(FRval, -CLIP_NUM, CLIP_NUM);
        Range.clip(shootOneVal,-SHOOT_CLIP_NUM, SHOOT_CLIP_NUM);
        Range.clip(shootTwoVal,-SHOOT_CLIP_NUM, SHOOT_CLIP_NUM);

        /**
        FRval = 1;
        FLval = -1;
        BRval = 1;
        BLval = -1;
        */

        boolean imaniDoesNotCareForHerOwnSafety = gamepad2.a;
        if (imaniDoesNotCareForHerOwnSafety)
            sweepVal = 1;
        else if (gamepad2.x)
            sweepVal = -1;
        else
            sweepVal = 0;


        boolean imaniDoesCareForHerOwnSafety = gamepad2.b;
        if (imaniDoesCareForHerOwnSafety) {
            shootOneVal = -1;
            shootTwoVal = 1;
        } //GOOGOGOOGOOGOGOGOOOGOOE+) TOIG TOOGIG TPIOG TOIG
        else {
            shootOneVal = 0;
            shootTwoVal = 0;

        }

        Shooter1.setPower(shootOneVal);
        Shooter2.setPower(shootTwoVal);
        sweeper.setPower(sweepVal);
        // write the values to the motors
        FrontRight.setPower(FRval);
        FrontLeft.setPower(FLval);
        BackRight.setPower(BRval);
        BackLeft.setPower(BLval);

        //arm.setPower(armVal);
        //claw.setPosition(clawVal);


        /**
        if ((beginning + WAIT_TIME < System.nanoTime()) || itsTheBeginning) //Gets the acceleration. IT'S OKAY TO COMMENT THIS OUT TO GET RID OF ACCELERATION
        {
            oldSpeed = currentSpeed;
            itsTheBeginning = false;
            beginning = System.nanoTime();
            currentSpeed = Math.abs(accel - brake);

            currentAcceleration = (currentSpeed - oldSpeed)/WAIT_TIME;

        }
        */

        telemetry.addData("Claw Position", clawVal);
        telemetry.addData("Text", "*** Robot Data ***");
        //telemetry.addData("left tgt pwr",  "left front pwr: " + String.format("%.2f", y1));
        //telemetry.addData("right tgt pwr", "right front pwr: " + String.format("%.2f", x1));
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
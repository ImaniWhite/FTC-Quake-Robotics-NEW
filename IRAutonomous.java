
package org.firstinspires.ftc.teamcode;
//package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Nathan Kapsin and Imani White on 12/13/2016.
 * At first at least mostly copied from a FTC powerpoint
 */

enum Step {
    SETUP, MOVE, CHECK, HONE_IN, BEACONPRESS, STOP
}

public class IRAutonomous extends LinearOpMode { //Just declaring this as OpMode until later

    Step currentState = Step.SETUP;

    DcMotor FrontRight;
    DcMotor FrontLeft;
    DcMotor BackRight;
    DcMotor BackLeft;

    ColorSensor colorSensor;

    double FRval;
    double FLval;
    double BRval;
    double BLval;

    boolean lineFound = false, lineSeen = true, beaconSeen = true; //Placeholder boolean for line checker & sensor
    boolean leftIsOurColor = true, rightIsOurColor = true;

    //Note to self: PROPERLY figure out what this does

    @Override
    public void runOpMode()
    {
        float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        // bPrevState and bCurrState represent the previous and current state of the button.
        //boolean bPrevState = false;
        //boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // get a reference to our ColorSensor object.
        colorSensor = hardwareMap.colorSensor.get("sensor_color");

        // turn the LED on in the beginning, just so user will know that the sensor is active.
        colorSensor.enableLed(bLedOn);

        waitForStart();
        sleep(5000);

        while (true) { //PUT IN ACTUAL THING

            switch (currentState) { /** Operates by looping through a switch statement on a enum variable. Can be adjusted in a lot of ways, but I'm working with this for right now*/
                case SETUP: /** This is just a setup, i.e., this effectively replaces init, but in the loop. I'm not sure how necessary it is.*/
                    //resetTime(); //Starts a timer

                    //Added by Nathan: Rotate a certain amount, variable by starting position and target. This will be used in order to get the robot to head towards one of the beacons
                    //This example turns the robot to the right for BLUE
                    FLval = 1; //Note: values are temporary
                    FRval = -1; //A timer will be needed here to decide how much time to turn before moving on to the next case
                    BLval = 1; //Other processes may be needed as well, for example, the robot may need to move forwards a little before turning, wait before moving, etc.
                    BRval = -1; //Alternatively, the robot could move forwards and then strafe/rotate in order to reach a beacon. May be more precise than turning, but a little harder

                    colorSensor.enableLed(bLedOn);

                    //if (timerIsDone)
                    sleep(500);
                    currentState = Step.MOVE; /**Each case activates the next for the next loop once it has completed its purpose*/

                    break;
                case MOVE: /**Starts the motors running. The way this program is set up, MOVE is only activated once to get the motors started*/
                    //this.DriveRobotTank(x,y) AKA Move robot certain amount when activated

                    //Added by Nathan: Start the robot moving forwards
                    FLval = 1; //Depending on how accurately the robot can use sensors compared to speed, this may be adjusted
                    FRval = 1; //Will have the robot moving in on the line leading to the beacon
                    BLval = 1; //In this case, I don't think we need a bounceback between CHECK and MOVE
                    BRval = 1;

                    currentState = Step.CHECK; /**Sets it to the next one*/
                    break;
                case CHECK: /**This case runs multiples times, waiting until the robot has moved for 1000 milliseconds before stopping*/
                    //if (this.getTime() > 1000)
                    //currentState = Step.STOP;

                    //Added by Nathan: Line checker
                    //I don't know how the robot is going to sense lines, so I'll make my best guess on how it would work

                    // convert the RGB values to HSV values.
                    Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues);

                    // send the info back to driver station using telemetry function.
                    telemetry.addData("LED", bLedOn ? "On" : "Off");
                    telemetry.addData("Clear", colorSensor.alpha());
                    telemetry.addData("Red  ", colorSensor.red());
                    telemetry.addData("Green", colorSensor.green());
                    telemetry.addData("Blue ", colorSensor.blue());
                    telemetry.addData("Hue", hsvValues[0]);

                    if (((colorSensor.red() + colorSensor.blue() + colorSensor.green()) / 3) >= 11)
                        lineSeen = true;

                    if (lineSeen)
                        currentState = Step.HONE_IN;

                    break;
                case HONE_IN:

                    if (((colorSensor.red() + colorSensor.blue() + colorSensor.green()) / 3) >= 11)
                        lineFound = true;
                    else
                        lineFound = false;
                    //CHECK FOR BEACON, CHECK = beaconSeen;

                    if (lineFound) //AKA if line underneath
                    {
                        FRval = 1;
                        FLval = 1;
                        BRval = 1;
                        BLval = 1;
                    }
                    else //Turn depending on team/direction. For Blue, aka right:
                    {
                        FRval = -1;
                        FLval = 1;
                        BRval = -1;
                        BLval = 1;
                    }

                    if (beaconSeen) //STOP once the beacon is seen
                    {
                        FRval = 0;
                        FLval = 0;
                        BRval = 0;
                        BLval = 0;

                        currentState = Step.BEACONPRESS;
                    }

                    break;
                case BEACONPRESS:
                    //Extra maneuvering necessary depending on where the arm is
                    //Scan for the color that is us on whatever side
                    if (leftIsOurColor) //Rather, leftColor == ourColor
                    {
                        //For a certain amount of time
                        FRval = 1;
                        FLval = -1;
                        BRval = -1;
                        BLval = 1;
                        sleep(1234); //Adjust

                        //Press the button however necessary. Presumably by going forwards a bit then backing up
                    }
                    else
                    {
                        //For a certain amount of time
                        FRval = -1;
                        FLval = 1;
                        BRval = 1;
                        BLval = -1;
                        sleep(1234); //Adjust

                        //Press the button however necessary. Presumably by going forwards a bit then backing up
                    }

                    currentState = Step.STOP;
                    break;
                case STOP:
                    //this.StopDrive();
                    //break;
                    //STOP
                    FRval = 0;
                    FLval = 0;
                    BRval = 0;
                    BLval = 0;

                    break;
                default: /**Not sure when the default would ever activate here; might as well have it for emergencies/errors*/
                    //currentState = Step.STOP;
                    break;
            }
            FrontLeft.setPower(FLval);
            FrontRight.setPower(FRval);
            BackLeft.setPower(BLval);
            BackRight.setPower(BRval);

        }

    }

}

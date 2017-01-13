
package org.firstinspires.ftc.teamcode;
//package com.qualcomm.ftcrobotcontroller.opmodes; This is what THEY have in their thing

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Nathan Kapsin on 12/13/2016.
 * At first at least mostly copied from a FTC powerpoint
 */

enum Step {
    SETUP, MOVE, CHECK, HONE_IN, BEACONPRESS, STOP
}

public class IRAutonomous extends LinearOpMode { //Just declaring this as OpMode until later

    State currentState = State.SETUP;

    DcMotor FrontRight;
    DcMotor FrontLeft;
    DcMotor BackRight;
    DcMotor BackLeft;

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

                    //if (timerIsDone)
                    currentState = State.MOVE; /**Each case activates the next for the next loop once it has completed its purpose*/

                    break;
                case MOVE: /**Starts the motors running. The way this program is set up, MOVE is only activated once to get the motors started*/
                    //this.DriveRobotTank(x,y) AKA Move robot certain amount when activated

                    //Added by Nathan: Start the robot moving forwards
                    FLval = 1; //Depending on how accurately the robot can use sensors compared to speed, this may be adjusted
                    FRval = 1; //Will have the robot moving in on the line leading to the beacon
                    BLval = 1; //In this case, I don't think we need a bounceback between CHECK and MOVE
                    BRval = 1;

                    currentState = State.CHECK; /**Sets it to the next one*/
                    break;
                case CHECK: /**This case runs multiples times, waiting until the robot has moved for 1000 milliseconds before stopping*/
                    //if (this.getTime() > 1000)
                    //currentState = State.STOP;

                    //Added by Nathan: Line checker
                    //I don't know how the robot is going to sense lines, so I'll make my best guess on how it would work

                    //CHECK FOR LINE, CHECK = lineSeen;

                    if (lineSeen)
                        currentState = State.HONE_IN;

                    break;
                case HONE_IN:

                    //CHECK FOR LINE, CHECK = lineFound;
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

                        currentState = State.BEACONPRESS;
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

                    currentState = State.STOP;
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
                    //currentState = State.STOP;
                    break;
            }
            FrontLeft.setPower(FLval);
            FrontRight.setPower(FRval);
            BackLeft.setPower(BLval);

        }

    }

}

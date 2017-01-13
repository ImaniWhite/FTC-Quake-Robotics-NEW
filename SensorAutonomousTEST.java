package org.firstinspires.ftc.teamcode;
//package com.qualcomm.ftcrobotcontroller.opmodes; This is what THEY have in their thing

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Nathan Kapsin on 12/13/2016.
 * At first at least mostly copied from a FTC powerpoint
 */

enum State {
    SETUP, MOVE, CHECK, HONE_IN, BEACONPRESS, STOP
}

public class SensorAutonomousTEST extends LinearOpMode { //Just declaring this as OpMode until later

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

    }

}

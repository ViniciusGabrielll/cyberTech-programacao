package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Robo (Blocks to Java)")
public class Robo extends LinearOpMode {

    private DcMotor roda_dir;
    private DcMotor roda_esq;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        roda_dir = hardwareMap.get(DcMotor.class, "roda_dir");
        roda_esq = hardwareMap.get(DcMotor.class, "roda_esq");

        waitForStart();
        // Put initialization blocks here.
        roda_dir.setDirection(DcMotor.Direction.REVERSE);
        roda_esq.setDirection(DcMotor.Direction.FORWARD);
        roda_dir.setTargetPosition(2500);
        roda_esq.setTargetPosition(2500);
        roda_dir.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        roda_esq.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ((DcMotorEx) roda_dir).setVelocity(-1000);
        ((DcMotorEx) roda_esq).setVelocity(-1000);
        sleep(2000);
        roda_dir.setTargetPosition(-180);
        roda_esq.setTargetPosition(-180);
        roda_dir.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        roda_esq.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ((DcMotorEx) roda_dir).setVelocity(1000);
        ((DcMotorEx) roda_esq).setVelocity(0);
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}
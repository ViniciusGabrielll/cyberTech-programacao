package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "autonomo3.0", group = "FTC")
public class Autonomo extends LinearOpMode {

    private DcMotor roda_esq = null;
    private DcMotor roda_dir = null;
    private DcMotor braco = null;
    private DcMotor garra = null;
    private CRServo giro_garra = null;
    private CRServo coletor = null;

    private ElapsedTime tempo = new ElapsedTime();

    private double VELO_NORMAL = 0.6;

    @Override
    public void runOpMode() {

        // Mapeamento dos motores e servos conforme nomes do Driver Station
        roda_esq = hardwareMap.get(DcMotor.class, "roda_esq");
        roda_dir = hardwareMap.get(DcMotor.class, "roda_dir");
        braco = hardwareMap.get(DcMotor.class, "braco");
        garra = hardwareMap.get(DcMotor.class, "garra");
        giro_garra = hardwareMap.get(CRServo.class, "giro_garra");
        coletor = hardwareMap.get(CRServo.class, "coletor");

        // Ajustar direção da roda esquerda se necessário
        roda_esq.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        tempo.reset();

        if (opModeIsActive()) {

            moverPorMetros(-0.3, 0.7);
            sleep(500);
            elevar(150);
            sleep(500);
            liberarColeta();
            coletar(-0.4, 500);



            pararMotores();

        }
    }

    private void moverPorMetros(double metros, double power) {
        roda_esq.setPower(-power);
        roda_dir.setPower(-power);
        int tempo = (int) ((metros * 4500 / (power * 10)));
        sleep(tempo);
        pararMotores();
        mover(-power, 500);
        sleep(1000);
    }

    //sleep((int) (metros * 450) para setPower de 0.6;

    private void mover(double power, long tempoMs) {
        power = power / 100;
        roda_esq.setPower(-power);
        roda_dir.setPower(-power);
        sleep(tempoMs);
        pararMotores();
        sleep(1000);
    }

    private void coletar(double power, long tempoMs) {
        coletor.setPower(power);
        sleep(tempoMs);
        coletor.setPower(0);
    }

    private void liberarColeta() {
        coletor.setPower(1.0);
        sleep(500);
        coletor.setPower(0);
    }

    private void elevar(long graus) {
        if(graus < 0) {
            braco.setPower(-0.6);
            sleep((100 * (graus * -1)));
        } else {
            braco.setPower(0.6);
            sleep( 100 * graus);
        }
        braco.setPower(0.01);
    }

    private void ajustarGiroGarra(double power, long tempoMs) {
        giro_garra.setPower(power);
        sleep(tempoMs);
        giro_garra.setPower(0);
    }

    private void acionarGarra(double power, long tempoMs) {
        garra.setPower(power);
        sleep(tempoMs);
        garra.setPower(0);
    }

    private void pararMotores() {
        roda_esq.setPower(0);
        roda_dir.setPower(0);
    }

    private void girar(double power, long tempoMs) {
        roda_esq.setPower(power);
        roda_dir.setPower(-power);
        sleep(tempoMs);
        roda_esq.setPower(-power);
        roda_dir.setPower(power);
        sleep(100);
        pararMotores();
    }

    private void girarPorGraus(double graus) {
        if (graus < 0) {
            roda_esq.setPower(VELO_NORMAL);
            roda_dir.setPower(-VELO_NORMAL);
            sleep((long) (-graus * 5));
        } else {
            roda_esq.setPower(-VELO_NORMAL);
            roda_dir.setPower(VELO_NORMAL);
            sleep((long) (graus * 5));
        }

        if (graus < 0) {
            roda_esq.setPower(-VELO_NORMAL);
            roda_dir.setPower(VELO_NORMAL);
            sleep((long) (-graus * 2.4)); // corrigido
        } else {
            roda_esq.setPower(VELO_NORMAL);
            roda_dir.setPower(-VELO_NORMAL);
            sleep((long) (graus * 2.4)); // corrigido
        }
        pararMotores();
    }
}
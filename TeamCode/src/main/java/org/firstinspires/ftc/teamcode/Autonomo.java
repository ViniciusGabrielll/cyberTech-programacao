@Autonomous(name = "Auto_MaxPontos_Open", group = "Competição")
public class Autonomo extends LinearOpMode {

    private DcMotor roda_esq = null;
    private DcMotor roda_dir = null;
    private Servo braco = null;
    private Servo garra = null;
    private ElapsedTime runtime = new ElapsedTime();

    // Constantes de controle
    static final double P_FRENTE = 0.55;
    static final double P_RE = 0.5;
    static final double P_GIRO = 0.5;

    static final double POS_BRACO_BAIXO = 0.1;
    static final double POS_BRACO_TRANSPORTE = 0.5;
    static final double POS_GARRA_FECHADA = 0.4;
    static final double POS_GARRA_ABERTA = 0.75;

    @Override
    public void runOpMode() {
        roda_esq = hardwareMap.get(DcMotor.class, "roda_esq");
        roda_dir = hardwareMap.get(DcMotor.class, "roda_dir");
        braco = hardwareMap.get(Servo.class, "braco");
        garra = hardwareMap.get(Servo.class, "garra");

        roda_esq.setDirection(DcMotorSimple.Direction.REVERSE);
        roda_dir.setDirection(DcMotorSimple.Direction.FORWARD);
        roda_esq.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        roda_dir.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        braco.setPosition(POS_BRACO_BAIXO);
        garra.setPosition(POS_GARRA_ABERTA);

        telemetry.addData("Status", "Pronto para iniciar - Estratégia Otimizada");
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {

            // FASE 1: Pontuar amostra pré-carregada
            garra.setPosition(POS_GARRA_FECHADA);
            sleep(400);
            braco.setPosition(POS_BRACO_TRANSPORTE);
            sleep(600);
            moverPorTempo(P_FRENTE, P_FRENTE, 2.0); // Ir até a rede
            garra.setPosition(POS_GARRA_ABERTA);
            sleep(300);

            // FASE 2: Voltar para coletar segunda amostra
            moverPorTempo(-P_RE, -P_RE, 1.2);
            moverPorTempo(-P_GIRO, P_GIRO, 0.9); // giro leve (~135°)

            braco.setPosition(POS_BRACO_BAIXO);
            sleep(300);
            garra.setPosition(POS_GARRA_FECHADA);
            sleep(500);
            braco.setPosition(POS_BRACO_TRANSPORTE);
            sleep(500);

            // FASE 3: Voltar à rede e soltar
            moverPorTempo(P_GIRO, -P_GIRO, 0.9); // girar de volta (~135°)
            moverPorTempo(P_FRENTE, P_FRENTE, 2.0);
            garra.setPosition(POS_GARRA_ABERTA);
            sleep(300);

            // FASE 4: Estacionar
            moverPorTempo(-P_RE, -P_RE, 1.0);
            moverPorTempo(P_GIRO, -P_GIRO, 0.9); // girar para zona
            moverPorTempo(P_FRENTE, P_FRENTE, 1.7); // entrar zona observação

            pararMotores();
            telemetry.addData("Status", "Pontuação Máxima Concluída");
            telemetry.update();
        }
    }

    private void moverPorTempo(double leftPower, double rightPower, double seconds) {
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < seconds) {
            roda_esq.setPower(leftPower);
            roda_dir.setPower(rightPower);
        }
        pararMotores();
        sleep(100);
    }

    private void pararMotores() {
        roda_esq.setPower(0);
        roda_dir.setPower(0);
    }
}

package sk.uniza.fri.chessboard;


import javax.swing.JOptionPane;

/**
 * 30. 4. 2022 - 18:13
 *
 * @author Matúš
 */
public class AnimationChessboard extends Chessboard {

    private long timer = 0;

    public AnimationChessboard(int chessboardSize) {
        super(chessboardSize);
    }

    /**
     * Metóda určená na animáciu a vizualizáciu prehľadávanie šachovnice.
     * Animuje každý krok. Keď nájde riešenie na 3 sekundy ho vysvieti.
     *
     * @param numberOfPlacedQueens Posúva ďalej informáciu o tom koľko už je položených královien.
     * @param field Posúva ďalej informácie o tom kde už sú dámy vo forme dvoj rozmerného poľa.
     */

    private void animateSolving(int numberOfPlacedQueens, ChessboardBox[][] field) {

        if (numberOfPlacedQueens == super.getChessboardSize()) {
            this.holdTime(3000);
        } else {
            for (ChessboardBox box: field[numberOfPlacedQueens]) {
                int coordinatesX = box.giveCoordinatesOfBox()[0];
                int coordinatesY = box.giveCoordinatesOfBox()[1];

                super.getTilesWithQueens()[coordinatesY][coordinatesX].showQueen();
                this.holdTime(this.timer);

                if (super.getInspector().isBoxUnderThreat(field, coordinatesX, coordinatesY)) {
                    super.getTilesWithQueens()[coordinatesY][coordinatesX].changeColor("green");
                    super.getTilesWithQueens()[coordinatesY][coordinatesX].showQueen();
                    this.holdTime(this.timer);

                    field[coordinatesX][coordinatesY].placeQueen();
                    this.animateSolving(numberOfPlacedQueens + 1, field);
                    field[coordinatesX][coordinatesY].removeQueen();
                }

                super.getTilesWithQueens()[coordinatesY][coordinatesX].changeColor("red");
                super.getTilesWithQueens()[coordinatesY][coordinatesX].showQueen();
                this.holdTime(this.timer);
                super.getTilesWithQueens()[coordinatesY][coordinatesX].changeColor(super.getTilesWithQueens()[coordinatesY][coordinatesX].getColor());
                super.getTilesWithQueens()[coordinatesY][coordinatesX].hideQueen();
            }
        }
    }

    /**
     * Primitívna metóda ktorá "zdrží čas" aby sa proces riešenia dal sledovať.
     *
     * @param delay Čas "zdržania" v milisekundách.
     */

    public void holdTime(long delay) {

        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            System.out.println("Cakanie sa nepodarilo");
        }
    }

    /**
     * Metóda kto nadvezuje na metodu holdTime().
     * Jej úlohou je zmeniť všeobecne dáný čas zdržania ktorý si zvolí používatel.
     *
     * @param newTimer Nový čas zdržania animácie.
     */

    public void changeTimer(int newTimer) {
        this.timer = newTimer;
    }

    /**
     * Zaobalovacia metóda metódy animateSoloving().
     * Jéj úlohou je zavolať metódu animateSolving() s konkretnými doplnenými parametrami.
     */

    public void doYourJob() {


        int speed = super.numberChecker("Zadaj požadovanú rýchlosť animácie v milisekundách.", "Zvolte rýchlosť.");

        JOptionPane.showMessageDialog(null, "Ak si želáte poustiť animáciu a ukončiť program stlačte ESC", "Informácia o ukončení programu.", JOptionPane.ERROR_MESSAGE);
        this.changeTimer(speed);

        this.animateSolving(0, super.getChessboardboxes());
        super.exit();
    }
}


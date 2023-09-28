package sk.uniza.fri.chessboard;

import sk.uniza.fri.filetool.BinTool;
import sk.uniza.fri.filetool.FileTool;
import sk.uniza.fri.filetool.ObjTool;
import sk.uniza.fri.filetool.TxtTool;

import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * 30. 4. 2022 - 18:13
 *
 * @author Matúš
 */
public class PrintOutChessboard extends Chessboard {

    private int counter = 0;
    private FileTool fileTool;

    public PrintOutChessboard(int chessboardSize, FileTool tool) {
        super(chessboardSize);
        this.fileTool = tool;
    }

    /**
     * Metóda ktorej úlohou je vymazať všetky texstové súbory po ukončení programu.
     */

    public void clearFolder() {
        this.fileTool.deleteFiles(this.counter);
        this.fileTool.deleteFolder();
    }

    /**
     * Metóda určená na vyhľadanie a zapísanie všetkých riešení.
     * Riešenia zapisuje do textových súborov.
     *
     * @param numberOfPlacedQueens Posúva ďalej informáciu o tom koľko už je položených královien.
     * @param field                Posúva ďalej informácie o tom kde už sú dámy vo forme dvoj rozmerného poľa.
     */

    private void findAllSolutions(int numberOfPlacedQueens, ChessboardBox[][] field) {
        if (numberOfPlacedQueens == super.getChessboardSize()) {
            this.counter++;
            this.fileTool.saveSolution(this.counter, field);
        } else {
            for (ChessboardBox box : field[numberOfPlacedQueens]) {
                int coordinatesX = box.giveCoordinatesOfBox()[0];
                int coordinatesY = box.giveCoordinatesOfBox()[1];

                if (super.getInspector().isBoxUnderThreat(field, coordinatesX, coordinatesY)) {
                    field[coordinatesX][coordinatesY].placeQueen();
                    this.findAllSolutions(numberOfPlacedQueens + 1, field);
                    field[coordinatesX][coordinatesY].removeQueen();
                }

            }
        }
    }

    /**
     * Zaobalovacia metóda metódy findAllSolutions().
     * Jéj úlohou je zavolať metódu findAllSolutions() s konkretnými doplnenými parametrami.
     */

    public void doYourJob() {
        if (this.getChessboardSize() == 2 || this.getChessboardSize() == 3) {

            JOptionPane.showMessageDialog(null, "Pre počet dám ktoré ste si zvolili nexistuje riešenie\n nakoľko sa nedajú umiestniť ani raz tak aby sa neohrozovali.", "Neexistujúce riešenie", JOptionPane.WARNING_MESSAGE);
            super.exit();

        } else {
            this.fileTool = this.chooseDataType(this.getChessboardSize());
            this.findAllSolutions(0, super.getChessboardboxes());
            int numberOfSolutions = this.getNumberOfSolutions();

            do {
                this.hideAllQueens();
                this.displayChosenSolution(this.pickSolution(numberOfSolutions, this.getChessboardSize()));
            } while (JOptionPane.showConfirmDialog(null, "Prajete si zobraziť iné riešenie?") == 0);
        }

        this.clearFolder();
        super.exit();

    }

    /**
     * Vykreslí vybrané riešenie ktoré si použivaťel zvolí.
     *
     * @param solutionNumber Číslo riešenia ktoré chce uživatel zobraziť.
     */

    public void displayChosenSolution(int solutionNumber) {


        ArrayList<ArrayList<Integer>> solution = this.fileTool.readSolution(solutionNumber);

        for (int i = 0; i < super.getChessboardSize(); i++) {
            for (int j = 0; j < super.getChessboardSize(); j++) {
                if (solution.get(j).get(i) == 1) {
                    super.getTilesWithQueens()[i][j].showQueen();
                }
            }
        }
    }


    /**
     * Skryje všetky vykreslené královny aby mohol vykresliť nové riešenie.
     */

    public void hideAllQueens() {
        for (int i = 0; i < super.getChessboardSize(); i++) {
            for (int j = 0; j < super.getChessboardSize(); j++) {
                super.getTilesWithQueens()[i][j].hideQueen();
            }
        }
    }

    public int getNumberOfSolutions() {
        return this.counter;
    }


    /**
     * Zaistí aby si uživateľ zvolil reálne dostupné riešenie.
     */

    private int pickSolution(int numberOfSolutions, int size) {
        int solutionNumber = super.numberChecker("Množstvo riešení pre " + size + " dám je " + numberOfSolutions + ".\n " +
                "Zvoľte čislo riešenia ktoré si želáte zobraziť. (od 1 po " + numberOfSolutions + " )", "Zvolte číslo riešenia.");

        if (solutionNumber > numberOfSolutions || solutionNumber == 0) {
            JOptionPane.showMessageDialog(null, "Také riešenie neexistuje", "Neplatné číslo", JOptionPane.WARNING_MESSAGE);
            solutionNumber = this.pickSolution(numberOfSolutions, size);
        }

        return solutionNumber;
    }

    /**
     * Dovolí použivateľovy zvoliť si dátový tip.
     */

    private FileTool chooseDataType(int size) {
        String[] options = {"binárne súbory", "textové súbory", "serializácia"};
        String mod = (String)JOptionPane.showInputDialog(null,
                "Prosím vyberte si dátovy režim v ktorom chcete uložiť riešenia.",
                "Vyberte si režim programu.", JOptionPane.QUESTION_MESSAGE,
                null, options, options[2]);

        try {
            if (mod.equals("binárne súbory")) {
                return new BinTool("", size);
            } else if (mod.equals("textové súbory")) {
                return new TxtTool("", size);
            } else {
                return new ObjTool("", size);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null,
                    "Je nutné vybrať si jeden z módov programu\n",
                    "Nesprávna voľba", JOptionPane.WARNING_MESSAGE);
            this.chooseDataType(size);
        }
        return null;
    }
}

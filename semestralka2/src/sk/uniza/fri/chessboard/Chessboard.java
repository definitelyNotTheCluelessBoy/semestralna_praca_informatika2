package sk.uniza.fri.chessboard;


import sk.uniza.fri.grafika.GraficChessboard;
import sk.uniza.fri.grafika.Manazer;
import sk.uniza.fri.grafika.TileWithQueen;

import javax.swing.JOptionPane;

/**
 * Hlavna plocha šachovnice kde sa odohráva hlavné prehľadávanie a výpočet 
 * všetkých riešení.
 * 
 * @author Matúš Kán 
 */

public abstract class Chessboard {

    private int chessboardSize;
    private ChessboardBox[][] chessboardboxes;
    private Inspector inspector;
    private GraficChessboard graficChessboard;
    private TileWithQueen[][] tilesWithQueens;
    
    /**
     * Inicializuje poľe a naplní ho políčkami ktoré sú prázdene (bez dámi).
     * Násletne si vytvorí inšpektora (nástroj na overenie bezpečnosti políčka)
     * nakoľko vytvárať ho lokálne vo funkcí by bolo neefektívne keďže by sa
     * vytvoril nanovo vždy keď sa funkcia zavolá rekurzívne.
     * 
     * @param chessboardSize Veľkosť šachovnice a počet dám.
     */
    
    public Chessboard(int chessboardSize) {
        this.chessboardSize = chessboardSize;
        this.chessboardboxes = new ChessboardBox[this.chessboardSize][this.chessboardSize];
        
        this.graficChessboard = new GraficChessboard(this.chessboardSize);
        this.tilesWithQueens = this.graficChessboard.getTilesWithQueens();
        
        for (int i = 0; i < chessboardSize; i++) {
            for (int j = 0; j < chessboardSize; j++) {
                this.chessboardboxes[i][j] = new ChessboardBox(i, j);
            }
        }
        
        this.inspector = new Inspector(this.chessboardSize);
        
        Manazer manager = new Manazer();
        manager.spravujObjekt(this);
    }

    /** 
     * Metóda ktorá slúži na opustenie programu.
     */
    
    public void exit() {
        JOptionPane.showMessageDialog(null, "Po stlačení OK sa program ukončí. Ďakujem za spoluprácu", "Ďakujem za spoluprácu", JOptionPane.WARNING_MESSAGE);
        System.exit(1);
    }

    /**
     * Skontroluje validitu vstupu.
     */

    protected int numberChecker(String mesage, String title) {
        int returnNumber;
        try {
            returnNumber = Integer.parseInt(JOptionPane.showInputDialog(null, mesage, title, JOptionPane.QUESTION_MESSAGE));
            if (returnNumber < 0) {
                JOptionPane.showMessageDialog(null, "Neplatné číslo", "Neplatné číslo", JOptionPane.WARNING_MESSAGE);
                returnNumber = this.numberChecker(mesage, title);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Je nutné zvoliť si číslo", "Neplatné číslo", JOptionPane.WARNING_MESSAGE);
            returnNumber = this.numberChecker(mesage, title);
        }

        return returnNumber;
    }

    public abstract void doYourJob();

    public int getChessboardSize() {
        return this.chessboardSize;
    }

    protected ChessboardBox[][] getChessboardboxes() {
        return this.chessboardboxes;
    }

    protected Inspector getInspector() {
        return this.inspector;
    }

    protected TileWithQueen[][] getTilesWithQueens() {
        return this.tilesWithQueens;
    }
}




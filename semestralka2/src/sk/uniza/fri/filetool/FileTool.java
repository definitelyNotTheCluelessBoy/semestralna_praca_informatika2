package sk.uniza.fri.filetool;

import sk.uniza.fri.chessboard.ChessboardBox;

import java.io.File;
import java.util.ArrayList;

/**
 * Nástroj na prácu so súbomy. Ich čítanie a zapisovanie do nich.
 * 
 * @author Matúš Kán
 */
public abstract class FileTool {
    
    private String absolutePathToFolder;
    private int chessboardSize;
    private String dataType;

    protected void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * Konštrukto inicializuje atribúty a vytvorý zložku kde uloží všetky riešenia.
     * 
     * @param path Je to absolútna cesta k zložke v ktorej sa vytvorí nová zložka s riešeniami.
     * @param chessboardSize Je veľkosť šachovnice. Slúží na to aby nástroj vedel v akom rozmedzí má zapisovať dáta.
     */
    
    public FileTool(String path, int chessboardSize) {
        this.absolutePathToFolder = path + "riesenia\\";
        this.chessboardSize = chessboardSize;
        
        File file = new File(this.absolutePathToFolder);
        file.mkdir();
    }
    
    /**
     * Metóda ktorá zmaže všetky riešenia a následne vymaže aj zložku kde boli uložené.
     * @param numberOfSolutions počet riešení ktoré ma vymazať.
     */
    
    public void deleteFiles(int numberOfSolutions) {
        try {
            for (int i = 1; i <= numberOfSolutions; i++) {
                File file = new File(this.absolutePathToFolder + "riešenie_číslo_" + i + this.dataType);
                file.delete();
            }
    
        }  catch (Exception e) {
            System.out.println("Nenasiel som subor.");
        }
    }
    
    /**
     * Metóda ktorá zmaže zložku kde boli uložené riešenia.
     */
    
    public void deleteFolder() {
        try {
            File file = new File(this.absolutePathToFolder);
            file.delete();
        }  catch (Exception e) {
            System.out.println("Nenasiel som subor.");
        }
    }

    /**
     * Metóda ktorá uloží všetky riešenia.
     * @param solutionNumber Poradové číslo riešenia.
     * @param solution Riešenie v forme dvojrozmerného poľa.
     */

    public abstract void saveSolution(int solutionNumber, ChessboardBox[][] solution);

    /**
     * Metóda ktorá vráti konkrétne požadované riešenie.
     * @param solutionNumber Poradové číslo riešenia.
     * @return Dvojrozmerný ArrayList ktorý slúží ako súradnicová mapa pre proces vykreslovania.
     */

    public abstract ArrayList<ArrayList<Integer>> readSolution(int solutionNumber);

    protected String getAbsolutePathToFolder() {
        return this.absolutePathToFolder;
    }

    protected int getChessboardSize() {
        return this.chessboardSize;
    }
}

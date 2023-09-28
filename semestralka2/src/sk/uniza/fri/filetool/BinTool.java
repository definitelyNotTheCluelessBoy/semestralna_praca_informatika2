package sk.uniza.fri.filetool;

import sk.uniza.fri.chessboard.ChessboardBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 30. 4. 2022 - 18:13
 *
 * @author Matúš
 */
public class BinTool extends FileTool {
    /**
     * Konštrukto inicializuje atribúty a vytvorý zložku kde uloží všetky riešenia.
     *
     * @param path           Je to absolútna cesta k zložke v ktorej sa vytvorí nová zložka s riešeniami.
     * @param chessboardSize Je veľkosť šachovnice. Slúží na to aby nástroj vedel v akom rozmedzí má zapisovať dáta.
     */
    public BinTool(String path, int chessboardSize) {
        super(path, chessboardSize);
        super.setDataType(".dat");
    }

    @Override
    public void saveSolution(int solutionNumber, ChessboardBox[][] solution) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(super.getAbsolutePathToFolder() + "riešenie_číslo_" + solutionNumber + ".dat"));
            for (int i = 0; i < super.getChessboardSize(); i++) {
                for (int j = 0; j < super.getChessboardSize(); j++) {
                    if (solution[i][j].hasQueen()) {
                        dataOutputStream.writeInt(1);
                    }  else {
                        dataOutputStream.writeInt(0);
                    }
                }
            }
            dataOutputStream.close();
        }  catch (FileNotFoundException e) {
            System.out.println("Nenasiel som subor.");
        } catch (IOException e) {
            System.out.println("Chyba pri zapise.");
        }
    }

    @Override
    public ArrayList<ArrayList<Integer>> readSolution(int solutionNumber) {
        ArrayList<ArrayList<Integer>> wantedSolution = new ArrayList<>();

        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(super.getAbsolutePathToFolder() + "riešenie_číslo_" + solutionNumber + ".dat"));
            for (int i = 0; i < super.getChessboardSize(); i++) {
                ArrayList<Integer> lineForWantedSolution = new ArrayList<>();

                for (int j = 0; j < super.getChessboardSize(); j++) {
                    lineForWantedSolution.add(dataInputStream.readInt());
                }
                wantedSolution.add(lineForWantedSolution);
            }
            dataInputStream.close();
        }  catch (FileNotFoundException e) {
            System.out.println("Nenasiel som subor.");
        } catch (IOException e) {
            System.out.println("Chyba pri nacitani");
        }
        return wantedSolution;
    }
}

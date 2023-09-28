package sk.uniza.fri.filetool;

import sk.uniza.fri.chessboard.ChessboardBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 30. 4. 2022 - 18:13
 *
 * @author Matúš
 */
public class ObjTool extends FileTool {
    /**
     * Konštrukto inicializuje atribúty a vytvorý zložku kde uloží všetky riešenia.
     *
     * @param path           Je to absolútna cesta k zložke v ktorej sa vytvorí nová zložka s riešeniami.
     * @param chessboardSize Je veľkosť šachovnice. Slúží na to aby nástroj vedel v akom rozmedzí má zapisovať dáta.
     */
    public ObjTool(String path, int chessboardSize) {
        super(path, chessboardSize);
        super.setDataType(".dat");
    }

    @Override
    public void saveSolution(int solutionNumber, ChessboardBox[][] solution) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(super.getAbsolutePathToFolder() + "riešenie_číslo_" + solutionNumber + ".dat"));
            for (ChessboardBox[] chessboardBoxes : solution) {
                for (ChessboardBox box : chessboardBoxes) {
                    objectOutputStream.writeObject(box);
                }
            }
            objectOutputStream.close();
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
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(super.getAbsolutePathToFolder() + "riešenie_číslo_" + solutionNumber + ".dat"));
            for (int i = 0; i < super.getChessboardSize(); i++) {
                ArrayList<Integer> lineForWantedSolution = new ArrayList<>();

                for (int j = 0; j < super.getChessboardSize(); j++) {
                    Object object = objectInputStream.readObject();
                    if (object instanceof ChessboardBox) {
                        if (((ChessboardBox)object).hasQueen()) {
                            lineForWantedSolution.add(1);
                        } else {
                            lineForWantedSolution.add(0);
                        }
                    }
                }
                wantedSolution.add(lineForWantedSolution);
            }
            objectInputStream.close();
        }  catch (FileNotFoundException e) {
            System.out.println("Nenasiel som subor.");
        } catch (IOException e) {
            System.out.println("Chyba pri nacitani");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return wantedSolution;
    }
}

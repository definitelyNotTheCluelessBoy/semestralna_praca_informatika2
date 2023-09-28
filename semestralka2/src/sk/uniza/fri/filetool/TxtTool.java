package sk.uniza.fri.filetool;

import sk.uniza.fri.chessboard.ChessboardBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 30. 4. 2022 - 18:13
 *
 * @author Matúš
 */
public class TxtTool extends FileTool {

    public TxtTool(String path, int chessboardSize) {
        super(path, chessboardSize);
        super.setDataType(".txt");
    }

    @Override
    public void saveSolution(int solutionNumber, ChessboardBox[][] solution) {
        try {
            PrintWriter printWriter = new PrintWriter(super.getAbsolutePathToFolder() + "riešenie_číslo_" + solutionNumber + ".txt");
            for (int i = 0; i < super.getChessboardSize(); i++) {
                for (int j = 0; j < super.getChessboardSize(); j++) {
                    if (solution[i][j].hasQueen()) {
                        printWriter.print(1 + "  ");
                    } else {
                        printWriter.print(0 + "  ");
                    }
                }
                printWriter.println("");
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nenasiel som subor.");
        }
    }


    @Override
    public ArrayList<ArrayList<Integer>> readSolution(int solutionNumber) {
        ArrayList<ArrayList<Integer>> wantedSolution = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(super.getAbsolutePathToFolder() + "riešenie_číslo_" + solutionNumber + ".txt"));
            while (scanner.hasNextLine()) {
                ArrayList<Integer> lineForWantedSolution = new ArrayList<>();
                String[] line = scanner.nextLine().split(" ");
                for (String i : line) {

                    if (i.equals("0")) {
                        lineForWantedSolution.add(0);
                    } else if (i.equals("1")) {
                        lineForWantedSolution.add(1);
                    }
                }
                wantedSolution.add(lineForWantedSolution);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nenasiel som subor.");
        }
        return wantedSolution;
    }
}


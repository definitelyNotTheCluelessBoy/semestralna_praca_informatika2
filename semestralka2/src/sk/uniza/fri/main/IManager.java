package sk.uniza.fri.main;

import sk.uniza.fri.chessboard.Chessboard;

/**
 * 30. 4. 2022 - 18:13
 *
 * @author Matúš
 */

public interface IManager {
    int numberChecker(String mesage, String title);
    Chessboard chooseMode();
}

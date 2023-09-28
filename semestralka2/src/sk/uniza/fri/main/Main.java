package sk.uniza.fri.main;

import sk.uniza.fri.chessboard.AnimationChessboard;
import sk.uniza.fri.chessboard.Chessboard;
import sk.uniza.fri.chessboard.PrintOutChessboard;

import javax.swing.JOptionPane;

/**
 * Spúšťacia trieda pre spúšťaciu metódu main().
 *
 * @author Matúš Kán
 */

public class Main {

    /**
     * Hlavná metóda ktorá riadí chod programu.
     */

    public static void main(String[] args) {

        IManager manager = new IManager() {
            /**
             * Metóda ktorá sa stará o to aby si uživaťel vybral vhodné číslo.
             */
            @Override
            public int numberChecker(String mesage, String title) {
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

            /**
             * Metóda ktorá sa stará o to aby si uživaťel vybral vhodný režim.
             */
            @Override
            public Chessboard chooseMode() {

                String[] options = {"animuj", "vykresli"};
                String mod = (String)JOptionPane.showInputDialog(null,
                        "Ak si želáte animovať proces vyhľadávania zadajde \"animuj\".\n "
                                + "Ak si želáte vykresliť konkrétné hladané riešenie zadajte \"vykresli\".",
                        "Vyberte si režim programu.", JOptionPane.QUESTION_MESSAGE,
                        null, options, options[1]);


                try {

                    if (mod.equals("animuj")) {
                        int size = this.numberChecker("Zadaj požadovaný počet dám.", "Zvolte velkosť");
                        return new AnimationChessboard(size);
                    } else {
                        int size = this.numberChecker("Zadaj požadovaný počet dám.", "Zvolte velkosť");
                        return new PrintOutChessboard(size, null);
                    }
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null,
                            "Je nutné vybrať si jeden z módov programu\n",
                            "Nesprávna voľba", JOptionPane.WARNING_MESSAGE);
                    return this.chooseMode();
                }


            }
        };

        Chessboard chessboard = manager.chooseMode();
        chessboard.doYourJob();
    }
}




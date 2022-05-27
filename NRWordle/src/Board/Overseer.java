package Board;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Overseer {
    private Layout _lay;
    private String _theTheme;
    private int col;
    public boolean win = false;
    private int rowCnt = 0;
    public int totalGuessCnt;
    public int gameCnt;

    //initializes wordle program without user history
    public Overseer (){
        introduction1();
        setTheme();
        _lay = new Layout (_theTheme);
        gameCnt = _lay.gameCnt;
        col = _lay.col;
        while (!win && rowCnt < 6){
            update();
        }

        lose();
        try {
            String s = "";
            FileWriter myWriter = new FileWriter("gameCount.txt");
            myWriter.write(gameCnt + "");
            myWriter.close();
            Scanner input = new Scanner("gameCount.txt");
            while (input.hasNextLine())
            {
                System.out.println(input.nextLine());
            }
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    //asks for another guess unless they have exceeded the limit of attempts
    public void update() {
            String askInput = JOptionPane.showInputDialog(_lay.frame, "Guess the 5 letter word: ");
            while (askInput.length() != 5) {
                askInput = JOptionPane.showInputDialog( _lay.frame, "Too long/short. Guess the 5 letter word: ");
            }
            if (askInput.length() == 5 ) {
                    checkGuess(askInput);
            }

    }

    //sets the theme of the wordle to animal, food, or country based on user input
    public void setTheme (){
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        Object[] options = {"animal", "food", "country"};
        Object selectionObject = JOptionPane.showInputDialog(frame, "What kind of words do you want?", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        _theTheme = selectionObject.toString();
    }

    //compares user input to correct answer then increments counters accordingly
    public void checkGuess(String guess) {
        boolean check = true;
        String[] _guess = _lay.inputToArr(guess);
        String[] _actual = _lay.inputToArr(_lay.word);
        for (int c = 0; c < col; c++){
            if (_guess[c].equalsIgnoreCase(_actual[c])){
                _lay.setCorrectGuess(rowCnt, c, _guess[c]);
            }
            else if (_lay.word.indexOf(guess.substring(c, c+1)) != -1){
                _lay.setWrongLocGuess(rowCnt, c, _guess[c]);
                check = false;
            }
            else {
                _lay.setWrongGuess(rowCnt, c, _guess[c]);
                check = false;
            }
            totalGuessCnt++;
        }
        if (check){
            win = true;
        }
        if (win){
            victory();
        }
        rowCnt++;
    }

    //displays lose message to user
    public void lose (){
        JPanel panel = new JPanel();
        JLabel c = new JLabel("You lost! The correct word was: " + _lay.word);
        panel.add(c);
        panel.setForeground(new Color(67, 124, 144));
        panel.setBackground(new Color(238, 235, 211));
        JOptionPane.showMessageDialog(null, panel, "Lose", JOptionPane.INFORMATION_MESSAGE);
        Overseer o = new Overseer();
    }

    //displays win message to user
    public void victory() {
        JPanel panel = new JPanel();
        JLabel c = new JLabel("Congratulations on guessing the word! You are a Wordle genius.");
        JLabel d = new JLabel("Number of character guesses this game: "+ totalGuessCnt+ ".");
        JLabel e = new JLabel("Number of word guesses: "+ totalGuessCnt/5+ ".");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(c);
        panel.add(d);
        panel.add(e);
        panel.setForeground(new Color(67, 124, 144));
        panel.setBackground(new Color(238, 235, 211));
        JOptionPane.showMessageDialog(null, panel, "Your statistics", JOptionPane.INFORMATION_MESSAGE);

        Overseer o = new Overseer();
    }

    //displays introduction
    public static void introduction1() {
        JPanel panel = new JPanel();
        JLabel a = new JLabel("Welcome to Basically Wordle!.");
        JLabel b = new JLabel("If you guessed a letter correctly, the box will be green.");
        JLabel e = new JLabel("If you guessed a letter correctly but in the wrong spot, the box will be yellow.");
        JLabel f = new JLabel("If you guessed a letter incorrectly, the box will be red.");
        JLabel c = new JLabel("You will choose a theme of words to guess from.");
        JLabel d = new JLabel("You have six chances to guess the word correctly.");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(a);
        panel.add(b);
        panel.add(e);
        panel.add(f);
        panel.add(c);
        panel.add(d);
        panel.setBackground(new Color(238, 235, 211));
        JOptionPane.showMessageDialog(null, panel, "Basically Wordle", JOptionPane.INFORMATION_MESSAGE);
    }

}

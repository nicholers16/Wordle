package Board;

import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Layout //  implements DocumentListener
{
    public int row = 6;
    public int col = 5;
    public Words w = new Words();
    public String word;
    public int[][] answers = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
    public JButton[][] button;
    public JButton getButton(int r, int c) {return button[r][c];}
    public int gameCnt = 1;
    JPanel panel;
    JFrame frame;

    //FOR ANSWERS ARRAY
    //0 == no input
    //1 == input is in correct location
    //2 == input is not in correct location but is in the word
    //3 == input is not in correct location or in the word

    //generates wordle text board
    public Layout(String _theme) {
        word = wordGenerated(_theme);
        button = new JButton[row][col];
        panel = new JPanel(new GridLayout(row, col));
        frame = new JFrame();
        frame.setLayout(new GridLayout());
        panel.setSize(600, 600);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                button[i][j] = new JButton();
                setUnguessed(i,j);
                panel.add(button[i][j]);
            }
        }

        frame.setTitle("Basically Wordle. Number of games played: "+ gameCnt);
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
    }

    //helper method that changes square to the visual value of an unguessed square
    public void setUnguessed(int r, int c) {
        button[r][c].setOpaque(true);
        button[r][c].setBorderPainted(false);
        button[r][c].setFont(new Font("TimesRoman", Font.PLAIN, 20));
        button[r][c].setBackground(Color.gray);
        answers[r][c] = 0;
    }

    //helper method that changes square to the visual value of a correct guessed
    public void setCorrectGuess(int r, int c, String s) {
        button[r][c].setOpaque(true);
        button[r][c].setBorderPainted(false);
        button[r][c].setFont(new Font("TimesRoman", Font.PLAIN, 20));
        button[r][c].setBackground(new Color (161, 241, 87));
        button[r][c].setText(s);
        answers[r][c] = 1;
    }

    //helper method that changes square to the visual value of a guess that is the correct
    //character but in the wrong location
    public void setWrongLocGuess(int r, int c, String s) {
        button[r][c].setOpaque(true);
        button[r][c].setBorderPainted(false);
        button[r][c].setFont(new Font("TimesRoman", Font.PLAIN, 20));
        button[r][c].setBackground(new Color (240,230, 91));
        button[r][c].setText(s);
        answers[r][c] = 2;
    }

    //helper method that changes square to the visual value of an incorrect guess
    public void setWrongGuess(int r, int c, String s) {
        button[r][c].setOpaque(true);
        button[r][c].setBorderPainted(false);
        button[r][c].setFont(new Font("TimesRoman", Font.PLAIN, 20));
        button[r][c].setBackground(new Color (234, 117, 98));
        button[r][c].setText(s);
        answers[r][c] = 3;
    }
    //randomly picks a word from one of the arrays based on the input for theme
    public String wordGenerated(String theme) {
        if (theme.equalsIgnoreCase("animal")) {
            int rnd = new Random().nextInt(w.animal.length);
            return w.animal[rnd];
        } else if (theme.equalsIgnoreCase("food")) {
            int rnd = new Random().nextInt(w.food.length);
            return w.food[rnd];
        }
        int rnd = new Random().nextInt(w.country.length);
        return w.country[rnd];

    }

    //converts user String input into an array of single character String
    public String[] inputToArr(String guess) {
        String[] arr = new String[5];
        if (guess.length() == 5) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = guess.substring(i, i + 1);
            }
        }
        return arr;
    }



}

// Main class for Wordle
// Author: Rishabh Purwar
// Date: 07/30/24


import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String gameMode = selectMode();
        
        Wordle newGame = new Wordle(gameMode);
        newGame.startGame();
    }

    

    // user will select the difficulty here and it will be passed on when game is initialized
    private static String selectMode() {
        Scanner in = new Scanner(System.in);
        boolean validMode = false;
        String modeInput = "";
        System.out.println("Welcome to Wordle!\nSelect difficulty: easy, medium, or hard?");

        while (!validMode) {
            modeInput = in.nextLine().toLowerCase().trim();
            if (modeInput.equals("easy") || modeInput.equals("medium") || modeInput.equals("hard")) {
                validMode = true;
            } else {
                System.out.println("Difficulty can only be easy, medium or hard.\nTry again.");
            }
        }
        return modeInput;
    }
}
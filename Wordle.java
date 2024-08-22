// Wordle Game class
// Author: Rishabh Purwar
// Date: 07/30/24

import java.util.Scanner;
import java.util.Random;

class Wordle {
    private Scanner in = new Scanner(System.in);
    
    private static boolean isGameRunning = true;
    private static int guessCount = 0;
    private static int guessChances = 6;
    
    private static String wordleWord;
    private static Guess[] guessArr = new Guess[guessChances];
    
    private static String[] words4 = {"tree", "peak", "exit", "game", "beam", "nine", "ears", "long", "jump"};
    private static String[] words5 = {"hello", "apple", "magic", "panic", "ratio", "order", "smart", "bland", "chugs"};
    private static String[] words6 = {"tables", "attack", "danger", "corner", "become", "nachos", "vacuum", "yachts", "knight"};


    // wordle initalizer
    public Wordle(String gameMode) {
        // initializes every object in the guess array to null
        for (int guessIndex = 0; guessIndex < guessArr.length; guessIndex++) {
            guessArr[guessIndex] = new Guess(null);
        }
        this.wordleWord = chooseWordleWord(gameMode);
    }

    
    public void startGame() {
        // main wordle game loop
        while (isGameRunning) {
            boolean validGuess = false;
            String guessInput = null;

            while (!validGuess) {
                printScreen();

                System.out.printf("Guess a %d letter word:\n", wordleWord.length());
                guessInput = in.nextLine().trim().toLowerCase();
                validGuess = validateGuess(guessInput);
            }

            guessArr[guessCount].setGuess(guessInput);
            guessCount++;

            // checks if user guesses the word and has won
            if (guessInput.toLowerCase().equals(wordleWord)) {
                isGameRunning = false;
                System.out.printf("You won the game! Took you %d guesses to get %S", guessCount, wordleWord);
            }

            // checks if user has used max guesses and lost
            if (guessCount == guessChances) {
                // sets game running boolean to false to end loop
                isGameRunning = false;
                System.out.printf("You ran out of guesses!\nThe word was %S.", wordleWord);
            }
        }
        // prints everything a final time if the game has ended
        if (!isGameRunning) {
            printScreen();
        }
    }


    // method to randomly select the word based on the users input of difficulty
    private static String chooseWordleWord(String gameModeInput) {
        String gameWord;
        Random rand = new Random();

        switch(gameModeInput) {
            case "easy":
                gameWord = words4[rand.nextInt(words4.length)];
                break;
            case "medium":
                gameWord = words5[rand.nextInt(words5.length)];
                break;
            case "hard":
                gameWord = words6[rand.nextInt(words6.length)];
                break;
            default:
                gameWord = words5[rand.nextInt(words5.length)];
                break;
        }
        return gameWord;
    }


    // method that makes sure that the input of the guess is fully valid
    private static boolean validateGuess(String guessInput) {
        String testInput = guessInput.toUpperCase();
        // first checks for correct length
        if (testInput.length() == wordleWord.length()) {
            // next checks for all valid alphabet characters
            // runs a for loop through all characters and checks unicode

            boolean foundNonLetter = false;
            for (int letterIndex = 0; letterIndex < testInput.length(); letterIndex++) {
                if (guessInput.charAt(letterIndex) < 65 || testInput.charAt(letterIndex) > 90) {
                    foundNonLetter = true;
                }
            }
            if (!foundNonLetter) {
                return true;
            } else {
                System.out.println("Word must be valid letters.");
                return false;
            }
        } else {
            System.out.printf("Word must be %d characters.\n", wordleWord.length());
            return false;
        }
    }


    // method to print the screen and show all guesses and letters
    private static void printScreen() {

        System.out.println("\n");

        // prints the guesses on each line
        for (int guessIndex = 0; guessIndex < guessArr.length; guessIndex++) {
            if (guessArr[guessIndex] != null && guessArr[guessIndex].toString() != null) {
                System.out.println(guessArr[guessIndex].toFormattedString());
                System.out.println(findColorSquares(guessArr[guessIndex].toString()) + "\n");
            } else {
                // prints blank lines for spots without a guess
                for (int lineCount = 0; lineCount < wordleWord.length(); lineCount++) {
                    System.out.print("_ ");
                }
                System.out.println("\n");
            }
        }
    }


    
    // method to process the word guess and determine the square colors corresponding to each letter
    private static String findColorSquares(String guess) {

        // makes array counting occurences of each letter in the full word with nested for loops
        // does this in order to correctly display number of yellows
        int[] charCounts = new int[wordleWord.length()];
        for (int charIndex = 0; charIndex < wordleWord.length(); charIndex++) {
            int charCounter = 0;
            for (int countIndex = 0; countIndex < wordleWord.length(); countIndex++) {
                if (wordleWord.charAt(charIndex) == wordleWord.charAt(countIndex)) {
                    charCounter += 1;
                }
            }
            charCounts[charIndex] = charCounter;
        }

        // builds them in array in order to correctly grade the guess
        String[] squaresArray = new String[wordleWord.length()];
        for (int squareIndex = 0; squareIndex < wordleWord.length(); squareIndex++) {
            squaresArray[squareIndex] = "⬛";
        }

        // first just simply checks for greens
        for (int guessIndex = 0; guessIndex < guess.length(); guessIndex++) {
            if (wordleWord.charAt(guessIndex) == guess.charAt(guessIndex)) {
                squaresArray[guessIndex] = "🟩";
                charCounts[guessIndex]--;
            }
        }

        // next checks for yellows
        for (int guessIndex = 0; guessIndex < guess.length(); guessIndex++) {
            for (int wordIndex = 0; wordIndex < guess.length(); wordIndex++) {
                if ((squaresArray[guessIndex] != "🟩") && (guess.charAt(guessIndex) == wordleWord.charAt(wordIndex)) && charCounts[wordIndex] > 0) {
                    squaresArray[guessIndex] = "🟨";

                    // if there is multiple of the letter it will lower it everyhwere in the array
                    for (int counterIndex = 0; counterIndex < wordleWord.length(); counterIndex++) {
                        if (wordleWord.charAt(wordIndex) == wordleWord.charAt(counterIndex)) {
                            charCounts[counterIndex] -= 1;
                        }
                    }
                }
            }
        }

        // merges the final array into a string
        String squaresOutput = "";
        for (int squareIndex = 0; squareIndex < wordleWord.length(); squareIndex++) {
            squaresOutput += squaresArray[squareIndex];
        }
        return squaresOutput;
    }
}
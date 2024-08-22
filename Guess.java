// Guess class for Wordle
// Author: Rishabh Purwar
// Date: 07/30/24

// Each object of this class is one of the users guesses

class Guess {
    private String guess;

    // initializer
    public Guess (String guess) {
        this.guess = guess;
    }

    // guess setter
    public void setGuess (String newGuess) {
        this.guess = newGuess;
    }

    // regular getter without formatting
    public String toString() {
        return guess;
    }
    
    // guess getter but formatted when the screen is printed
    public String toFormattedString() {
        // formats it in all caps with a space between each letter
        String output = "";
        for (int guessIndex = 0; guessIndex < guess.length(); guessIndex++) {
            output += guess.charAt(guessIndex) + " ";
        }
        output = output.toUpperCase();
        return output;
    }
}
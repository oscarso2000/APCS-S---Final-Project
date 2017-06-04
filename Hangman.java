    import java.awt.*;
    import java.io.*;
    import java.util.*;
    import java.util.List;
    import javax.swing.*;

public class Hangman extends HangmanGUI{
    public static String[] wordList = {"apple","pear"};
    public static String secretWord;
    public static Set<Character>lettersGuessed;
    public static Set<Character>alphabet;
    public static boolean[] lettersRevealed;
    public static int guessesRemaining;
    
    public static boolean checkIfWon(){
        for (boolean isLetterShown:lettersRevealed){
            if(!isLetterShown){
                return false;
            }
        }
        return true;
    }
    public static boolean checkUserGuess (String str){
        if(str.length() == 1 && lettersGuessed.contains(str.charAt(0)) == false
            && alphabet.contains(str.charAt(0))){
                HangmanGUI.setText(null);
                lettersGuessed.add(str.charAt(0));
                return true;
            }else{
               // Toolkit.getDefaultToolkit.beep();
            }
            return false;
    }
    
    private static String chooseWord(String[] wordList){
        return wordList[(int)(Math.random()*wordList.length)];
    }
    private static void createAlphabetSet(){
            alphabet = new HashSet<Character>(26);
            for(Character c = 'a'; c<='z'; c++){
                alphabet.add(c);
            }
    }
    private static void playAgain(String message){
        int ans = HangmanGUI.playAgainDialog(message);
        if(ans == JOptionPane.YES_OPTION){
            setUpGame();
        }else{
            System.exit(0);
        } 
    }
    public static void loseSequence(){
        for(int i = 0; i<lettersRevealed.length;i++){
            lettersRevealed[i] = true;
        }
        HangmanGUI.drawSecretWord();
        playAgain("Better Luck Next Time. The word was " + secretWord + "\n Play Again?");
    }
    public static void winSequence(){
        playAgain("Well done! You guessed " +secretWord + "\n Play Again?");
    }
    private static void setUpGame(){
        guessesRemaining = 6;
        secretWord = chooseWord(wordList);
        lettersRevealed = new boolean[secretWord.length()];
        Arrays.fill(lettersRevealed,false);
        lettersGuessed = new HashSet<Character>(26);
        HangmanGUI.drawSecretWord();
        HangmanGUI.drawLettersGuessed();
        HangmanGUI.drawGuessesRemaining();
    }

    public static void updateSecretWord(String str){
        List<Integer>changeBoolean = new ArrayList<Integer>();        
        if(secretWord.contains(str)){
            for(int i=0;i<secretWord.length();i++){
                if(secretWord.charAt(i) == str.charAt(0)){
                    changeBoolean.add(i);
                }
            }
            for(Integer index : changeBoolean){
                lettersRevealed[index] = true;
            }
        }else{
            guessesRemaining--;
            HangmanGUI.drawGuessesRemaining();
        }
    }
    
    public static void play (){
        Hangman hangman = new Hangman();
        hangman.createAlphabetSet();
        HangmanGUI.buildGUI();
        setUpGame();
    }
}
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.EventQueue;
    
public class HangmanGUI{
    //initial code that failed
    /*private Display display;
    private ArrayList<JButton> alphabetButtonsList = new ArrayList<JButton>();
    private JButton nextGameButton;
    private JButton giveUpButton;
    
    private String displayMessage;
    private String[] wordList = {"Banana","Apple"};
    private String unknownWord;
    private String guesses; //contains letters which user has guessed
    private boolean gameOver;
    private int badGuesses; //all incorrect guesses (Number of times)
    
    public HangmanGUI(){ //all buttons, displays, etc created here
        ButtonHandler buttonHandler = new ButtonHandler();
        display = new Display();
        JPanel bottomPanel = new JPanel();
        setLayout(new BorderLayout(3,3));
        add(display,BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        nextGameButton = new JButton("Next Word");
        giveUpButton = new JButton("Give Up");
        JButton quitButton = new JButton("Quit");
        
        nextGameButton.addActionListener(buttonHandler);
        giveUpButton.addActionListener(buttonHandler);
        quitButton.addActionListener(buttonHandler);
        
        bottomPanel.add(nextGameButton);
        bottomPanel.add(giveUpButton);
        bottomPanel.add(quitButton);
        
        startGame();
    }
    
    private class Display extends JPanel{
        Display(){ 
            setPreferredSize(new Dimension (500,500)); //same as snake
            setBackground(new Color(250,230,180)); //change to adjust color
            setFont(new Font("Arial",Font.BOLD,20));
        }
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            ((Graphics2D)g).setStroke(new BasicStroke(3));
            if (displayMessage!=null){
                g.setColor(Color.YELLOW); //color of string
                g.drawString(displayMessage,30,40); //alter to fit screen
            }
        }
    }
    
    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JButton clickedButton = (JButton)event.getSource();
            String command = event.getActionCommand();
            if(command.equals("Quit")){
                System.exit(0);
            }else{
                displayMessage = "The command is " + command;
            }
            display.repaint(); //redraw to show message
        }
    }
    
    private void startGame(){
        gameOver = false;
        guesses = "";
        badGuesses = 0;
        nextGameButton.setEnabled(false); //only true when word is finished
        giveUpButton.setEnabled(true);
        
        for(int i=0;i<alphabetButtonsList.size();i++){
            alphabetButtonsList.get(i).setEnabled(true);
        }
        
        int index = (int)(Math.random()*wordList.length); //generates index of word array
        unknownWord = wordList[index];
        unknownWord = unknownWord.toUpperCase();
        displayMessage = "The word has" + unknownWord.length() + "letters.";
    }
    
    private boolean wordIsComplete(){
        for(int i=0; i<unknownWord.length();i++){
            char c = unknownWord.charAt(i);
            if (guesses.indexOf(c) == -1){
                return false;
            }
        }
        return true;
    }
   
     
    public static void main(String[] args) {
        JFrame window = new JFrame("Hangman"); // The window, with "Hangman" in the title bar.
        HangmanGUI panel = new HangmanGUI();  // The main panel for the window.
        //window.setContentPane(panel);   // Set the main panel to be the content of the window
        window.pack();  // Set the size of the window based on the preferred sizes of what it contains.
        window.setResizable(false);  // Don't let the user resize the window.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // End the program if the user closes the window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();  // The width/height of the screen.
        window.setLocation( (screen.width - window.getWidth())/2, 
                (screen.height - window.getHeight())/2 );  // Position window in the center of screen.
        window.setVisible(true);  // Make the window visible on the screen.
    }*/
    
    private static JFrame frame;
    private static JTextField textField;
    private static JLabel guessesRemainingLabel;
    private static JLabel lettersGuessedLabel;
    private static JLabel unknownWordLabel;
    //very weird how everything has to be static
    //not finished still lots of bugs
    //win situation works; does not display guessed letters; does not let me guess more than one letter
    //lose situation works, but guess box is too big, need to resize, and hopefully draw graphics or add PNGs of hangman
    public static void buildGUI(){
        EventQueue.invokeLater(new Runnable(){
                public void run(){
                    frame = new JFrame ("Hangman by Oscar So");
                    guessesRemainingLabel = new JLabel ("Guesses remaining: " + String.valueOf(Hangman.guessesRemaining));
                    unknownWordLabel = new JLabel();
                    lettersGuessedLabel = new JLabel("Already Guessed: ");
                    JButton guessButton = new JButton("Guess");
                    GuessListener guessListener = new GuessListener();
                    textField = new JTextField();
                    guessButton.addActionListener(guessListener);
                    textField.addActionListener(guessListener);
                    JPanel userPanel = new JPanel(new BorderLayout());
                    userPanel.add(BorderLayout.CENTER,textField);
                    userPanel.add(BorderLayout.EAST,guessButton);
                    JPanel labels = new JPanel();
                    labels.setLayout(new BoxLayout(labels, BoxLayout.PAGE_AXIS));
                    labels.add(lettersGuessedLabel);
                    labels.add(unknownWordLabel);
                    labels.add(guessesRemainingLabel);
                    labels.add(userPanel);
                    frame.add(BorderLayout.CENTER,labels);
                    frame.setSize(300,150);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
    }
    
    private static class GuessListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String guess = getText();
            if(Hangman.checkUserGuess(guess) == true){
                Hangman.updateSecretWord(guess);
                drawSecretWord();
                if(Hangman.lettersGuessed.size() != 0){
                    drawLettersGuessed();
                }
                if(Hangman.checkIfWon()){
                    Hangman.winSequence();
                }else if (Hangman.guessesRemaining == 0){
                    Hangman.loseSequence();
                }
            }
        }
    }
    
    public static void drawGuessesRemaining(){
        final String guessesMessage = "Guesses remaining: "+ String.valueOf(Hangman.guessesRemaining);
        EventQueue.invokeLater(new Runnable(){
           public void run(){
               guessesRemainingLabel.setText(guessesMessage);
               guessesRemainingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            } 
        });
    }
    
    public static void drawLettersGuessed(){
        StringBuilder strBuilder = new StringBuilder();
        for (Character c : Hangman.lettersGuessed){
            String s = c+" ";
            strBuilder.append(s);
        }
    }
    
    public static int playAgainDialog(String str){
        return JOptionPane.showConfirmDialog(frame,str,"Play again?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
    
    public static String getText(){
        return textField.getText();
    }
    
    public static void setText(final String str){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                textField.setText(str);
            }
        });
    }
    
    public static void drawSecretWord(){
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<Hangman.lettersRevealed.length;i++){
            if (Hangman.lettersRevealed[i]){
                String s = Hangman.secretWord.charAt(i) + " ";
                builder.append(s);
            }else{
                builder.append("_ ");
            }
        }
    }
}
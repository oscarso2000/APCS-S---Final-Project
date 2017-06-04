    import java.awt.*;
    import java.io.*;
    import javax.swing.*;
public class mainGUI{
    //create fram
    private static JFrame frame;
    private static String str = "Welcome!";
    private static int ans = 0;
    
    public static void main (String [] args){
        //create objects of each game
        Snake snake = new Snake();
        Hangman hangman = new Hangman();
        //create pop up panel asking user to play game
        if(JOptionPane.showConfirmDialog(frame,str+"\nDo you want to play Snake? \nIf No, You will be asked to play another game.","", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION){
            snake.play();
        }else{
            if(JOptionPane.showConfirmDialog(frame,"Screw you. \nDo you want to play Hangman?","", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION){
                hangman.play();
            }else{ //glitch exit by command Q only
                //System.exit(0);
                while(ans == 0){
                    ans = JOptionPane.showConfirmDialog(frame,"You have said No too many times. \n This is now a virus.","", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                    ans = 0;
                }
            }    
        } 
        
    }
    
}
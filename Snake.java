import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame{
    public Snake () {
        add(new Board()); //add the snake game and rules to this class
        pack();
        setTitle("SNAKE by OSCAR SO"); //header
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void play (){
        EventQueue.invokeLater(new Runnable (){
            public void run(){
                JFrame k= new Snake(); //initialize game
                k.setResizable(false); //cannot adjust size
                k.setVisible(true); //make it visible for user
            }
        });
    }
}
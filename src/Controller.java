import javax.swing.*;

public class Controller {

    public static int WIDTH = 600;
    public static int HEIGHT = 800;



    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);


        MainMenu mainMenu = new MainMenu();
        mainMenu.getStatMenu();
        GameController game = new GameController();
        GamePanel panel = new GamePanel(game);




        new Thread(panel).start();


        frame.add(panel);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
    }
}

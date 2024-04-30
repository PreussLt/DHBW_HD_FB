import javax.swing.*;

public class MainMenu {
    public boolean getStatMenu(int highscorce){
        var option = JOptionPane.showOptionDialog(null, "Highscore: "+highscorce,"Hauptmenü",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Flappy Bird Starten", "Schließen"}, "A");

        if(option == 0){
            return true;
        }
        else {
            System.exit(1);
            return false;
        }

    }

    public boolean getRestartMenu(int highscorce,int score){
        var option = JOptionPane.showOptionDialog(null, "Youre Score: "+score+"\n Highscore:  "+highscorce,"Gameover",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Neustart", "Schließen"}, "A");

        if(option == 0){
            return true;
        }
        else {
            System.exit(1);
            return false;
        }
    }
}

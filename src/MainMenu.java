import javax.swing.*;

public class MainMenu {
    public void getStatMenu(){
        var option = JOptionPane.showOptionDialog(null, "Dies ist ein Optionsdialog","Optionsdialog",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Flappy Bird Starten", "Schließen", "C"}, "B");

    }
}

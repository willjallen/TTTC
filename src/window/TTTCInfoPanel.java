package window;

import game.TTTC;

import javax.swing.*;
import java.awt.*;

public class TTTCInfoPanel extends JPanel {
    JTextArea botData;

    public TTTCInfoPanel(){
        super();


        this.setPreferredSize(new Dimension(TTTC.DEFAULT_WINDOW_WIDTH/2, TTTC.DEFAULT_WINDOW_HEIGHT/2));
        this.setLayout(new GridLayout(1, 0));

        botData = new JTextArea();

        botData = new JTextArea();
        botData.setFont(new Font("TimesRoman", Font.BOLD, 16));
        botData.setLineWrap(true);
        botData.setPreferredSize(new Dimension(TTTC.DEFAULT_WINDOW_WIDTH/2, 400));
        botData.setEditable(false);

        this.add(botData);

    }

    public void setBotData(String string){
        botData.setText(string);
    }
}

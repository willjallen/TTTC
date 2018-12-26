package window;

import game.*;
import javax.swing.*;
import java.awt.*;




public class TTTCWindow {


    //Window shit
    public static JFrame window;
    public static TTTCGamePanel gamePanel;
    private TTTCInfoPanel infoPanel;
    private JPanel windowPanel;

    private JTextArea lastWinner;
    private JTextArea score;
    private JTextArea currentPlayer;
    private JButton newGame;

    private ActionMonitor actionMonitor;




    public void initWindow(){
        actionMonitor = new ActionMonitor();
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setSize(new Dimension(TTTC.DEFAULT_WINDOW_WIDTH, TTTC.DEFAULT_WINDOW_HEIGHT));
        window.addMouseListener(actionMonitor);

        Container windowContainer = window.getContentPane();
        windowContainer.setLayout(new GridLayout(1, 0));



        gamePanel = new TTTCGamePanel();
        gamePanel.setPreferredSize(new Dimension(TTTC.DEFAULT_WINDOW_WIDTH/2, TTTC.DEFAULT_WINDOW_HEIGHT/2));

        infoPanel = new TTTCInfoPanel();

        windowContainer.add(gamePanel, BorderLayout.LINE_START);
        windowContainer.add(infoPanel, BorderLayout.LINE_END);


        window.pack();
        window.setVisible(true);



    }

    public TTTCGamePanel getGamePanel(){
        return gamePanel;
    }

    public TTTCInfoPanel getInfoPanel(){
        return infoPanel;
    }


}

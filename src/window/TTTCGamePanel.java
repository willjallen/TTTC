package window;

import game.*;
import javax.swing.*;
import java.awt.*;

public class TTTCGamePanel extends JPanel {

    private double PANEL_WIDTH;
    private double PANEL_HEIGHT;


    Tile[][] tiles = TTTC.tiles;

    @Override
    public void paint(Graphics g){
        //Clear previous
        g.setColor(Color.white);
        g.fillRect(0, 0, (int)PANEL_WIDTH, (int)PANEL_HEIGHT);

        PANEL_WIDTH = this.getWidth();
        PANEL_HEIGHT = this.getHeight();

        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                tiles[i][j].draw(g, (int)PANEL_WIDTH, (int)PANEL_HEIGHT);
            }
        }

        g.dispose();

    }



    public double getPanelWidth() {
        return PANEL_WIDTH;
    }

    public void setPanelWidth(double PANEL_WIDTH) {
        this.PANEL_WIDTH = PANEL_WIDTH;
    }

    public double getPanelHeight() {
        return PANEL_HEIGHT;
    }

    public void setPanelHeight(double PANEL_HEIGHT) {
        this.PANEL_HEIGHT = PANEL_HEIGHT;
    }

}

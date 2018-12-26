package ai;

import game.TILE_STATE;
import game.TTTC;

public class RandomBot {

    public void playMove(TILE_STATE side){

        int genX = (int) (Math.random() * 3);
        int genY = (int) (Math.random() * 3);

        while(TTTC.tiles[genX][genY].getTileState() != TILE_STATE.blank){
             genX = (int)(Math.random() * 3);
             genY = (int)(Math.random() * 3);

        }


        TTTC.tiles[genX][genY].setTileState(side);
    }

}

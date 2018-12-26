package game;

import java.awt.*;

public class Tile {
    private int x;
    private int y;
    private int width;
    private int height;
    private TILE_STATE TILE_STATE;



    public Tile(int x, int y, TILE_STATE tile_state){
        this.TILE_STATE = tile_state;
        this.x = x;
        this.y = y;

    }

    public void draw(Graphics g, int w, int h){

        width = w/3;
        height = h/3;
        int drawX = x * width;
        int drawY = y * height;
        g.setColor(Color.black);

        g.drawRect(drawX, drawY, width, height);
        if(TILE_STATE == game.TILE_STATE.X) {
            g.drawLine(drawX, drawY, drawX + width, drawY + height);
            g.drawLine(drawX + width, drawY, drawX, drawY + height);
        } else if(TILE_STATE == game.TILE_STATE.O){
            g.drawOval(drawX + width/4, drawY + height/4, width/2, height/2);
        } else{
            // Do nothing
        }
    }

    public void switchTileState(){
        TILE_STATE = (TILE_STATE== game.TILE_STATE.O) ? game.TILE_STATE.X : game.TILE_STATE.O;
    }


    public void setTileState(TILE_STATE state){
        this.TILE_STATE = state;
    }

    public TILE_STATE getTileState(){
        return this.TILE_STATE;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
       return this.height;
    }

}

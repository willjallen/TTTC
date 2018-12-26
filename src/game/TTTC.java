package game;

import ai.RandomBot;
import window.ActionMonitor;
import window.TTTCGamePanel;
import window.TTTCInfoPanel;
import window.TTTCWindow;

import java.util.Timer;
import java.util.TimerTask;

public class TTTC implements Runnable {


    private Thread gameThread;
    //Static references
    public static int DEFAULT_WINDOW_WIDTH = 800;
    public static int DEFAULT_WINDOW_HEIGHT = 600;


    private long actionTimeout = 250; // 250 ms action timeout
    private long lastAction = 0;

    private TTTCWindow window;
    private TTTCGamePanel gamePanel;
    private TTTCInfoPanel infoPanel;


    //Game shit
    private TILE_STATE winner;
    private TILE_STATE player = TILE_STATE.O;
    public static boolean gameOver = false;
    public static boolean running = false;
    private int turnsPlayed;

    public static boolean botMode = true;
    public static boolean renderingEnabled = true;
    RandomBot randomBot = new RandomBot();

    private double numberOfGames = 1;
    private double numberOfXWins;
    private double numberOfOWins;
    private double numberOfDraws;

    String percentXWins;
    String percentOWins;
    String percentDraws;


    public static Tile[][] tiles;


    //Main method
    public static void main(String args[]) {
        new TTTC();
    }


    public TTTC() {
        initGame(TILE_STATE.blank);
    }

    public void initGame(TILE_STATE initTileState) {


        gameThread = new Thread(this);


        tiles = new Tile[3][3];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(i, j, initTileState);
            }
        }


        window = new TTTCWindow();
        window.initWindow();
        gamePanel = window.getGamePanel();
        infoPanel = window.getInfoPanel();


        running = true;

        gameThread.start();
        // Swing has a seizure when you do anything while the program is running so it must be multithreaded
        TimerTask updateInfoPanelTask = new TimerTask() {
            @Override
            public void run() {
                infoPanel.setBotData("Games Played " + numberOfGames + "\n" + percentXWins + percentOWins + percentDraws);
            }
        };

        Timer infoPanelTimer = new Timer();
        infoPanelTimer.scheduleAtFixedRate(updateInfoPanelTask, 0, 100);

    }




    public void startGame() {
        clearBoard();

        gameOver = false;
        ActionMonitor.mouseDown = false;
        player = TILE_STATE.O;
        turnsPlayed = 0;
    }


    @Override
    public void run() {
        while (!gameOver) {
            update();
            if (renderingEnabled) {
                gamePanel.repaint();
            }

            try {
                Thread.sleep((long) 0.16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    public boolean pollEvents() {


        if (System.currentTimeMillis() - lastAction > 250) {


            if (ActionMonitor.mouseDown && !gameOver) {


                if (ActionMonitor.mouseClickX < gamePanel.getPanelWidth() || ActionMonitor.mouseClickY < gamePanel.getPanelHeight()) {
                    int tileClickX = (int) ((ActionMonitor.mouseClickX / gamePanel.getPanelWidth()) * 3);
                    int tileClickY = (int) ((ActionMonitor.mouseClickY / gamePanel.getPanelHeight()) * 3);
                    if (tileClickX < tiles.length && tileClickY < tiles[0].length) {
                        if (tiles[tileClickX][tileClickY].getTileState() == TILE_STATE.blank) {
                            tiles[(int) ((ActionMonitor.mouseClickX / gamePanel.getPanelWidth()) * 3)][(int) ((ActionMonitor.mouseClickY / gamePanel.getPanelHeight()) * 3)].setTileState(player);
                            System.out.println("Polling events");
                            return true;
                        }
                    }
                }

                lastAction = (System.currentTimeMillis());
            }
        }

        return false;
    }


    public void update() {

        if (botMode) {
            randomBot.playMove(player);
            turnsPlayed++;
            player = (player == TILE_STATE.X) ? TILE_STATE.O : TILE_STATE.X;

            if(checkForWin()){
                startGame();
            }


        } else {
            if(pollEvents()){
                turnsPlayed++;
                player = (player == TILE_STATE.X) ? TILE_STATE.O : TILE_STATE.X;
                if(checkForWin()){
                    startGame();
                    return;
                }

                randomBot.playMove(player);
                turnsPlayed++;
                player = (player == TILE_STATE.X) ? TILE_STATE.O : TILE_STATE.X;
                if(checkForWin()){
                    startGame();
                    return;
                }
            }

        }




        percentXWins = String.format(" Percent X wins %.3f\n", (numberOfXWins / numberOfGames) * 100);
        percentOWins = String.format(" Percent O wins %.3f\n", (numberOfOWins / numberOfGames) * 100);
        percentDraws = String.format(" Percent draws %.3f\n", (numberOfDraws / numberOfGames) * 100);


    }



    public void clearBoard() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].setTileState(TILE_STATE.blank);
            }
        }

        System.out.println("Clearing board");


    }

    public boolean checkForWin() {

        /*
            XXX
            OOO
            OOO
         */
        //System.out.println(tiles[0][0].getTileState() == (tiles[1][0].getTileState()));
        if (tiles[0][0].getTileState() == (tiles[1][0].getTileState())) {
            if (tiles[0][0].getTileState() == (tiles[2][0]).getTileState()) {
                if (tiles[0][0].getTileState() != TILE_STATE.blank) {
                    winner = tiles[0][0].getTileState();
                    gameOver = true;

                }
            }
        }


        /*
            XOO
            XOO
            XOO
         */
        if (tiles[0][0].getTileState() == (tiles[0][1]).getTileState()) {
            if (tiles[0][0].getTileState() == (tiles[0][2]).getTileState()) {
                if (tiles[0][0].getTileState() != TILE_STATE.blank) {
                    winner = tiles[0][0].getTileState();
                    gameOver = true;

                }
            }

        }



        /*
            OOO
            XXX
            OOO
         */

        if (tiles[0][1].getTileState() == (tiles[1][1]).getTileState()) {
            if (tiles[1][1].getTileState() == (tiles[2][1]).getTileState()) {
                if (tiles[0][1].getTileState() != TILE_STATE.blank) {
                    winner = tiles[0][1].getTileState();
                    gameOver = true;

                }
            }

        }



        /*
            XOO
            OXO
            OOX
         */
        if (tiles[0][0].getTileState() == (tiles[1][1]).getTileState()) {
            if (tiles[1][1].getTileState() == (tiles[2][2]).getTileState()) {
                if (tiles[0][0].getTileState() != TILE_STATE.blank) {
                    winner = tiles[0][0].getTileState();
                    gameOver = true;

                }
            }
        }

        /*
           OOX
           OXO
           XOO
         */


        if (tiles[2][0].getTileState() == (tiles[1][1]).getTileState()) {
            if (tiles[1][1].getTileState() == (tiles[0][2]).getTileState()) {
                if (tiles[2][0].getTileState() != TILE_STATE.blank) {
                    winner = tiles[2][0].getTileState();
                    gameOver = true;

                }
            }
        }


        /*
            OOX
            OOX
            OOX
         */
        if (tiles[2][2].getTileState() == (tiles[2][1]).getTileState()) {
            if (tiles[2][2].getTileState() == (tiles[2][0]).getTileState()) {
                if (tiles[2][2].getTileState() != TILE_STATE.blank) {
                    winner = tiles[2][2].getTileState();
                    gameOver = true;

                }
            }
        }




        /*
        OXO
        OXO
        OXO
         */

        if (tiles[1][0].getTileState() == (tiles[1][1]).getTileState()) {
            if (tiles[1][1].getTileState() == (tiles[1][2]).getTileState()) {
                if (tiles[1][0].getTileState() != TILE_STATE.blank) {
                    winner = tiles[1][0].getTileState();
                    gameOver = true;

                }
            }
        }




        /*
        OOO
        OOO
        XXX
         */
        if (tiles[2][2].getTileState() == (tiles[1][2]).getTileState()) {
            if (tiles[2][2].getTileState() == (tiles[0][2]).getTileState()) {
                if (tiles[2][2].getTileState() != TILE_STATE.blank) {
                    winner = tiles[2][2].getTileState();
                    gameOver = true;

                }
            }

        }



        if (gameOver) {
            numberOfGames++;

            if (winner == TILE_STATE.X) {
                numberOfXWins++;
            } else {
                numberOfOWins++;
            }


            return true;

        } else if (turnsPlayed == 9) {
            numberOfGames++;
            numberOfDraws++;


            return true;


        }

        return false;

    }

}






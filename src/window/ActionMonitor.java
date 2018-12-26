package window;

import java.awt.event.*;

public class ActionMonitor implements MouseListener {

    public static boolean mouseDown;
    public static double mouseClickX, mouseClickY;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClickX = e.getX();
        mouseClickY = e.getY();
        mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

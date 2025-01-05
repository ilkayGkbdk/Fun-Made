package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean up, down, left, right;
    public boolean debug = false;
    public boolean enterPressed = false;

    public KeyHandler(GamePanel gp_) {
        gp = gp_;

        up = false;
        down = false;
        left = false;
        right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.mainState == GameState.PLAY) {
            if (code == KeyEvent.VK_W) {
                up = true;
            }
            if (code == KeyEvent.VK_S) {
                down = true;
            }
            if (code == KeyEvent.VK_A) {
                left = true;
            }
            if (code == KeyEvent.VK_D) {
                right = true;
            }
            if (code == KeyEvent.VK_T) {
                debug = !debug;
            }
            if (code == KeyEvent.VK_P) {
                gp.mainState = GameState.PAUSE;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }
        else if (gp.mainState == GameState.PAUSE) {
            if (code == KeyEvent.VK_P) {
                gp.mainState = GameState.PLAY;
            }
        }
        else if (gp.mainState == GameState.MAIN_MENU) {
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.mainState = GameState.PLAY;
                }
                else if (gp.ui.commandNum == 1) {
                    // TODO: Load game
                }
                else if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        else if (gp.mainState == GameState.DIALOGUE) {
            if (code == KeyEvent.VK_ENTER) {
                gp.mainState = GameState.PLAY;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            up = false;
        }
        if (code == KeyEvent.VK_S) {
            down = false;
        }
        if (code == KeyEvent.VK_A) {
            left = false;
        }
        if (code == KeyEvent.VK_D) {
            right = false;
        }
    }
}

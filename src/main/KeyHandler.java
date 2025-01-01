package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

      GamePanel gp;

      public KeyHandler(GamePanel gp) {
            this.gp = gp;
      }

      public boolean upPressed,
                  downPressed,
                  leftPressed,
                  rightPressed,
                  shiftPressed, shiftToggle;

      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {

            int code = e.getKeyCode();

            if (code == KeyEvent.VK_W) {
                  upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                  downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                  leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                  rightPressed = true;
            }

            if (code == KeyEvent.VK_SHIFT) {
                  shiftPressed = true;

                  if (!shiftToggle) {
                        if (gp.time >= 3) {
                              gp.time = 0;
                        } else {
                              gp.time++;
                        }
                        shiftToggle = true;
                  }
            }

      }

      @Override
      public void keyReleased(KeyEvent e) {

            int code = e.getKeyCode();

            if (code == KeyEvent.VK_W) {
                  upPressed = false;
            }
            if (code == KeyEvent.VK_S) {
                  downPressed = false;
            }
            if (code == KeyEvent.VK_A) {
                  leftPressed = false;
            }
            if (code == KeyEvent.VK_D) {
                  rightPressed = false;
            }

            if (code == KeyEvent.VK_SHIFT) {
                  shiftPressed = false;
                  shiftToggle = false;
            }

      }
}

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyInput {
    public static void main(String[] args) {
        // Create a key listener
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used in this example
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP:
                        controlUp();
                        break;
                    case KeyEvent.VK_RIGHT:
                        controlRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        controlDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        controlLeft();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        controlExit();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used in this example
            }
        };


    }

    // Methods to handle key events
    private static void controlUp() {
        // Implement action for UP key
        System.out.println("up");
    }

    private static void controlRight() {
        // Implement action for RIGHT key
        System.out.println("right");
    }

    private static void controlDown() {
        // Implement action for DOWN key
        System.out.println("down");
    }

    private static void controlLeft() {
        // Implement action for LEFT key
        System.out.println("left");
    }

    private static void controlExit() {
        // Implement action for ESCAPE key (exit the program)
        System.out.println("esc");
    }
}

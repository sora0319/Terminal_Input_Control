import java.awt.Frame;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class awtGUIReader extends Frame implements KeyListener {

    public awtGUIReader() {
        super("Key Input Example");
        Label label = new Label("Type something (press 'q' to quit): ");
        add(label);
        setSize(400, 100);
        addKeyListener(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        new awtGUIReader();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        System.out.println("Typed: " + keyChar);

        // Check if the typed key is 'q'
        if (keyChar == 'q') {
            System.out.println("'q' typed. Exiting...");
            dispose(); // Close the window and exit the program
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not used in this example
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this example
    }
}

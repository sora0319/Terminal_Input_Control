import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class keyLisenerGlobal implements NativeKeyListener {
    public static void main(String[] args) {
        try {
            // Register the native hook.
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            ex.printStackTrace();
            System.exit(1);
        }

        // Add the key listener.
        GlobalScreen.addNativeKeyListener(new keyLisenerGlobal());

        System.out.println("Press 'A' key to exit...");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Check if the 'A' key (VC_A) is pressed.
        if (e.getKeyCode() == NativeKeyEvent.VC_A) {
            System.out.println("'A' key pressed. Exiting...");
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Do nothing
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Do nothing
    }
}

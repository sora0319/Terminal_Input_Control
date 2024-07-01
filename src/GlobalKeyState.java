import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class GlobalKeyState {

    public interface KeyStateLibrary extends Library {
        KeyStateLibrary INSTANCE = Native.load(getLibraryName(), KeyStateLibrary.class);

        // Define method to get key state
        int getKeyState(int keyCode);
    }

    // Method to get appropriate library name based on platform
    private static String getLibraryName() {
        return Platform.isWindows() ? "user32" :
                (Platform.isLinux() ? "X11" : "ApplicationServices");
    }

    // Method to check if a key is pressed
    public static boolean isKeyPressed(int keyCode) {
        return KeyStateLibrary.INSTANCE.getKeyState(keyCode) != 0;
    }

    public static void main(String[] args) {
        System.out.println("Press 'A' key to exit...");

        while (true) {
            if (isKeyPressed(getKeyCodeForA())) {
                System.out.println("'A' key pressed. Exiting...");
                break;
            }
            try {
                Thread.sleep(100); // sleep to reduce CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to get appropriate key code based on platform
    private static int getKeyCodeForA() {
        return Platform.isWindows() ? 0x41 :
                (Platform.isLinux() ? 0x41 : // Adjust for Linux if necessary
                        (Platform.isMac() ? getKeyCodeForMacA() : -1)); // Handle macOS if needed
    }

    // Example method for macOS key code mapping
    private static int getKeyCodeForMacA() {
        // Adjust this method as per macOS key code requirements
        return 0; // Placeholder, replace with actual macOS key code
    }
}

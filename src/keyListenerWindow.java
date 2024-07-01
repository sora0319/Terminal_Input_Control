import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class keyListenerWindow {

    // User32 DLL의 GetAsyncKeyState 함수를 정의합니다.
    public interface User32 extends Library {
        User32 INSTANCE = Native.load(Platform.isWindows() ? "user32" : "c", User32.class);

        short GetAsyncKeyState(int key);
    }

    public static void main(String[] args) {
        System.out.println("Press 'A' key to exit...");

        // 'A' 키의 키 코드는 0x41입니다.
        int aKeyCode = 0x41;

        while (true) {
            // GetAsyncKeyState를 사용하여 'A' 키가 눌렸는지 확인합니다.
            short keyState = User32.INSTANCE.GetAsyncKeyState(aKeyCode);

            // 키가 눌렸는지 확인하는 조건입니다.
            if ((keyState & 0x8000) != 0) {
                System.out.println("'A' key pressed. Exiting...");
                break;
            }

            // CPU 사용량을 줄이기 위해 약간의 지연을 줍니다.
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

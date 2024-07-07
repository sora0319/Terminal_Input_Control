import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.win32.StdCallLibrary;

public class EchoMode {

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);

        int GetStdHandle(int nStdHandle);
        boolean GetConsoleMode(int hConsoleHandle, IntByReference lpMode);
        boolean SetConsoleMode(int hConsoleHandle, int dwMode);
    }

    private static int originalConsoleMode;

    private static void setupWindowsConsole() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        IntByReference consoleMode = new IntByReference();
        int hStdin = kernel32.GetStdHandle(-10); // STD_INPUT_HANDLE
        if (kernel32.GetConsoleMode(hStdin, consoleMode)) {
            originalConsoleMode = consoleMode.getValue();
            int newConsoleMode = originalConsoleMode & ~0x4; // 비에코 모드 비활성화
            kernel32.SetConsoleMode(hStdin, newConsoleMode);
        }
    }

    private static void restoreWindowsConsole() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        int hStdin = kernel32.GetStdHandle(-10); // STD_INPUT_HANDLE
        kernel32.SetConsoleMode(hStdin, originalConsoleMode);
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(EchoMode::restoreWindowsConsole));
        setupWindowsConsole();

        // 프로그램 실행 코드 (예: 입력을 받는 루프)
        System.out.println("콘솔 모드가 비에코 모드로 설정되었습니다. 종료하려면 엔터를 누르세요.");

        int input;
        try {
            while ((input = System.in.read()) != -1) {
                if (input == '\n' || input == '\r') {
                    break;
                }
                System.out.print((char) input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

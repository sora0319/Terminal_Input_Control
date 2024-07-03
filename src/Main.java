import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public class Main {
    // Windows용 JNA 인터페이스
    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);
        int GetConsoleMode(int hConsoleHandle, IntByReference lpMode);
        int SetConsoleMode(int hConsoleHandle, int dwMode);
        int GetStdHandle(int nStdHandle);
    }

    // Unix용 JNA 인터페이스
    public interface LibC extends Library {
        LibC INSTANCE = Native.load("c", LibC.class);
        int tcgetattr(int fd, termios termios);
        int tcsetattr(int fd, int optionalActions, termios termios);

        class termios extends com.sun.jna.Structure {
            public com.sun.jna.ptr.ByteByReference c_iflag = new com.sun.jna.ptr.ByteByReference();
            public com.sun.jna.ptr.ByteByReference c_oflag = new com.sun.jna.ptr.ByteByReference();
            public com.sun.jna.ptr.ByteByReference c_cflag = new com.sun.jna.ptr.ByteByReference();
            public com.sun.jna.ptr.ByteByReference c_lflag = new com.sun.jna.ptr.ByteByReference();
            public byte[] c_cc = new byte[20];

            @Override
            protected java.util.List<String> getFieldOrder() {
                return java.util.Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc");
            }
        }
    }

    public static void main(String[] args) {
        if (Platform.isWindows()) {
            setupWindowsConsole();
        } else {
            setupUnixConsole();
        }

        System.out.println("문자를 입력하세요 (Enter를 누르지 않고 바로 입력됩니다):");

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

    private static void setupWindowsConsole() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        IntByReference consoleMode = new IntByReference();
        int hStdin = kernel32.GetStdHandle(-10); // STD_INPUT_HANDLE
        kernel32.GetConsoleMode(hStdin, consoleMode);
        int newConsoleMode = consoleMode.getValue() & ~0x4; // 비에코 모드 비활성화
        kernel32.SetConsoleMode(hStdin, newConsoleMode);
    }

    private static void setupUnixConsole() {
        LibC libc = LibC.INSTANCE;
        LibC.termios termios = new LibC.termios();
        libc.tcgetattr(0, termios); // STDIN_FILENO
        termios.c_lflag.setValue((byte) (termios.c_lflag.getValue() & ~(0x0004 | 0x0002))); // 비에코 모드 비활성화
        libc.tcsetattr(0, 0, termios);
    }
}

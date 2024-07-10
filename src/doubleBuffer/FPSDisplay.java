package doubleBuffer;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.*;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FPSDisplay {
    // JNA 인터페이스 정의
    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

        HANDLE CreateConsoleScreenBuffer(int dwDesiredAccess, int dwShareMode,
                                         Pointer lpSecurityAttributes, int dwFlags, Pointer lpScreenBufferData);
        boolean SetConsoleActiveScreenBuffer(HANDLE hConsoleOutput);
        boolean SetConsoleCursorPosition(HANDLE hConsoleOutput, COORD dwCursorPosition);
        boolean WriteConsoleOutputCharacter(HANDLE hConsoleOutput, String lpCharacter, int nLength, COORD dwWriteCoord, IntByReference lpNumberOfCharsWritten);
        boolean FillConsoleOutputCharacter(HANDLE hConsoleOutput, char cCharacter, int nLength, COORD dwWriteCoord, IntByReference lpNumberOfCharsWritten);
        boolean CloseHandle(HANDLE hObject);

        int GENERIC_READ = 0x80000000;
        int GENERIC_WRITE = 0x40000000;
        int CONSOLE_TEXTMODE_BUFFER = 1;
    }

    public static class HANDLE extends PointerType {}

    public static class COORD extends Structure {
        public short X;
        public short Y;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("X", "Y");
        }

        public COORD() {}

        public COORD(short x, short y) {
            this.X = x;
            this.Y = y;
        }
    }

    private static HANDLE[] g_hScreen = new HANDLE[2];
    private static int g_nScreenIndex = 0;
    private static int g_numofFPS = 0;
    private static long OldTime;

    public static void main(String[] args) {
        Timer timer = new Timer();

        ScreenInit();

        OldTime = System.currentTimeMillis();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Render();
            }
        }, 0, 16); // 약 60FPS (16ms 마다 갱신)

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            timer.cancel();
            ScreenRelease();
        }));
    }

    private static void ScreenInit() {
        Kernel32 kernel32 = Kernel32.INSTANCE;

        // 화면 버퍼 2개를 만든다.
        g_hScreen[0] = kernel32.CreateConsoleScreenBuffer(Kernel32.GENERIC_READ | Kernel32.GENERIC_WRITE, 0, null, Kernel32.CONSOLE_TEXTMODE_BUFFER, null);
        g_hScreen[1] = kernel32.CreateConsoleScreenBuffer(Kernel32.GENERIC_READ | Kernel32.GENERIC_WRITE, 0, null, Kernel32.CONSOLE_TEXTMODE_BUFFER, null);

        // 커서를 숨긴다.
        kernel32.SetConsoleCursorPosition(g_hScreen[0], new COORD((short)0, (short)0));
        kernel32.SetConsoleCursorPosition(g_hScreen[1], new COORD((short)0, (short)0));
    }

    private static void ScreenFlipping() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        kernel32.SetConsoleActiveScreenBuffer(g_hScreen[g_nScreenIndex]);
        if(g_nScreenIndex == 0){
            g_nScreenIndex = 1;
        }
        if(g_nScreenIndex == 1){
            g_nScreenIndex = 0;
        }
    }

    private static void ScreenClear() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        COORD Coor = new COORD((short)0, (short)0);
        IntByReference dw = new IntByReference();
        kernel32.FillConsoleOutputCharacter(g_hScreen[g_nScreenIndex], ' ', 80 * 25, Coor, dw);
    }

    private static void ScreenRelease() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        kernel32.CloseHandle(g_hScreen[0]);
        kernel32.CloseHandle(g_hScreen[1]);
    }

    private static void ScreenPrint(int x, int y, String string) {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        COORD CursorPosition = new COORD((short)x, (short)y);
        IntByReference dw = new IntByReference();
        kernel32.SetConsoleCursorPosition(g_hScreen[g_nScreenIndex], CursorPosition);
        kernel32.WriteConsoleOutputCharacter(g_hScreen[g_nScreenIndex], string, string.length(), CursorPosition, dw);
    }

    private static void Render() {
        ScreenClear();

        long CurTime = System.currentTimeMillis();
        if (CurTime - OldTime >= 1000) { // 1초마다 FPS 갱신
            OldTime = CurTime;
            g_numofFPS = 0;
        }

        g_numofFPS++;
        ScreenPrint(0, 0, "FPS : " + g_numofFPS);
        ScreenFlipping();
    }
}


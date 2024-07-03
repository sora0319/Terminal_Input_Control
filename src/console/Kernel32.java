package console;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);

    boolean SetConsoleScreenBufferSize(WinNT.HANDLE hConsoleOutput, Wincon.COORD dwSize);
    boolean SetConsoleWindowInfo(WinNT.HANDLE hConsoleOutput, boolean bAbsolute, SMALL_RECT.ByReference lpConsoleWindow);
    WinNT.HANDLE GetStdHandle(int nStdHandle);

    boolean GetConsoleScreenBufferInfo(WinNT.HANDLE hConsoleOutput, Wincon.CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);
}
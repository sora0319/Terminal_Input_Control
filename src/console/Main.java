package console;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon;

public class Main {
        public static void main(String[] args) {
        WinNT.HANDLE hConsoleOutput = Kernel32.INSTANCE.GetStdHandle(Wincon.STD_OUTPUT_HANDLE);

        // 현재 콘솔 창 크기를 얻습니다
        Wincon.CONSOLE_SCREEN_BUFFER_INFO bufferInfo = new Wincon.CONSOLE_SCREEN_BUFFER_INFO();
        if (Kernel32.INSTANCE.GetConsoleScreenBufferInfo(hConsoleOutput, bufferInfo)) {
            System.out.println("현재 콘솔 창 크기: " + (bufferInfo.srWindow.Right - bufferInfo.srWindow.Left + 1) + " x " + (bufferInfo.srWindow.Bottom - bufferInfo.srWindow.Top + 1));
        } else {
            System.out.println("콘솔 창 크기 정보를 얻는 데 실패했습니다. 오류 코드: " + Native.getLastError());
            return;
        }

        // 콘솔 창 크기를 현재 크기보다 작게 설정
        SMALL_RECT.ByReference smallRect = new SMALL_RECT.ByReference();
        smallRect.Left = 0;        smallRect.Top = 0;
        smallRect.Right = 1;  // Width 최소값
        smallRect.Bottom = 1; // Height 최소값
        boolean resultSmallWindow = Kernel32.INSTANCE.SetConsoleWindowInfo(hConsoleOutput, true, smallRect);
        if (!resultSmallWindow) {
            System.out.println("콘솔 창 크기를 줄이는 데 실패했습니다. 오류 코드: " + Native.getLastError());
            return;
        }

        // 콘솔 화면 버퍼 크기 설정
        Wincon.COORD bufferSize = new Wincon.COORD();
        bufferSize.X = 150;
        bufferSize.Y = 50;
        boolean resultBuffer = Kernel32.INSTANCE.SetConsoleScreenBufferSize(hConsoleOutput, bufferSize);
        if (resultBuffer) {
            System.out.println("콘솔 화면 버퍼 크기 설정 성공");
        } else {
            System.out.println("콘솔 화면 버퍼 크기 설정 실패. 오류 코드: " + Native.getLastError());
        }

        SMALL_RECT.ByReference rect = new SMALL_RECT.ByReference();
        rect.Left = 0;
        rect.Top = 0;
        rect.Right = 149;  // Width - 1
        rect.Bottom = 49; // Height - 1

        // 콘솔 창 크기를 설정
        boolean resultWindow = Kernel32.INSTANCE.SetConsoleWindowInfo(hConsoleOutput, true, rect);
        if (resultWindow) {
            System.out.println("콘솔 창 크기 설정 성공");
        } else {
            System.out.println("콘솔 창 크기 설정 실패. 오류 코드: " + Native.getLastError());
        }
    }
}


//package control;
//
//public class ConsoleSize {
//    public static void main(String[] args) {
//        NativeUtilsImpl utils = new NativeUtilsImpl();
//        utils.setConsoleSizeAndTitle("창 제목", 100, 30);
//    }
//}

package control;

import com.sun.jna.Pointer;

import java.io.IOException;

public class ConsoleSize {

    public static void main(String[] args) {
        try {
            resizeConsole(80, 30); // 너비 80, 높이 30으로 설정
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resizeConsole(int width, int height) throws IOException {
        Kernel32 kernel32 = Kernel32.INSTANCE;

        // 콘솔 화면 버퍼 크기 변경
        Kernel32.COORD newSize = new Kernel32.COORD((short) width, (short) height);
        Pointer hConsoleOutput = kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        kernel32.SetConsoleScreenBufferSize(hConsoleOutput, newSize);

        // 콘솔 창 크기 변경
        Kernel32.SMALL_RECT rect = new Kernel32.SMALL_RECT((short) 0, (short) 0, (short) (width - 1), (short) (height - 1));
        kernel32.SetConsoleWindowInfo(hConsoleOutput, true, rect);
    }
}


package control;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import java.util.Arrays;
import java.util.List;

public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

    // 추가할 상수 정의
    int STD_OUTPUT_HANDLE = -11; // 표준 출력 핸들

    // 추가할 함수 정의
    // 표준 출력 핸들을 가져오는 함수 정의
    Pointer GetStdHandle(int nStdHandle);

    // 콘솔 화면 버퍼 크기를 설정하는 함수 정의
    boolean SetConsoleScreenBufferSize(Pointer hConsoleOutput, COORD dwSize);

    // 콘솔 창 크기를 설정하는 함수 정의
    boolean SetConsoleWindowInfo(Pointer hConsoleOutput, boolean bAbsolute, SMALL_RECT lpConsoleWindow);

    class COORD extends Structure {
        public short X;
        public short Y;

        public COORD() {} // JNA를 사용할 때는 기본 생성자가 필요합니다.

        public COORD(short X, short Y) {
            this.X = X;
            this.Y = Y;
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("X", "Y");
        }
    }

    class SMALL_RECT extends Structure {
        public short Left;
        public short Top;
        public short Right;
        public short Bottom;

        public SMALL_RECT() {} // JNA를 사용할 때는 기본 생성자가 필요합니다.

        public SMALL_RECT(short Left, short Top, short Right, short Bottom) {
            this.Left = Left;
            this.Top = Top;
            this.Right = Right;
            this.Bottom = Bottom;
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("Left", "Top", "Right", "Bottom");
        }
    }
}


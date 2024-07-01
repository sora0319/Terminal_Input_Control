import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class readTest {

    // JNA를 사용하여 C 라이브러리 함수를 호출하기 위한 인터페이스 정의
    public interface CLibrary extends Library {
        CLibrary INSTANCE = Native.load(Platform.isWindows() ? "msvcrt" : "c", CLibrary.class);

        // C의 getchar() 함수를 자바 메서드로 매핑
        int getchar();
        // C의 putchar() 함수를 자바 메서드로 매핑
        void putchar(int c);
    }

    public static void main(String[] args) {
        // 라이브러리 인스턴스 생성
        CLibrary lib = CLibrary.INSTANCE;

        System.out.println("문자를 입력하세요 (Enter를 누르지 않고 바로 입력됩니다):");

        // getchar()를 이용하여 입력을 받고 putchar()로 바로 출력
        int input;
        while ((input = lib.getchar()) != '\n' && input != '\r' && input != -1) {
            lib.putchar(input);
        }
        System.out.println(); // 마지막에 줄바꿈 추가
    }
}

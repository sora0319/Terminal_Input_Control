public class JavaArchitectureChecker {
    public static void main(String[] args) {
        String arch = System.getProperty("os.arch");
        System.out.println("Java 실행 환경 아키텍처: " + arch);

        if (arch.contains("64")) {
            System.out.println("Java 실행 환경은 64-bit입니다.");
        } else {
            System.out.println("Java 실행 환경은 32-bit입니다.");
        }
    }
}

package laba1;

public class Main {
    public static void main(String[] args) {
        Kernel kernel = new Kernel();
        System.out.printf("KERNEL system calls:\n%s\n\n", kernel);

        // Command 1 (OPEN)
        kernel.pushToStack("r");
        kernel.pushToStack("file1");
        kernel.exec(1);

        // Command 2 (READ)
        kernel.pushToStack(64);
        kernel.pushToStack("X");
        kernel.pushToStack("file1");
        kernel.exec(2);

        // Command 3 (CLOSE)
        kernel.pushToStack("file1");
        kernel.exec(4);

        // Command 4 (EXEC)
        kernel.pushToStack("-la");
        kernel.pushToStack("ls");
        kernel.exec(6);

        // Command 5 (KILL)
        kernel.pushToStack(1234);
        kernel.exec(9);
    }
}

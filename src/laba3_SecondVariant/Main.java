package laba3_SecondVariant;

import laba3_SecondVariant.OperatingSystem.OperatingSystem;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Memory size: ");
        int memorySize = 0;
        try {
            memorySize = Integer.parseInt(br.readLine());
        }
        catch(Exception ex){
            System.err.println("Invalid Format!");
            System.exit(0);
        }
        System.out.print("Page size: ");
        int pageSize = 0;
        try {
            pageSize = Integer.parseInt(br.readLine());
        }
        catch(Exception ex){
            System.err.println("Invalid Format!");
            System.exit(0);
        }

        System.out.print("\nWaiting for commands. Type help or ? for info.\n");
        OperatingSystem operatingSystem = new OperatingSystem(memorySize, pageSize);

        String command = "";
        while (!command.equals("q")) {
            if (command.startsWith("help") || command.startsWith("?")) {
                System.out.print(
                        "#####HELP#####\n" +
                                "    help ?        This menu\n" +
                                "    p list        List of all processes\n" +
                                "    p new         Create new process\n" +
                                "    p XXX list    List of all pages for process, where XXX is process id\n" +
                                "    p XXX new     Add new page for process, where XXX is process id\n" +
                                "    p XXX YYY     Get page for process and load it to memory\n" +
                                "    m             List of current memory state\n"
                );
            }
            else if (command.startsWith("p")) {
                command = command.substring(2, command.length());
                if (command.startsWith("l")) {
                    operatingSystem.printProcesses();
                }
                else if (command.startsWith("n")) {
                    operatingSystem.addProcess();
                }
                else {
                    String[] commands = command.split(" ");
                    if (commands.length == 2) {
                        try {
                            int processId = Integer.parseInt(commands[0]);
                            if (commands[1].startsWith("l")) {
                                operatingSystem.printProcess(processId);
                            }
                            else if (commands[1].startsWith("n")) {
                                operatingSystem.addPage(processId);
                            }
                            else {
                                try {
                                    int pageId = Integer.parseInt(commands[1]);
                                    operatingSystem.getPage(processId, pageId);
                                }
                                catch(NumberFormatException ex){
                                    System.err.println("Invalid page ID Format!");
                                }
                            }
                        }
                        catch(NumberFormatException ex){
                            System.err.println("Invalid process ID Format!");
                        }
                    }
                }
            }
            else if (command.startsWith("m")) {
                operatingSystem.printMemory();
            }

            try {
                System.out.print("~$ ");
                command = br.readLine();
            }
            catch(Exception ex){
                ex.printStackTrace();
                System.exit(0);
            }
        }

    }
}

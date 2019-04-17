package laba1;

import java.util.*;

public class Kernel {
    private Map<Integer, SystemCall> systemCalls;
    Stack stack = new Stack(10);

    public Kernel() {
        this.systemCalls = new HashMap<>();
        this.systemCalls.put(1, new SystemCall("open", new Class[] { String.class, String.class }, "FILEHANDLE MODE"));
        this.systemCalls.put(2, new SystemCall("read", new Class[] { String.class, String.class, Integer.class }, "FILEHANDLE BUF COUNT"));
        this.systemCalls.put(3, new SystemCall("write", new Class[] { String.class, String.class, Integer.class }, "FILEHANDLE BUF COUNT"));
        this.systemCalls.put(4, new SystemCall("close", new Class[] { String.class }, "FILEHANDLE"));
        this.systemCalls.put(5, new SystemCall("wait", new Class[] { Integer.class }, "PID"));
        this.systemCalls.put(6, new SystemCall("exec", new Class[] { String.class, String.class }, "PATH ARGS"));
        this.systemCalls.put(7, new SystemCall("fork", new Class[] { Integer.class }, "PID"));
        this.systemCalls.put(8, new SystemCall("exit", new Class[] { Integer.class }, "PID"));
        this.systemCalls.put(9, new SystemCall("kill", new Class[] { Integer.class }, "PID"));
    }


    public String help() {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<Integer, SystemCall> entry : this.systemCalls.entrySet()) {
            int key = entry.getKey();
            SystemCall systemCall = entry.getValue();
            result.append(key).append("\t").append(systemCall).append(System.lineSeparator());
        }
        return result.toString();
    }

    public void pushToStack(Object object) {
        stack.addElement(object);
    }

    public void exec(int systemCallId) throws IllegalArgumentException {
        SystemCall systemCall = this.systemCalls.get(systemCallId);
        if (systemCall == null) {
            String exceptionMessage = String.format("Invalid system call id \"%d\"", systemCallId);
            throw new IllegalArgumentException(exceptionMessage);
        }

        ArrayList<Object> arguments = new ArrayList<>();
        for (int i = 0; i < systemCall.getArgumentsCount(); i++) {
            if (!stack.isEmpty()) {
                arguments.add(stack.deleteElement());
            }
        }
        String command = systemCall.exec(arguments.toArray(new Object[0]));
        System.out.printf("KERNEL exec: %s\n", command);
    }

    @Override
    public String toString() {
        return this.help();
    }
}

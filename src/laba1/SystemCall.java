package laba1;

public class SystemCall {
    private String command;
    private Class[] argumentClasses;
    private String about;

    public SystemCall(String command, Class[] argumentClasses, String about) {
        this.command = command;
        this.argumentClasses = argumentClasses;
        this.about = about;
    }

    public int getArgumentsCount() {
        return this.argumentClasses.length;
    }

    public String exec(Object[] args) throws IllegalArgumentException {
        if (args.length != this.argumentClasses.length) {
            String exceptionMessage = String.format(
                    "Invalid number of arguments %d, need %d",
                    args.length, this.argumentClasses.length
            );
            throw new IllegalArgumentException(exceptionMessage);
        }

        StringBuilder result = new StringBuilder();
        result.append(this.command).append(" ");
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class argumentClass = this.argumentClasses[i];
            try {
                result.append(argumentClass.cast(arg)).append(" ");
            }
            catch (IllegalArgumentException exception) {
                String exceptionMessage = String.format(
                        "The argument \"%s\" must be of type %s",
                        arg, argumentClass
                );
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return this.command + "\t" + this.about;
    }
}

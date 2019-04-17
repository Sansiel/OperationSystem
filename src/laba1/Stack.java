package laba1;

public class Stack {
    private int mSize;
    private Object[] stackArray;
    private int top;

    public Stack(int m) {
        this.mSize = m;
        stackArray = new Object [mSize];
        top = -1;
    }

    public void addElement(Object element) {
        stackArray[++top] = element;
    }

    public Object deleteElement() {
        return stackArray[top--];
    }

    public Object peek() {
        return stackArray[top];

    }

    public boolean isEmpty() {
        return (top == -1);
    }
}
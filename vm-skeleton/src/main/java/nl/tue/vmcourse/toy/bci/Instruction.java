package nl.tue.vmcourse.toy.bci;

public class Instruction {

    private int count;
    private int capacity;
    private int code;
    private int lines;


    public Instruction(int count, int capacity, int code, int lines) {
        this.count = count;
        this.capacity = capacity;
        this.code = code;
        this.lines = lines;
    }


}

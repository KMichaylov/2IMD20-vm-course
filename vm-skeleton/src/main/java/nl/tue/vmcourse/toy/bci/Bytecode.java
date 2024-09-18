package nl.tue.vmcourse.toy.bci;

import java.util.ArrayList;
import java.util.List;

public class Bytecode {
    List<Instruction> listOfInstructions;

    public Bytecode(List<Instruction> listOfInstructions) {
        if (listOfInstructions == null) {
            listOfInstructions = new ArrayList<Instruction>();
        }
        this.listOfInstructions = listOfInstructions;
    }
}

import com.cpp.project6.assembler.Assembler;

import java.io.IOException;

public class Driver {
    static void main(String[] args) throws IOException {
        Assembler assembler = new Assembler("src/main/resources/MaxL.asm");
        assembler.firstPass();
        assembler.secondPass();
        assembler.writeToTargetFile();
    }
}

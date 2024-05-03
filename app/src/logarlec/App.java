
package logarlec;

import logarlec.control.Interpreter;
import logarlec.view.frames.MenuFrame;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class App {
    public static void main(String[] args) {
        // try {
        //     System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
        // } catch (UnsupportedEncodingException e) {
        //     throw new InternalError("VM does not support mandatory encoding UTF-8");
        // }

        // Interpreter.getInstance().handleInput(System.in);
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }
}

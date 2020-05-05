package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


/** The suite of all JUnit tests for the gitlet package.
 *  @author
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(UnitTest.class));
    }

    /** Testing opening files */
    @Test
    public void filesInDir() {
        return;
    }

    /** Testing writing bytes. */
    @Test
    public void writeBytes() throws IOException {
        File blah = Utils.join(Main.getCWD(), "blah.txt");
        System.out.println(blah.exists());
        File b1 = Utils.join(Main.getCWD(), "b1.txt");
        File b2 = Utils.join(Main.getCWD(), "b2.txt");
        String head = "<<<<<<< HEAD\n";
        String mid = "\n=======\n";
        String end = "\n>>>>>>>\n";
        String nil = "";
        byte[] headB = head.getBytes();
        byte[] midB = mid.getBytes();
        byte[] endB = end.getBytes();
        byte[] b1Cont = Utils.readContents(b1);
        byte[] b2Cont = nil.getBytes();

        ByteArrayOutputStream newCont = new ByteArrayOutputStream();
        newCont.write(headB);
        newCont.write(b1Cont);
        newCont.write(midB);
        newCont.write(b2Cont);
        newCont.write(endB);


        Utils.writeContents(blah, newCont.toByteArray());



    }
}



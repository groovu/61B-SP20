package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

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
    public void readFile() {
        File f = Utils.join(Main.getCWD(), "test.txt");
        System.out.println(Utils.sha1(Utils.readContents(f)));
        String test = Utils.readContentsAsString(f);
        System.out.println(test);
        test += "boo ";
        Utils.writeContents(f, test);
        System.out.println(Utils.sha1(Utils.readContents(f)));
    }

}



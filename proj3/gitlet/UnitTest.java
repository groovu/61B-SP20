package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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

    @Test
    public void hashTest() {
        File empty1 = Utils.join("./testing/empty1.txt");
        File empty2 = Utils.join("./testing/empty2.txt");
        File test = Utils.join("./testing/bob.txt");
        byte[] testc = Utils.readContents(test);
        byte[] empty1c= Utils.readContents(empty1);
        byte[] empty2c = Utils.readContents(empty2);
        assertEquals(Utils.sha1(empty1c), Utils.sha1(empty2c));
        System.out.println(Utils.sha1(testc));
    }

    @Test
    public void writeToDisk() throws IOException {
        String boo = "Boo!";
        String sha = Utils.sha1(boo);
        //File test = new File("./testing/", sha);
        File test = Utils.join("./testing/", sha);
        test.createNewFile();

    }

}



package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        byte[] empty1c = Utils.readContents(empty1);
        byte[] empty2c = Utils.readContents(empty2);
        assertEquals(Utils.sha1(empty1c), Utils.sha1(empty2c));
        System.out.println(Utils.sha1(testc));
    }

    @Test
    public void writeText() {
        File test = Utils.join("./testing/global.txt");
        Utils.writeContents(test, "Hello");
    }
    @Test
    public void filesInDir() {
        List<String> test = Utils.plainFilenamesIn(Main.getCWD());
        System.out.println(test.toString());
        File dir = new File(".");
        System.out.println(Arrays.toString(dir.list()));
    }
    @Test
    public void readObjTest() throws IOException {
        File test = Utils.join("./testing/scratch/test");
        test.createNewFile();
        Index i = new Index();
        Utils.writeObject(test, i);
        Index b = Utils.readObject(test, Index.class);
        System.out.println(b.blobs());
        System.out.println(test.getClass());

    }

}



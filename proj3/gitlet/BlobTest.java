package gitlet;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class BlobTest {
    public static void main(String... args) {

    }
    @Test
    public void blobRead() throws IOException {
        Blob blob = new Blob(Utils.join("./testing/bob.txt"));
        File test = Utils.join("./testing/bob2.txt");
        Utils.writeObject(test, blob);
        test.createNewFile();
        File test2 = Utils.join("./testing/bob2.txt");
        Blob blob2 = Utils.readObject(test2, Blob.class);
        System.out.println(blob2.sha());

    }
}

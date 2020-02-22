import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author your name here
 */
public class TrReader extends Reader {
    /**
     * A new TrReader that produces the stream of characters produced
     * by STR, converting all characters that occur in FROM to the
     * corresponding characters in TO.  That is, change occurrences of
     * FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     * in STR unchanged.  FROM and TO must have the same length.
     *
     */

    public TrReader(Reader str, String from, String to) {
        // TODO: YOUR CODE
        assert(from.length() == to.length());
        _str = str;
        _from = from;
        _to = to;
    }


    /* TODO: IMPLEMENT ANY MISSING ABSTRACT METHODS HERE
     * NOTE: Until you fill in the necessary methods, the compiler will
     *       reject this file, saying that you must declare TrReader
     *       abstract. Don't do that; define the right methods instead!
     */
    public int read(char[] cbuf, int off, int len) throws IOException { //from txt file
        int buffed = _str.read(cbuf);
        for (int i = 0; i < cbuf.length; i += 1) {
            if (_from.indexOf(cbuf[i]) > -1) {
                cbuf[i] = _to.charAt(_from.indexOf(cbuf[i]));
            }
        }
        return buffed;
    }

    public void close() {

    }
    private Reader _str;
    private String _from;
    private String _to;
}
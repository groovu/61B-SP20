/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        // FIXME: Add your code here.
        this.matched = match;
        num = input.colNameToIndex(colName);

    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        if (_next.getValue(num).contentEquals(matched)) {
            return true;
        } else {
            return false;
        }
    }

    // FIXME: Add instance variables?
    private int num;
    private String matched;
}

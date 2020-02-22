/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        // FIXME: Add your code here.
        num = input.colNameToIndex(colName);
        compare = ref;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        if (0 < _next.getValue(num).compareTo(compare)) {
            return true;
        } else {
            return false;
        }
    }

    // FIXME: Add instance variables?
    private int num;
    private String compare;
}

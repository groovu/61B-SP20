/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        // FIXME: Add your code here.
        num = input.colNameToIndex(colName);
        subString = subStr;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        if (_next.getValue(num).contains(subString) == true) {
            return true;
        } else {
        return false;
    }
    }

    // FIXME: Add instance variables?
    int num;
    String subString;
}

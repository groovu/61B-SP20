/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        this.num1 = input.colNameToIndex(colName1);
        this.num2 = input.colNameToIndex(colName2);
        // FIXME: Add your code here.
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        if (_next.getValue(num1).equals(_next.getValue(num2)) == true) {
            return true;
        } else {
            return false;
        }
    }

    // FIXME: Add instance variables?
    private int num1;
    private int num2;
}

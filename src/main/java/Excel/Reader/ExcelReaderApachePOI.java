package Excel.Reader;

import Excel.ExcelReader;

import java.util.List;

/**
 * Created by alco on 11/08/2015.
 */
public class ExcelReaderApachePOI implements ExcelReader {
    @Override
    public List<String> getAllSheetName() {
        return null;
    }

    @Override
    public List<String> getAllTitle(String sheetName) {
        return null;
    }

    @Override
    public int getTitlePos(String sheetName, String title) {
        return 0;
    }

    @Override
    public List<String> getColumn(String sheetName, int column) {
        return null;
    }

    @Override
    public List<String> getColumnUpper(String sheetName, int column) {
        return null;
    }
}

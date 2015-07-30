package Excel;

import java.util.List;

/**
 * Created by alco on 06/07/2015.
 */
public interface ExcelReader {
    List<String> getAllSheetName();
    List<String> getAllTitle(String sheetName);
    int getTitlePos(String sheetName, String title);
    List<String> getColumn(String sheetName, int column);

    List<String> getRow(String sheetName, int row);

    String getColumnType(String sheetName, int column, int row);
    List<String> getColumnUpper(String sheetName, int column);

    int getMaxSizeColumn(String sheetname, int column);

    int getTotalSizeColumn(String tablename, int columnPos);

    long getMinValueColumn(String tablename, int columnPos);

    long getMaxValueColumn(String tablename, int columnPos);
}

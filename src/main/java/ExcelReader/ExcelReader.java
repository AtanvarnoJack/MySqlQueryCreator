package ExcelReader;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by alco on 06/07/2015.
 */
public interface ExcelReader {
    Workbook takeReader(String workbook) throws IOException, BiffException;
    Workbook takeReader(File file) throws IOException, BiffException;
    Sheet getSheetInt(int sheet);
    Sheet getSheetName(String trigger);
    List<String> getAllSheetName();
    List<String> getAllTitle(String sheetName);
    int getTitlePos(String sheetName, String title);
    List<String> getColumn(String sheetName, int column);
}

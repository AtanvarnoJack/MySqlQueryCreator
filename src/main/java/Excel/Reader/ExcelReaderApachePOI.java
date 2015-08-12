package Excel.Reader;

import Excel.ExcelReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 11/08/2015.
 */
public class ExcelReaderApachePOI implements ExcelReader {
    private final static int TITLE_POSITION = 0;
    private Workbook workbook;

    public ExcelReaderApachePOI(String path) throws IOException {
        this.workbook = takeReaderHSSF(path);
    }

    private Workbook takeReaderXSSF(String path) throws IOException {
        return new XSSFWorkbook(new FileInputStream(path));
    }

    private Workbook takeReaderHSSF(String path) throws IOException {
        return new HSSFWorkbook(new FileInputStream(path));
    }

    private Sheet getSheetName(String sheetName) throws IllegalArgumentException {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("No Sheet have this Name!");
        }
        return sheet;
    }

    @Override
    public List<String> getAllSheetName() {
        List<String> listSheetName = new ArrayList<String>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            listSheetName.add(workbook.getSheetAt(i).getSheetName());
        }
        return listSheetName;
    }

    @Override
    public List<String> getAllTitle(String sheetName) {
        Sheet sheet = getSheetName(sheetName);
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            try {
                titleList.add(sheet.getRow(TITLE_POSITION).getCell(i).toString().trim().toUpperCase());
            } catch (NullPointerException NPE) {
                return titleList;
            }
        }
        return titleList;
    }

    @Override
    public int getTitlePos(String sheetName, String title) throws IllegalArgumentException {
        Sheet sheet = getSheetName(sheetName);
        int count = -1;
        List<String> allTitle = getAllTitle(sheetName);
        for (int i = 0; i < allTitle.size(); i++) {
            if (allTitle.get(i).equals(title)) {
                count = i;
            }
        }
        if (count == -1) {
            throw new IllegalArgumentException("No Title have this Name!");
        }
        return count;
    }

    @Override
    public List<String> getColumn(String sheetName, int column) {
        Sheet sheet = workbook.getSheet(sheetName);
        List<String> cellList = new ArrayList<>();
        for (int i = 2; i < sheet.getLastRowNum(); i++) {
            cellList.add(sheet.getRow(i).getCell(column).toString().trim());
        }
        return cellList;
    }

    @Override
    public List<String> getRow(String sheetName, int row) {
        return null;
    }

    @Override
    public String getColumnType(String sheetName, int column) {
        return null;
    }

    @Override
    public List<String> getColumnUpper(String sheetName, int column) {
        Sheet sheet = workbook.getSheet(sheetName);
        List<String> cellList = new ArrayList<>();
        for (int i = 2; i < sheet.getLastRowNum(); i++) {
            cellList.add(sheet.getRow(i).getCell(column).toString().trim().toUpperCase());
        }
        return cellList;
    }
}

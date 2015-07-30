package Excel.Reader;

import Excel.ExcelReader;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 06/07/2015.
 * Excel contain method to read the Excel File For SqlCreator App.
 */
public class ExcelReaderImpl implements ExcelReader {
    private Workbook workbook;
    public ExcelReaderImpl(String path) throws IOException, BiffException {
        this.workbook = takeReader(path);
    }

    public ExcelReaderImpl(File file) throws IOException, BiffException {
        this.workbook = takeReader(file);
    }

    private Workbook takeReader(String path) throws IOException, BiffException {
       return Workbook.getWorkbook(new File(path));
    }

    private Workbook takeReader(File file) throws IOException, BiffException {
        return Workbook.getWorkbook(file);
    }

    private Sheet getSheetInt(int sheet) {
        return workbook.getSheet(sheet);
    }

    private Sheet getSheetName(String trigger) throws IllegalArgumentException {
        Sheet sheet = workbook.getSheet(trigger);
        if (sheet == null){
            throw new IllegalArgumentException("No Sheet have this Name!");
        }
        return sheet;
    }

    @Override
    public List<String> getAllSheetName() {
        List<String> sheetNameList = new ArrayList<String>();
        int maxSheet = workbook.getNumberOfSheets();
        for (int i = 0; i < maxSheet; i++) {
            sheetNameList.add(workbook.getSheet(i).getName());
        }
        return sheetNameList;
    }

    @Override
    public List<String> getAllTitle(String sheetName) {
        List<String> allTitle = new ArrayList<String>();
        Sheet sheet = getSheetName(sheetName);
        int i = 0;
        while (i < sheet.getColumns()) {
            allTitle.add(sheet.getCell(i, 0).getContents().trim().toUpperCase());
            i++;
        }
        return allTitle;
    }

    @Override
    public int getTitlePos(String sheetName, String title) throws IllegalArgumentException {
        List<String> allTitle = getAllTitle(sheetName);
        int count = -1;
        for (int i = 0; i < allTitle.size(); i++) {
            if (allTitle.get(i).equals(title)) {
                return i;
            }
        }
        if (count == -1) {
            throw new IllegalArgumentException("No Title have this Name!");
        }
        return count;
    }

    @Override
    public List<String> getColumn(String sheetName, int column) {
        List<String> valueList = new ArrayList<String>();
        Sheet sheet = workbook.getSheet(sheetName);
        int numberRows = sheet.getRows();
        for (int i = 1; i < numberRows; i++) {
            valueList.add(sheet.getCell(column, i).getContents().trim());
        }
        return valueList;
    }

    @Override
    public List<String> getRow(String sheetName, int row) {
        List<String> valueList = new ArrayList<String>();
        Sheet sheet = workbook.getSheet(sheetName);
        int numberColumns = sheet.getColumns();
        for (int i = 1; i < numberColumns; i++) {
            if (sheet.getCell(i, row).getType().toString() != "Number")
                valueList.add("'" + sheet.getCell(i, row).getContents().trim() + "'");
            else
                valueList.add(sheet.getCell(i, row).getContents().trim());
        }
        return valueList;
    }

    @Override
    public String getColumnType(String sheetName, int column, int row) {
        Sheet sheet = workbook.getSheet(sheetName);
        CellType columnType = sheet.getCell(column, row).getType();
        if (columnType == CellType.LABEL || (row == sheet.getRows() - 1)) {
            return columnType.toString();
        }
        return getColumnType(sheetName, column, row + 1);
    }

    @Override
    public List<String> getColumnUpper(String sheetName, int column) {
        List<String> valueList = new ArrayList<String>();
        Sheet sheet = workbook.getSheet(sheetName);
        int numberRows = sheet.getRows();
        for (int i = 1; i < numberRows; i++) {
            valueList.add(sheet.getCell(column, i).getContents().trim().toUpperCase());
        }
        return valueList;
    }

    @Override
    public int getMaxSizeColumn(String sheetname, int column) {
        Sheet sheet = workbook.getSheet(sheetname);
        List<String> columnList = getColumn(sheetname, column);
        int numberRows = sheet.getRows();
        int maxLength = 0;
        for (int i = 1; i < numberRows; i++) {
            int dataLength = sheet.getCell(column, i).getContents().trim().length();
            maxLength = (maxLength > dataLength) ? maxLength : dataLength;
        }
        return maxLength;
    }

    @Override
    public int getTotalSizeColumn(String sheetname, int column) {
        Sheet sheet = workbook.getSheet(sheetname);
        List<String> columnList = getColumn(sheetname, column);
        int numberRows = sheet.getRows();
        int size = 0;
        for (int i = 1; i < numberRows; i++) {
            size += sheet.getCell(column, i).getContents().trim().length();
        }
        return size;
    }

    @Override
    public long getMinValueColumn(String tablename, int columnPos) {
        Sheet sheet = workbook.getSheet(tablename);
        List<String> columnList = getColumn(tablename, columnPos);
        List<Long> convertedList = new ArrayList<>();
        for (String val : columnList) {
            convertedList.add(Long.valueOf(val));
        }
        convertedList.sort(null);
        return convertedList.get(0);
    }

    @Override
    public long getMaxValueColumn(String tablename, int columnPos) {
        Sheet sheet = workbook.getSheet(tablename);
        List<String> columnList = getColumn(tablename, columnPos);
        List<Long> convertedList = new ArrayList<>();
        for (String val : columnList) {
            convertedList.add(Long.valueOf(val));
        }
        convertedList.sort(null);
        return convertedList.get(columnList.size() - 1);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExcelReaderImpl that = (ExcelReaderImpl) o;

        if (workbook != null ? !workbook.equals(that.workbook) : that.workbook != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return workbook != null ? workbook.hashCode() : 0;
    }

    public enum ExcelColumnType {Number, Date, Label}
}

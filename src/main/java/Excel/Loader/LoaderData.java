package Excel.Loader;

import Excel.ExcelReader;
import Excel.Reader.ExcelReaderJXLApi;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by CDEF on 28/07/2015.
 */
public class LoaderData {
    private ExcelReader excelReader;

    public LoaderData(String filename) {
        File file = new File(filename);
        try {
            excelReader = new ExcelReaderJXLApi(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTablesName() {
        return excelReader.getAllSheetName();
    }

    public List<String> getColumnsName(String sheetname) {
        return excelReader.getAllTitle(sheetname);
    }

    public List<String> getColumnData(String sheetname, int colIndex) {
        return excelReader.getColumn(sheetname, colIndex);
    }

    public String createTable(String tablename) {
        if (!getTablesName().contains(tablename))
            throw new IllegalArgumentException("Table " + tablename + " does not exist!");

        int nbCol = 0;
        if ((nbCol = getValidColumns(tablename)) <= 0)
            throw new IllegalArgumentException("Columns description for table " + tablename + " is not acceptable!");

        String request = "CREATE TABLE IF NOT EXISTS `" + tablename + "` (\n";
        for (int i = 0; i < nbCol; i++) {
            String col = getColumnsName(tablename).get(i);
            String columnType = guessType(tablename, col);
            request += "    `" + col + "` " + columnType;
            request += (i != nbCol - 1) ? ",\n" : "\n";
        }
        request += ");";
        return request;
    }

    private int getValidColumns(String tablename) {
        List<String> columnsList = getColumnsName(tablename);
        int valid = 0;
        for (String col : columnsList) {
            if (columnsList.indexOf(col) == columnsList.lastIndexOf(col)) valid++;
            else return -1;
        }
        return valid;
    }

    public String guessType(String tablename, String columnName) {
        String columnType = excelReader.getColumnType(tablename, excelReader.getTitlePos(tablename, columnName));
        switch (columnType) {
            case "Label":
                return "TEXT";
            case "Number":
                return "INTEGER";
            case "Date":
                return "DATETIME";
        }
        return "TEXT";
    }

    // TODO : INSERT INTO TABLE
    public String insertIntoTable(String tablename) {
        String request = "INSERT INTO TABLE " + tablename + " VALUES (\n";
        for (int i = 1; i < excelReader.getColumn(tablename, 1).size(); i++) {
            request += "    " + excelReader.getRow(tablename, i) + "\n";
        }
        request += ");";
        return request;
    }
}

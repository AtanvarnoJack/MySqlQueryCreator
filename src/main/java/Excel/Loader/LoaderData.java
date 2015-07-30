package Excel.Loader;

import Excel.ExcelReader;
import Excel.Reader.ExcelReaderImpl;
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
            excelReader = new ExcelReaderImpl(file);
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
        int columnPos = excelReader.getTitlePos(tablename, columnName);
        String columnType = excelReader.getColumnType(tablename, columnPos, 1);
        return getAccurateColumnType(tablename, columnPos, columnType);
    }

    private String getAccurateColumnType(String tablename, int columnPos, String columnType) {
        //return columnType;
        /**/
        switch (columnType) {
            case "Label":
                int maxSize = excelReader.getMaxSizeColumn(tablename, columnPos);
                if (maxSize < 255)
                    return "CHAR(" + maxSize + ")";
                else if (excelReader.getTotalSizeColumn(tablename, columnPos) > 65535)
                    return "TEXT";
                else
                    return "VARCHAR(" + maxSize + ")";

            case "Number":
                long minValue = excelReader.getMinValueColumn(tablename, columnPos);
                long maxValue = excelReader.getMaxValueColumn(tablename, columnPos);
                if (minValue >= 0) {
                    if (maxValue <= 255) return "TINYINT UNSIGNED";
                    if (maxValue <= 65535) return "SMALLINT UNSIGNED";
                    if (maxValue <= 16777215) return "MEDIUMINT UNSIGNED";
                    if (maxValue <= 4294967295L) return "INT UNSIGNED";
                    else return "BIGINT UNSIGNED";
                }
                if (minValue >= -128 && maxValue <= 127) return "TINYINT";
                if (minValue >= -32768 && maxValue <= 32767) return "SMALLINT";
                if (minValue >= -8388608 && maxValue <= 8388607) return "MEDIUMINT";
                if (minValue >= -2147483648 && maxValue <= 2147483647) return "INT";
                else return "BIGINT";

            case "Date":
                return "DATETIME";
        }
        return "TEXT";
        /**/
    }

    // TODO : INSERT INTO TABLE
    public String insertIntoTable(String tablename) {
        String request = "INSERT INTO TABLE `" + tablename + "` VALUES\n";
        for (int i = 1; i < excelReader.getColumn(tablename, 1).size(); i++) {
            String values = String.valueOf(excelReader.getRow(tablename, i));
            values = values.substring(1, values.length() - 1);
            request += "    (" + values + ")";
            request += (i == excelReader.getColumn(tablename, 1).size() - 1) ? ";" : ",\n";
        }
        return request;
    }
}

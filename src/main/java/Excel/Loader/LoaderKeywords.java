package Excel.Loader;

import Excel.ExcelReader;
import Excel.Reader.ExcelReaderJXLApi;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 06/07/2015.
 * LoaderKeywords load Excel in List.
 */
public class LoaderKeywords {
    private static final String SHEET_NAME = "request";
    private static final String TITLE_NAME_TABLE = "TABLE";
    private static final String TITLE_NAME_CHAMPS = "FIELD";
    private static final String TITLE_NAME_CONDITION = "CONDITION";
    private static final String TITLE_TYPE = "TYPE";
    private List<String> tableList = new ArrayList<String>();
    private List<String> champsList = new ArrayList<String>();
    private List<String> conditionList = new ArrayList<String>();
    private List<String> typeList = new ArrayList<String>();

    public LoaderKeywords() {
    }

    public LoaderKeywords(List<String> tableList, List<String> champsList, List<String> conditionList) {
        this.tableList = tableList;
        this.champsList = champsList;
        this.conditionList = conditionList;
    }

    public void loadList(){
        File file = new File("ExampleFile.xls");
        try {
            ExcelReader excelReader = new ExcelReaderJXLApi(file);
            List<String> titleList = excelReader.getAllTitle(SHEET_NAME);
            for (String title : titleList) {
                switch (title) {
                    case TITLE_NAME_TABLE:
                        this.tableList.addAll(excelReader.getColumn(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_NAME_TABLE)));
                        break;
                    case TITLE_NAME_CHAMPS:
                        this.champsList.addAll(excelReader.getColumn(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_NAME_CHAMPS)));
                        break;
                    case TITLE_NAME_CONDITION:
                        this.conditionList.addAll(excelReader.getColumn(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_NAME_CONDITION)));
                        break;
                    case TITLE_TYPE:
                        this.typeList.addAll(excelReader.getColumn(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_TYPE)));
                        break;
                    default:
                        throw new IllegalArgumentException("champs hors configuration!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public void loadList(File file) throws IOException, BiffException, IllegalArgumentException {
        ExcelReader excelReader = new ExcelReaderJXLApi(file);
        List<String> titleList = excelReader.getAllTitle(SHEET_NAME);
        for (String title : titleList) {
            switch (title) {
                case TITLE_NAME_TABLE:
                    this.tableList.addAll(excelReader.getColumn(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_NAME_TABLE)));
                    break;
                case TITLE_NAME_CHAMPS:
                    this.champsList.addAll(excelReader.getColumn(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_NAME_CHAMPS)));
                    break;
                case TITLE_NAME_CONDITION:
                    this.conditionList.addAll(excelReader.getColumn(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_NAME_CONDITION)));
                    break;
                case TITLE_TYPE:
                    this.typeList.addAll(excelReader.getColumnUpper(SHEET_NAME, excelReader.getTitlePos(SHEET_NAME, TITLE_TYPE)));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field '" + title + "'");
            }
        }
    }

    public List<String> getTableList() {
        return tableList;
    }
    public void setTableList(List<String> tableList) {
        this.tableList = tableList;
    }

    public List<String> getChampsList() {
        return champsList;
    }
    public void setChampsList(List<String> champsList) {
        this.champsList = champsList;
    }

    public List<String> getConditionList() {
        return conditionList;
    }
    public void setConditionList(List<String> conditionList) {
        this.conditionList = conditionList;
    }

    public List<String> getTypeList() {
        return typeList;
    }
    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoaderKeywords that = (LoaderKeywords) o;

        if (champsList != null ? !champsList.equals(that.champsList) : that.champsList != null) return false;
        if (conditionList != null ? !conditionList.equals(that.conditionList) : that.conditionList != null)
            return false;
        if (tableList != null ? !tableList.equals(that.tableList) : that.tableList != null) return false;
        if (typeList != null ? !typeList.equals(that.typeList) : that.typeList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableList != null ? tableList.hashCode() : 0;
        result = 31 * result + (champsList != null ? champsList.hashCode() : 0);
        result = 31 * result + (conditionList != null ? conditionList.hashCode() : 0);
        result = 31 * result + (typeList != null ? typeList.hashCode() : 0);
        return result;
    }
}

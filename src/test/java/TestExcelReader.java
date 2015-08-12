/**
 * Created by alco on 06/07/2015.
 */

public class TestExcelReader {
    private static final String OCDE_FILE = "ExampleFile.xls";
/*
    @Test
    public void shouldGetRateFile(){
        try {
            File file = new File(OCDE_FILE);
            Excel excelReader = new ExcelReaderImpl(file);
            Workbook found = excelReader.takeReader(file);
            assertThat(found).isNotNull();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetASheetByInt(){
        File file = new File(OCDE_FILE);
        try {
            Excel excelReader = new ExcelReaderImpl(file);
            Sheet sheet = excelReader.getSheetInt(0);
            assertThat(sheet.getName()).isEqualTo("request");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetASheetByName(){
        File file = new File(OCDE_FILE);
        try {
            Excel excelReader = new ExcelReaderImpl(file);
            Sheet sheet = excelReader.getSheetName("request");
            assertThat(sheet.getName()).isEqualTo("request");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAllSheetName(){
        File file = new File(OCDE_FILE);
        try {
            Excel excelReader = new ExcelReaderImpl(file);
            List<String> sheetList = excelReader.getAllSheetName();
            assertThat(sheetList).containsOnly("request","Feuil2","Feuil3");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAllTitleForASheet(){
        File file = new File(OCDE_FILE);
        try {
            Excel excelReader = new ExcelReaderImpl(file);
            List<String> titleList = excelReader.getAllTitle("request");
            assertThat(titleList).containsOnly("TYPE","TABLE","CHAMPS","CONDITION");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetColumnOfATitle(){
        File file = new File(OCDE_FILE);
        try {
            Excel excelReader = new ExcelReaderImpl(file);
            int column = excelReader.getTitlePos("request","CHAMPS");
            assertThat(column).isEqualTo(2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAColumn(){
        File file = new File(OCDE_FILE);
        final String sheetName = "request";
        final String titleName = "CHAMPS";
        try {
            Excel excelReader = new ExcelReaderImpl(file);
            List<String> valueList = excelReader.getColumn(sheetName,excelReader.getTitlePos(sheetName,titleName));
            assertThat(valueList).contains("RED_CONTRACT_TYPE","INCIDENT_EMAIL2","ESCALADE_EMAIL2");
            assertThat(valueList).doesNotContain("",titleName);
            assertThat(valueList.size()).isEqualTo(70);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    /****
     * Loader
     */
/*
    @Test
    public void shouldLoadAllData(){
        LoaderKeywords loader = new LoaderKeywords();
        loader.loadListJXLApi();

        List<String> tableListFound = loader.getTableList();
        List<String> champsListFound = loader.getChampsList();
        List<String> conditionListFound = loader.getConditionList();
        List<String> typeListFound = loader.getTypeList();

        assertThat(tableListFound).contains("CLIENT_RED", "CLIENT_V3_NEW", "EQUIPMENTS_BKP");
        assertThat(tableListFound).doesNotContain("");
        assertThat(champsListFound).contains("SDM_EMAIL", "RED_Under_supervision", "INCIDENT_EMAIL2");
        assertThat(champsListFound).doesNotContain("");
        assertThat(conditionListFound).contains("'BASIC' or 'ADVANCED' or 'EXTENDED'", "'%@%'", "'Y' or 'N'");
        assertThat(conditionListFound).doesNotContain("");
        assertThat(typeListFound).contains("Request");
        assertThat(typeListFound).doesNotContain("");
        assertThat(tableListFound.size()).isEqualTo(champsListFound.size()).isEqualTo(conditionListFound.size()).isEqualTo(typeListFound.size());
    }

    /****
     * File output
     */

/*    @Test
    public void canWriteStringOnFile(){
        FileSql fileSql = new FileSql();
        fileSql.writeStringInSqlFile("boooooooooo2");
    }*/
}

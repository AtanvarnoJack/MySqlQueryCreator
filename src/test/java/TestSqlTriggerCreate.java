/**
 * Created by alco on 03/07/2015.
 */

import ExcelReader.LoaderExcel;
import Reference.Const;
import Request.SqlTrigger;
import org.testng.annotations.Test;

public class TestSqlTriggerCreate {

    @Test
    public void CanDoList(){
        SqlTrigger sqlTrigger = new SqlTrigger();
        Const constente = new Const();

        String chaine = sqlTrigger.toStringGetAll(constente.getTableNameList(), constente.getChampNameList(), constente.getConditionList(), 5.1);
        System.out.println("getChaine = " + chaine);
    }

    @Test
    public void canFindArgsOnExcel() {
        LoaderExcel loaderExcel = new LoaderExcel();
        loaderExcel.loadList();
        SqlTrigger sqlTrigger = new SqlTrigger();

        String chaine = sqlTrigger.toStringGetAll(loaderExcel.getTableList(), loaderExcel.getChampsList(), loaderExcel.getConditionList(), 5.6);
        System.out.println("chaine = " + chaine);
    }
}

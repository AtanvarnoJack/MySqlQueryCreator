/**
 * Created by alco on 03/07/2015.
 */

import Excel.Loader.LoaderKeywords;
import Reference.Const;
import Request.MySQL.Query.SqlTrigger;
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
        LoaderKeywords loaderKeywords = new LoaderKeywords();
        loaderKeywords.loadList();
        SqlTrigger sqlTrigger = new SqlTrigger();

        String chaine = sqlTrigger.toStringGetAll(loaderKeywords.getTableList(), loaderKeywords.getChampsList(), loaderKeywords.getConditionList(), 5.6);
        System.out.println("chaine = " + chaine);
    }
}

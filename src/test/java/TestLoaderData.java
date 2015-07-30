import Excel.Loader.LoaderData;
import org.testng.annotations.Test;

/**
 * Created by CDEF on 28/07/2015.
 */
public class TestLoaderData {

    @Test
    public void testCreateTable() {
        LoaderData loaderData = new LoaderData("File/ClasseurTest.xls");
        System.out.println(loaderData.createTable("Feuil1"));
    }

    @Test
    public void testInsertIntoTable() {
        LoaderData loaderData = new LoaderData("File/ClasseurTest.xls");
        System.out.println(loaderData.insertIntoTable("Feuil1"));
    }
}

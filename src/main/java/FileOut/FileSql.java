package FileOut;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by alco on 06/07/2015.
 */
public class FileSql {
    private static File urlFile;

    public FileSql(File fileDirectory) {
        urlFile = fileDirectory;
    }

    public void writeStringInSqlFile(String requete){
        File file = urlFile;
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!file.canRead()){
            file.setReadable(true);
        }
        if(!file.canWrite()){
            file.setWritable(true);
        }

        try {
            FileUtils.writeStringToFile(file, requete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

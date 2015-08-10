package Base.SQLite.SQLJet;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alco on 10/08/2015.
 */
public class OpenHelperBddParamsSQLJet {
    private final static String DB_REPOSITORIES = "Settings/";
    private final static String DB_NAME = "ParamsBaseConnection";

    private final static String TABLE_PARAMS = "Params";
    private final static String TABLE_ROW_URL = "URL";
    private static final String TABLE_ROW_USER = "User";
    private static final String TABLE_ROW_PASSWORD = "Password";
    private final static String INDEX_Params = "Index_Params";

    private static File dbFile;

    /**
     * getSqlJetDb
     *
     * @return SqlJetDb
     * @throws org.tmatesoft.sqljet.core.SqlJetException
     */
    public static SqlJetDb getSqlJetDb() throws SqlJetException {
        dbFile = new File(DB_REPOSITORIES + DB_NAME);
        SqlJetDb db = SqlJetDb.open(dbFile, true);
        return db;
    }

    /**
     * printRecords
     *
     * @param cursor
     * @return HashMap<String, List<String>>
     * @throws SqlJetException
     */
    private static HashMap<String, List<String>> printRecords(ISqlJetCursor cursor) throws SqlJetException {
        HashMap<String, List<String>> allRecords = new HashMap<>();
        try {
            if (!cursor.eof()) {
                do {
                    List<String> champsList = new ArrayList<>();
                    champsList.add(cursor.getString(TABLE_ROW_USER));
                    champsList.add(cursor.getString(TABLE_ROW_PASSWORD));
                    allRecords.put(cursor.getString(TABLE_ROW_URL), champsList);
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
        return allRecords;
    }

    /**
     * createNewBase
     *
     * @throws SqlJetException
     */
    public void createNewBase() throws SqlJetException {
        dbFile = new File(DB_REPOSITORIES + DB_NAME);
        dbFile.delete();

        SqlJetDb db = SqlJetDb.open(dbFile, true);
        db.getOptions().setAutovacuum(true);
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            db.getOptions().setUserVersion(1);
        } finally {
            db.commit();
            db.close();
        }
    }

    /**
     * createTables
     *
     * @param db
     * @throws SqlJetException
     */
    public void createTables(SqlJetDb db) throws SqlJetException {
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            db.createTable("CREATE TABLE " + TABLE_PARAMS + " (" + TABLE_ROW_URL + " TEXT NOT NULL PRIMARY KEY, " + TABLE_ROW_USER + " TEXT NOT NULL," + TABLE_ROW_PASSWORD + " TEXT NOT NULL)\n");
            db.createIndex("CREATE INDEX " + INDEX_Params + " ON " + TABLE_PARAMS + "(" + TABLE_ROW_URL + "," + TABLE_ROW_USER + "," + TABLE_ROW_PASSWORD + ")\n");
        } finally {
            db.commit();
            db.close();
        }
    }

    /**
     * addRecordBase
     *
     * @param db
     * @param url
     * @param user
     * @throws SqlJetException
     */
    public void addRecordBase(SqlJetDb db, String url, String user, String password) throws SqlJetException {
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            ISqlJetTable table = db.getTable(TABLE_PARAMS);
            table.insert(url, user, password);
        } finally {
            db.commit();
            db.close();
        }
    }

    /**
     * getRecords
     *
     * @param db
     * @param url
     * @return String
     * @throws SqlJetException
     */
    public String getRecords(SqlJetDb db, String url) throws SqlJetException {
        ISqlJetTable table = db.getTable(TABLE_PARAMS);
        db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        String found;
        try {
            found = printRecords(table.open(), url);
        } finally {
            db.commit();
            db.close();
        }
        return found;
    }

    /**
     * getAllRecords
     *
     * @param db
     * @return HashMap<String, List<String>>
     * @throws SqlJetException
     */
    public HashMap<String, List<String>> getAllRecords(SqlJetDb db) throws SqlJetException {
        ISqlJetTable table = db.getTable(TABLE_PARAMS);
        db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        HashMap<String, List<String>> found;
        try {
            found = printRecords(table.open());
        } finally {
            db.commit();
            db.close();
        }
        return found;
    }

    /**
     * printRecords
     *
     * @param cursor
     * @param key
     * @return String
     * @throws SqlJetException
     */
    private String printRecords(ISqlJetCursor cursor, String key) throws SqlJetException {
        String record = null;
        try {
            if (!cursor.eof()) {
                do {
                    if (cursor.getString(TABLE_ROW_URL).equals(key)) {
                        record = cursor.getString(TABLE_ROW_USER);
                        record = record + ":" + cursor.getString(TABLE_ROW_PASSWORD);
                    }
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
        return record;
    }
}

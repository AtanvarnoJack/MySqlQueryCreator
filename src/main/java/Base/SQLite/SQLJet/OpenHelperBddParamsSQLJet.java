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
 * OpenHelperBddParamsSQLJet class contains all SQLJet method for base Params
 */
public class OpenHelperBddParamsSQLJet {
    private final static String DB_REPOSITORIES = "settings/";
    private final static String DB_NAME = "ParamsBaseConnection";

    private final static String TABLE_PARAMS = "Params";
    private final static String TABLE_ROW_URL = "URL";
    private static final String TABLE_ROW_USER = "User";
    private static final String TABLE_ROW_PASSWORD = "Password";
    private final static String INDEX_Params = "Index_Params";

    /**
     * getSqlJetDb
     *
     * @return SqlJetDb
     * @throws org.tmatesoft.sqljet.core.SqlJetException
     */
    public static SqlJetDb getSqlJetDb() throws SqlJetException {
        File dbFile = new File(DB_REPOSITORIES + DB_NAME);
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
    protected void createNewBase() throws SqlJetException {
        File dbFile = new File(DB_REPOSITORIES + DB_NAME);
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
    protected void createTables(SqlJetDb db) throws SqlJetException {
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
    protected void addRecordBase(SqlJetDb db, String url, String user, String password) throws SqlJetException {
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
    protected String[] getRecords(SqlJetDb db, String url) throws SqlJetException {
        ISqlJetTable table = db.getTable(TABLE_PARAMS);
        db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        String[] found;
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
    protected HashMap<String, List<String>> getAllRecords(SqlJetDb db) throws SqlJetException {
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
    private String[] printRecords(ISqlJetCursor cursor, String key) throws SqlJetException {
        String[] record = new String[3];
        try {
            if (!cursor.eof()) {
                do {
                    if (cursor.getString(TABLE_ROW_URL).equals(key)) {
                        record[0] = cursor.getString(TABLE_ROW_URL);
                        record[1] = cursor.getString(TABLE_ROW_USER);
                        record[2] = cursor.getString(TABLE_ROW_PASSWORD);
                    }
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
        return record;
    }

    /**
     * updateRecords
     *
     * @param db
     * @param url
     * @param user
     * @param Password
     * @throws SqlJetException
     */
    protected void updateRecords(SqlJetDb db, String url, String user, String Password) throws SqlJetException {
        ISqlJetTable table = db.getTable(TABLE_PARAMS);
        db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        try {
            update(table.open(), url, user, Password, table);
        } finally {
            db.commit();
            db.close();
        }
    }

    /**
     * update
     * @param open
     * @param url
     * @param user
     * @param Password
     * @param table
     * @throws SqlJetException
     */
    private void update(ISqlJetCursor open, String url, String user, String Password, ISqlJetTable table) throws SqlJetException {
        ISqlJetCursor cursor = open;
        try {
            if (!cursor.eof()) {
                do {
                    if (cursor.getString(TABLE_ROW_URL).equals(url)) {
                        cursor.update(url, user, Password);
                    }
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * deleteRecords
     * @param db
     * @param url
     * @throws SqlJetException
     */
    protected void deleteRecords(SqlJetDb db, String url) throws SqlJetException {
        ISqlJetTable table = db.getTable(TABLE_PARAMS);
        db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        try {
            delete(table.open(), url);
        } finally {
            db.commit();
            db.close();
        }
    }

    /**
     * delete
     * @param open
     * @param url
     * @throws SqlJetException
     */
    private void delete(ISqlJetCursor open, String url) throws SqlJetException {
        ISqlJetCursor cursor = open;
        try {
            if (!cursor.eof()) {
                do {
                    if (cursor.getString(TABLE_ROW_URL).equals(url)) {
                        cursor.delete();
                    }
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
    }
}

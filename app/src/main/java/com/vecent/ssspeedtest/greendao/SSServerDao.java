package com.vecent.ssspeedtest.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.vecent.ssspeedtest.dao.SSServer;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SSSERVER".
*/
public class SSServerDao extends AbstractDao<SSServer, Long> {

    public static final String TABLENAME = "SSSERVER";

    /**
     * Properties of entity SSServer.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ServerAddr = new Property(1, String.class, "serverAddr", false, "serverAddr");
        public final static Property ServerPort = new Property(2, int.class, "serverPort", false, "serverPort");
        public final static Property Password = new Property(3, String.class, "password", false, "password");
        public final static Property Method = new Property(4, String.class, "method", false, "method");
        public final static Property Score = new Property(5, int.class, "score", false, "score");
        public final static Property Grade = new Property(6, int.class, "grade", false, "grade");
    }


    public SSServerDao(DaoConfig config) {
        super(config);
    }
    
    public SSServerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SSSERVER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"serverAddr\" TEXT NOT NULL ," + // 1: serverAddr
                "\"serverPort\" INTEGER NOT NULL ," + // 2: serverPort
                "\"password\" TEXT NOT NULL ," + // 3: password
                "\"method\" TEXT NOT NULL ," + // 4: method
                "\"score\" INTEGER NOT NULL ," + // 5: score
                "\"grade\" INTEGER NOT NULL );"); // 6: grade
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SSSERVER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SSServer entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getServerAddr());
        stmt.bindLong(3, entity.getServerPort());
        stmt.bindString(4, entity.getPassword());
        stmt.bindString(5, entity.getMethod());
        stmt.bindLong(6, entity.getScore());
        stmt.bindLong(7, entity.getGrade());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SSServer entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getServerAddr());
        stmt.bindLong(3, entity.getServerPort());
        stmt.bindString(4, entity.getPassword());
        stmt.bindString(5, entity.getMethod());
        stmt.bindLong(6, entity.getScore());
        stmt.bindLong(7, entity.getGrade());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SSServer readEntity(Cursor cursor, int offset) {
        SSServer entity = new SSServer( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // serverAddr
            cursor.getInt(offset + 2), // serverPort
            cursor.getString(offset + 3), // password
            cursor.getString(offset + 4), // method
            cursor.getInt(offset + 5), // score
            cursor.getInt(offset + 6) // grade
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SSServer entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setServerAddr(cursor.getString(offset + 1));
        entity.setServerPort(cursor.getInt(offset + 2));
        entity.setPassword(cursor.getString(offset + 3));
        entity.setMethod(cursor.getString(offset + 4));
        entity.setScore(cursor.getInt(offset + 5));
        entity.setGrade(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SSServer entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SSServer entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SSServer entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

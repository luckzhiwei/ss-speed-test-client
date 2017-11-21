package com.vecent.ssspeedtest.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TOTAL_SPEED_TEST_RESULT".
*/
public class TotalSpeedTestResultDao extends AbstractDao<TotalSpeedTestResult, Long> {

    public static final String TABLENAME = "TOTAL_SPEED_TEST_RESULT";

    /**
     * Properties of entity TotalSpeedTestResult.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TotalTimeUsed = new Property(1, float.class, "totalTimeUsed", false, "totalTimeUsed");
        public final static Property WhiteAddrServerCount = new Property(2, int.class, "whiteAddrServerCount", false, "whiteAddrServerCount");
        public final static Property BlackAddrServerCount = new Property(3, int.class, "blackAddrServerCount", false, "blackAddrServerCount");
        public final static Property WhiteAddrConnectSuccesRate = new Property(4, float.class, "whiteAddrConnectSuccesRate", false, "whiteAddrConnectSuccesRate");
        public final static Property BlackAddrConnectSuccesRate = new Property(5, float.class, "blackAddrConnectSuccesRate", false, "blackAddrConnectSuccesRate");
        public final static Property TotalServerSize = new Property(6, int.class, "totalServerSize", false, "totalServerSize");
        public final static Property TotalByteSize = new Property(7, int.class, "totalByteSize", false, "totalByteSize");
        public final static Property SpeedWhiteAddrDownLoadAvg = new Property(8, float.class, "speedWhiteAddrDownLoadAvg", false, "speedWhiteAddrDownLoadAvg");
        public final static Property SpeedBlackAddrDownLoadAvg = new Property(9, float.class, "speedBlackAddrDownLoadAvg", false, "speedBlackAddrDownLoadAvg");
        public final static Property Server2TestAddr = new Property(10, String.class, "server2TestAddr", false, "server2TestAddr");
    }


    public TotalSpeedTestResultDao(DaoConfig config) {
        super(config);
    }
    
    public TotalSpeedTestResultDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TOTAL_SPEED_TEST_RESULT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"totalTimeUsed\" REAL NOT NULL ," + // 1: totalTimeUsed
                "\"whiteAddrServerCount\" INTEGER NOT NULL ," + // 2: whiteAddrServerCount
                "\"blackAddrServerCount\" INTEGER NOT NULL ," + // 3: blackAddrServerCount
                "\"whiteAddrConnectSuccesRate\" REAL NOT NULL ," + // 4: whiteAddrConnectSuccesRate
                "\"blackAddrConnectSuccesRate\" REAL NOT NULL ," + // 5: blackAddrConnectSuccesRate
                "\"totalServerSize\" INTEGER NOT NULL ," + // 6: totalServerSize
                "\"totalByteSize\" INTEGER NOT NULL ," + // 7: totalByteSize
                "\"speedWhiteAddrDownLoadAvg\" REAL NOT NULL ," + // 8: speedWhiteAddrDownLoadAvg
                "\"speedBlackAddrDownLoadAvg\" REAL NOT NULL ," + // 9: speedBlackAddrDownLoadAvg
                "\"server2TestAddr\" TEXT);"); // 10: server2TestAddr
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TOTAL_SPEED_TEST_RESULT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TotalSpeedTestResult entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getTotalTimeUsed());
        stmt.bindLong(3, entity.getWhiteAddrServerCount());
        stmt.bindLong(4, entity.getBlackAddrServerCount());
        stmt.bindDouble(5, entity.getWhiteAddrConnectSuccesRate());
        stmt.bindDouble(6, entity.getBlackAddrConnectSuccesRate());
        stmt.bindLong(7, entity.getTotalServerSize());
        stmt.bindLong(8, entity.getTotalByteSize());
        stmt.bindDouble(9, entity.getSpeedWhiteAddrDownLoadAvg());
        stmt.bindDouble(10, entity.getSpeedBlackAddrDownLoadAvg());
 
        String server2TestAddr = entity.getServer2TestAddr();
        if (server2TestAddr != null) {
            stmt.bindString(11, server2TestAddr);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TotalSpeedTestResult entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getTotalTimeUsed());
        stmt.bindLong(3, entity.getWhiteAddrServerCount());
        stmt.bindLong(4, entity.getBlackAddrServerCount());
        stmt.bindDouble(5, entity.getWhiteAddrConnectSuccesRate());
        stmt.bindDouble(6, entity.getBlackAddrConnectSuccesRate());
        stmt.bindLong(7, entity.getTotalServerSize());
        stmt.bindLong(8, entity.getTotalByteSize());
        stmt.bindDouble(9, entity.getSpeedWhiteAddrDownLoadAvg());
        stmt.bindDouble(10, entity.getSpeedBlackAddrDownLoadAvg());
 
        String server2TestAddr = entity.getServer2TestAddr();
        if (server2TestAddr != null) {
            stmt.bindString(11, server2TestAddr);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TotalSpeedTestResult readEntity(Cursor cursor, int offset) {
        TotalSpeedTestResult entity = new TotalSpeedTestResult( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getFloat(offset + 1), // totalTimeUsed
            cursor.getInt(offset + 2), // whiteAddrServerCount
            cursor.getInt(offset + 3), // blackAddrServerCount
            cursor.getFloat(offset + 4), // whiteAddrConnectSuccesRate
            cursor.getFloat(offset + 5), // blackAddrConnectSuccesRate
            cursor.getInt(offset + 6), // totalServerSize
            cursor.getInt(offset + 7), // totalByteSize
            cursor.getFloat(offset + 8), // speedWhiteAddrDownLoadAvg
            cursor.getFloat(offset + 9), // speedBlackAddrDownLoadAvg
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // server2TestAddr
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TotalSpeedTestResult entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTotalTimeUsed(cursor.getFloat(offset + 1));
        entity.setWhiteAddrServerCount(cursor.getInt(offset + 2));
        entity.setBlackAddrServerCount(cursor.getInt(offset + 3));
        entity.setWhiteAddrConnectSuccesRate(cursor.getFloat(offset + 4));
        entity.setBlackAddrConnectSuccesRate(cursor.getFloat(offset + 5));
        entity.setTotalServerSize(cursor.getInt(offset + 6));
        entity.setTotalByteSize(cursor.getInt(offset + 7));
        entity.setSpeedWhiteAddrDownLoadAvg(cursor.getFloat(offset + 8));
        entity.setSpeedBlackAddrDownLoadAvg(cursor.getFloat(offset + 9));
        entity.setServer2TestAddr(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TotalSpeedTestResult entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TotalSpeedTestResult entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TotalSpeedTestResult entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
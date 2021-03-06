package com.vecent.ssspeedtest.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.vecent.ssspeedtest.dao.SSServer;

import com.vecent.ssspeedtest.greendao.SSServerDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sSServerDaoConfig;

    private final SSServerDao sSServerDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sSServerDaoConfig = daoConfigMap.get(SSServerDao.class).clone();
        sSServerDaoConfig.initIdentityScope(type);

        sSServerDao = new SSServerDao(sSServerDaoConfig, this);

        registerDao(SSServer.class, sSServerDao);
    }
    
    public void clear() {
        sSServerDaoConfig.clearIdentityScope();
    }

    public SSServerDao getSSServerDao() {
        return sSServerDao;
    }

}

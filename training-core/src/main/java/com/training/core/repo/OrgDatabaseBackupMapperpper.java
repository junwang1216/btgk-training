package com.training.core.repo;

import com.training.core.repo.po.OrgDatabaseBackup;

public interface OrgDatabaseBackupMapperpper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrgDatabaseBackup record);

    OrgDatabaseBackup selectByPrimaryKey(Integer id);
}
package com.training.in.api;

import com.training.core.common.enums.LogTypeEnum;
import com.training.core.common.util.DateUtil;
import com.training.core.repo.po.OrgSystemLog;
import com.training.core.service.OrgSystemLogService;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by wangjun on 2018/2/1.
 */
public class WriteSystemlog {

    @Resource
    private OrgSystemLogService orgSystemLogService;

    public void log(int logType, int orgId, String ip, String mac) {
        OrgSystemLog orgSystemLog = new OrgSystemLog();

        orgSystemLog.setOrgId(orgId);
        orgSystemLog.setIp(ip);
        orgSystemLog.setMac(mac);

        orgSystemLog.setCreateTime(DateUtil.getNowDate());
        orgSystemLog.setLogType(logType);
        orgSystemLog.setLogNo(UUID.randomUUID().toString());
        orgSystemLog.setLogContent("[]");

        orgSystemLogService.addSystemLog(orgSystemLog);
    };
}

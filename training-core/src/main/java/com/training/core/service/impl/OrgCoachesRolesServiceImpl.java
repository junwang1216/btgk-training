package com.training.core.service.impl;

import com.training.core.repo.OrgCoachesRolesMapper;
import com.training.core.repo.po.OrgCoachesRoles;
import com.training.core.service.OrgCoachesRolesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgCoachesRolesServiceImpl implements OrgCoachesRolesService {

    @Resource
    private OrgCoachesRolesMapper orgCoachesRolesMapper;

    @Override
    public int addOrgCoachesRolesBatch(List<OrgCoachesRoles> orgCoachesRolesList) {
        return orgCoachesRolesMapper.insertBatch(orgCoachesRolesList);
    }

    @Override
    public List<OrgCoachesRoles> queryOrgCoachesRolesList(int coachId) {
        return orgCoachesRolesMapper.queryAllByCoachId(coachId);
    }

    @Override
    public int clearAllByCoachId(int coachId) {
        return orgCoachesRolesMapper.clearAllByCoachId(coachId);
    }

}



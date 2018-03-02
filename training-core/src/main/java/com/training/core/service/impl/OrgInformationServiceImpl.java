package com.training.core.service.impl;

import com.training.core.repo.OrgInformationMapper;
import com.training.core.repo.po.OrgInformation;
import com.training.core.service.OrgInformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrgInformationServiceImpl implements OrgInformationService {

    @Resource
    private OrgInformationMapper orgInformationMapper;

    @Override
    public OrgInformation selectByPrimaryKey(Integer id) {
        return orgInformationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(OrgInformation orgInformation) {
        return orgInformationMapper.updateByPrimaryKey(orgInformation);
    }

    @Override
    public int addOrgInformation(OrgInformation orgInformation) {
        return orgInformationMapper.insert(orgInformation);
    }
}



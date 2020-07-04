package com.example.demo_activity.test1.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo_activity.test1.model.HistoryVersion;
import com.example.demo_activity.test1.model.ReportDescTemplate;
import com.example.demo_activity.test1.dao.ReportDescTemplateMapper;
import com.example.demo_activity.test1.model.TemplateCombination;
import com.example.demo_activity.test1.service.IHistoryVersionService;
import com.example.demo_activity.test1.service.IReportDescTemplateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo_activity.test1.service.ITemplateCombinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 报告说明模板表 服务实现类
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@Service
public class ReportDescTemplateServiceImpl extends ServiceImpl<ReportDescTemplateMapper, ReportDescTemplate> implements IReportDescTemplateService {

    @Autowired
    private ITemplateCombinationService iTemplateCombinationService;

    @Autowired
    private IHistoryVersionService iHistoryVersionService;

    @Override
    public void historyVisionBackup() {
        List<ReportDescTemplate> reportDescTemplateList = this.selectList(new EntityWrapper<ReportDescTemplate>().in("template_status", "1,2"));
        List<HistoryVersion> list = new ArrayList<>();
        if(reportDescTemplateList!=null && reportDescTemplateList.size()>0){
            for (ReportDescTemplate reportDescTemplate:reportDescTemplateList) {
                HistoryVersion historyVersion = new HistoryVersion();
                historyVersion.setRelationId(reportDescTemplate.getId());
                historyVersion.setType(reportDescTemplate.getTemplateType());
                historyVersion.setUpdateUserName(reportDescTemplate.getUploadUserName());
                historyVersion.setCreateTime(new Date());
                historyVersion.setUpdateTime(new Date());
                historyVersion.setUpdateFileName(reportDescTemplate.getFileName());
                historyVersion.setUpdateFilePath(reportDescTemplate.getTemplateFilePath());
                historyVersion.setVersion(reportDescTemplate.getVersion());
                historyVersion.setStatus(1);
                list.add(historyVersion);
            }
        }
        List<TemplateCombination> templateCombinationList = iTemplateCombinationService.selectList(new EntityWrapper<TemplateCombination>().in("combination_status", "1,2"));
        if(templateCombinationList!=null && templateCombinationList.size()>0) {
            for (TemplateCombination templateCombination : templateCombinationList) {
                HistoryVersion historyVersion = new HistoryVersion();
                historyVersion.setRelationId(templateCombination.getId());
                historyVersion.setType(10);
                historyVersion.setUpdateUserName(templateCombination.getSetUsername());
                historyVersion.setCreateTime(new Date());
                historyVersion.setUpdateTime(new Date());
                historyVersion.setVersion(templateCombination.getVersion());
                historyVersion.setStatus(1);
                list.add(historyVersion);
            }
        }
        if(list.size()>0){
            iHistoryVersionService.insertBatch(list);
        }
    }
}

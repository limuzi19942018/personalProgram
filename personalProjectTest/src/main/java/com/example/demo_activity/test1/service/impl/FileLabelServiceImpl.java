package com.example.demo_activity.test1.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo_activity.test1.dao.FrameTemplateAssociationMapper;
import com.example.demo_activity.test1.model.FileLabel;
import com.example.demo_activity.test1.dao.FileLabelMapper;
import com.example.demo_activity.test1.model.FrameTemplateAssociation;
import com.example.demo_activity.test1.model.ReportDescTemplate;
import com.example.demo_activity.test1.model.TemplateCombination;
import com.example.demo_activity.test1.service.IFileLabelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo_activity.test1.service.IFrameTemplateAssociationService;
import com.example.demo_activity.test1.service.IReportDescTemplateService;
import com.example.demo_activity.test1.service.ITemplateCombinationService;
import com.example.demo_activity.test1.tips.SuccessTip;
import com.example.demo_activity.test1.utils.ObjectUtils;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件标签表 服务实现类
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@Service
public class FileLabelServiceImpl extends ServiceImpl<FileLabelMapper, FileLabel> implements IFileLabelService {

    @Autowired
    private IFrameTemplateAssociationService iFrameTemplateAssociationService;
    @Autowired
    private IReportDescTemplateService iReportDescTemplateService;

    @Autowired
    private ITemplateCombinationService iTemplateCombinationService;

    @Resource
    private FrameTemplateAssociationMapper frameTemplateAssociationMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public SuccessTip dealOldData() {
        //查询报告框架内容模板中间表数据
        List<FrameTemplateAssociation> frameTemplateAssociationList = iFrameTemplateAssociationService.selectList(new EntityWrapper<FrameTemplateAssociation>().eq("status", 1));
        if (ObjectUtils.isNull(frameTemplateAssociationList)) {
            return new SuccessTip(1, "数据不对", null);
        }
        //查询report_desc_template模板
        List<ReportDescTemplate> templateList = iReportDescTemplateService.selectList(new EntityWrapper<ReportDescTemplate>());
        //把FrameTemplateAssociation里面的content_name赋值上
        Map<String, List<FrameTemplateAssociation>> mappingMap = new HashMap<>();
        for (FrameTemplateAssociation frameTemplateAssociation : frameTemplateAssociationList) {
            //因为是主键自增，所以设置主键为null
            frameTemplateAssociation.setId(null);
            String reportDescId = frameTemplateAssociation.getReportDescId();
            matchingContentName(frameTemplateAssociation, templateList);
            if (!mappingMap.containsKey(reportDescId)) {
                List<FrameTemplateAssociation> list = new ArrayList<>();
                list.add(frameTemplateAssociation);
                mappingMap.put(reportDescId, list);
            } else {
                List<FrameTemplateAssociation> list = mappingMap.get(reportDescId);
                list.add(frameTemplateAssociation);
                mappingMap.put(reportDescId, list);
            }
        }
        //查询框架
        List<TemplateCombination> templateCombinationList = iTemplateCombinationService.selectList(new EntityWrapper<TemplateCombination>().ne("combination_status", 0));
        if (ObjectUtils.isNull(templateCombinationList)) {
            return new SuccessTip(1, "查不到", null);
        }
        List<FrameTemplateAssociation> insertList = new ArrayList<>();
        for (TemplateCombination templateCombination : templateCombinationList) {
            //报告框架id
            String reportTemplateId = templateCombination.getReportTemplateId();
            //说明框架id
            String descTemplateId = templateCombination.getDescTemplateId();
            setTemplateCombinationId(reportTemplateId, mappingMap, templateCombination, insertList);
            setTemplateCombinationId(descTemplateId, mappingMap, templateCombination, insertList);

        }
        //执行插入操作
        if (ObjectUtils.isNotNull(insertList)) {
            iFrameTemplateAssociationService.insertBatch(insertList);
        }
        //把原来没有content_name的数据状态置为0
        frameTemplateAssociationMapper.updateOldData();
        return new SuccessTip("数据操作成功");
    }

    private void setTemplateCombinationId(String key, Map<String, List<FrameTemplateAssociation>> mappingMap, TemplateCombination templateCombination, List<FrameTemplateAssociation> insertList)  {
        if (!mappingMap.containsKey(key)) {
            return;
        }
        List<FrameTemplateAssociation> frameTemplateAssociations = mappingMap.get(key);
        //这种方式是list的浅copy,也就是说newList里面的值改变，frameTemplateAssociations里面的值也会改变
        //List<FrameTemplateAssociation> newList = frameTemplateAssociations;
        List<FrameTemplateAssociation> newList = new ArrayList<>();
        //调用深copy方法
        try {
            //深度copy
            newList=ObjectUtils.deepCopy(frameTemplateAssociations,newList);
        }catch (Exception e){
            e.printStackTrace();
        }
        for (FrameTemplateAssociation frameTemplateAssociation : newList) {
            frameTemplateAssociation.setTemplateCombinationId(templateCombination.getId());
            insertList.add(frameTemplateAssociation);
        }
    }

    private void matchingContentName(FrameTemplateAssociation frameTemplateAssociation, List<ReportDescTemplate> templateList) {
        for (ReportDescTemplate reportDescTemplate : templateList) {
            String contentId = frameTemplateAssociation.getContentId();
            if (reportDescTemplate.getId().equals(contentId)) {
                frameTemplateAssociation.setContentName(getFilePrefix(reportDescTemplate.getFileName()));
                break;
            }
        }
    }
    /**
     * 根据文件名称返回文件前缀
     *
     * @param fileName 文件名称（比如 xxx.xls）
     * @return 参数为xxx.xls 返回数据为xxx
     */
    public static String getFilePrefix(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}

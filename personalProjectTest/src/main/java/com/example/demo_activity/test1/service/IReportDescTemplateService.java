package com.example.demo_activity.test1.service;

import com.example.demo_activity.test1.model.ReportDescTemplate;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 报告说明模板表 服务类
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
public interface IReportDescTemplateService extends IService<ReportDescTemplate> {

    void historyVisionBackup();
}

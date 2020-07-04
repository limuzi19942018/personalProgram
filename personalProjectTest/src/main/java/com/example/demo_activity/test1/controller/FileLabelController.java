package com.example.demo_activity.test1.controller;


import com.example.demo_activity.test1.service.IFileLabelService;
import com.example.demo_activity.test1.service.IReportDescTemplateService;
import com.example.demo_activity.test1.tips.SuccessTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 文件标签表 前端控制器
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@Controller
@RequestMapping("/fileLabel")
public class FileLabelController {

    @Autowired
    private IFileLabelService iFileLabelService;

    @Autowired
    private IReportDescTemplateService iReportDescTemplateService;

    /**
     * 处理老数据问题
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/dealOldData",method = RequestMethod.POST)
    public Object dealOldData(){
        return iFileLabelService.dealOldData();
    }

    /**
     * 备份版本数据库
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/historyVisionBackup",method = RequestMethod.GET)
    public Object historyVisionBackup(){
        try {
            iReportDescTemplateService.historyVisionBackup();
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessTip(1,"更新版本数据库出异常，请检查"+e.getMessage(),null);
        }
        return new SuccessTip();
    }
}


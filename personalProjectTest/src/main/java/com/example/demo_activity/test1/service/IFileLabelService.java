package com.example.demo_activity.test1.service;

import com.example.demo_activity.test1.model.FileLabel;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo_activity.test1.tips.SuccessTip;

/**
 * <p>
 * 文件标签表 服务类
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
public interface IFileLabelService extends IService<FileLabel> {

    SuccessTip dealOldData();
}

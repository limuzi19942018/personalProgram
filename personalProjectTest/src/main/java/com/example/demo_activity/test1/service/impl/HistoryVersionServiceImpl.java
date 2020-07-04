package com.example.demo_activity.test1.service.impl;

import com.example.demo_activity.test1.model.HistoryVersion;
import com.example.demo_activity.test1.dao.HistoryVersionMapper;
import com.example.demo_activity.test1.service.IHistoryVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 模板的历史版本表 服务实现类
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@Service
public class HistoryVersionServiceImpl extends ServiceImpl<HistoryVersionMapper, HistoryVersion> implements IHistoryVersionService {

}

package com.example.demo_activity.test1.dao;

import com.example.demo_activity.test1.model.FrameTemplateAssociation;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 框架模板和内容模板关联关系 Mapper 接口
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
public interface FrameTemplateAssociationMapper extends BaseMapper<FrameTemplateAssociation> {

    void updateOldData();
}

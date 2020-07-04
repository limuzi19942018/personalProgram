package com.example.demo_activity.test1.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 模板使用记录表
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@TableName("template_used_record")
public class TemplateUsedRecord extends Model<TemplateUsedRecord> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 组合模板id
     */
    @TableField("combination_template_id")
    private Integer combinationTemplateId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 状态
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCombinationTemplateId() {
        return combinationTemplateId;
    }

    public void setCombinationTemplateId(Integer combinationTemplateId) {
        this.combinationTemplateId = combinationTemplateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TemplateUsedRecord{" +
        ", id=" + id +
        ", userId=" + userId +
        ", combinationTemplateId=" + combinationTemplateId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", status=" + status +
        "}";
    }
}

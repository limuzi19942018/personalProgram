package com.example.demo_activity.test1.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 模板的历史版本表
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@TableName("history_version")
public class HistoryVersion extends Model<HistoryVersion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关联id(关联组合模板，框架，内容，样式模板)
     */
    @TableField("relation_id")
    private String relationId;
    /**
     * 模板类型（1是基础法，2是收益法，3是市场法，4是报告框架，5是说明框架，6是报告内容，7是说明内容，8是报告样式框架，9是说明样式框架，10组合模板）
     */
    private Integer type;
    /**
     * 修改人姓名
     */
    @TableField("update_user_name")
    private String updateUserName;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改的文件名称
     */
    @TableField("update_file_name")
    private String updateFileName;
    /**
     * 修改的文件路径
     */
    @TableField("update_file_path")
    private String updateFilePath;
    private Integer version;
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
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

    public String getUpdateFileName() {
        return updateFileName;
    }

    public void setUpdateFileName(String updateFileName) {
        this.updateFileName = updateFileName;
    }

    public String getUpdateFilePath() {
        return updateFilePath;
    }

    public void setUpdateFilePath(String updateFilePath) {
        this.updateFilePath = updateFilePath;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        return "HistoryVersion{" +
        ", id=" + id +
        ", relationId=" + relationId +
        ", type=" + type +
        ", updateUserName=" + updateUserName +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", updateFileName=" + updateFileName +
        ", updateFilePath=" + updateFilePath +
        ", version=" + version +
        ", status=" + status +
        "}";
    }
}

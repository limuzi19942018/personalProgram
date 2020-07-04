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
 * 文件标签表
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@TableName("file_label")
public class FileLabel extends Model<FileLabel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文件标签名称
     */
    @TableField("type_name")
    private String typeName;
    /**
     * 标签分类；1:文件标签，2:组合标签，3:报告内容类型，4说明内容类型,5所属行业
     */
    private Integer type;
    /**
     * 1：正常，0：禁用
     */
    private Integer status;
    /**
     * 父ID用于目录类型
     */
    @TableField("parent_id")
    private Integer parentId;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FileLabel{" +
        ", id=" + id +
        ", typeName=" + typeName +
        ", type=" + type +
        ", status=" + status +
        ", parentId=" + parentId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}

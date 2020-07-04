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
 * 框架模板和内容模板关联关系
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@TableName("frame_template_association")
public class FrameTemplateAssociation extends Model<FrameTemplateAssociation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 框架模板id
     */
    @TableField("report_desc_id")
    private String reportDescId;
    /**
     * 内容模板id
     */
    @TableField("content_id")
    private String contentId;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 0是禁用，1是使用，
     */
    private Integer status;
    /**
     * 框架里面的目录内容名称（从框架模板里面读取）
     */
    @TableField("content_name")
    private String contentName;
    /**
     * 组合模板id
     */
    @TableField("template_combination_id")
    private String templateCombinationId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportDescId() {
        return reportDescId;
    }

    public void setReportDescId(String reportDescId) {
        this.reportDescId = reportDescId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getTemplateCombinationId() {
        return templateCombinationId;
    }

    public void setTemplateCombinationId(String templateCombinationId) {
        this.templateCombinationId = templateCombinationId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FrameTemplateAssociation{" +
                "id=" + id +
                ", reportDescId='" + reportDescId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                ", contentName='" + contentName + '\'' +
                ", templateCombinationId='" + templateCombinationId + '\'' +
                '}';
    }
}

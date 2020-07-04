package com.example.demo_activity.test1.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 报告说明模板表
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@TableName("report_desc_template")
public class ReportDescTemplate extends Model<ReportDescTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    private String id;
    /**
     * 模板名称
     */
    @TableField("template_name")
    private String templateName;
    /**
     * 文件名称（上传文件名称）
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 模板描述
     */
    @TableField("template_describe")
    private String templateDescribe;
    /**
     * 模板类型(1:资产基础法作价表,2收益法作价表，3市场法作价表，4:报告框架模板，5:说明框架模板  6:报告内容模板   7:说明内容模板,8:评估报告样式，9评估说明样式)
     */
    @TableField("template_type")
    private Integer templateType;
    /**
     * 文件标签名称
     */
    @TableField("file_label_name")
    private String fileLabelName;
    /**
     * 文件类型（区分excel还是word）
     */
    @TableField("file_type")
    private String fileType;
    /**
     * 上传人姓名（不能用user_id因为有可能线下上传）
     */
    @TableField("upload_user_name")
    private String uploadUserName;
    /**
     * 组合状态（1启用，2禁用，0删除）
     */
    @TableField("template_status")
    private Integer templateStatus;
    /**
     * 模板存放在阿里云地址（单个文件的地址）
     */
    @TableField("template_file_path")
    private String templateFilePath;
    /**
     * 所属行业（只有内容模板时该字段才会有值）（关联标签表）
     */
    private String industry;
    /**
     * 内容模板的类型（关联字典表的主键id）
     */
    @TableField("content_type_id")
    private String contentTypeId;
    /**
     * 上传时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 内容模板的类型（存储字典表的具体名称）
     */
    @TableField("content_type_name")
    private String contentTypeName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateDescribe() {
        return templateDescribe;
    }

    public void setTemplateDescribe(String templateDescribe) {
        this.templateDescribe = templateDescribe;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getFileLabelName() {
        return fileLabelName;
    }

    public void setFileLabelName(String fileLabelName) {
        this.fileLabelName = fileLabelName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public Integer getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(Integer templateStatus) {
        this.templateStatus = templateStatus;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(String contentTypeId) {
        this.contentTypeId = contentTypeId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ReportDescTemplate{" +
        ", id=" + id +
        ", templateName=" + templateName +
        ", fileName=" + fileName +
        ", templateDescribe=" + templateDescribe +
        ", templateType=" + templateType +
        ", fileLabelName=" + fileLabelName +
        ", fileType=" + fileType +
        ", uploadUserName=" + uploadUserName +
        ", templateStatus=" + templateStatus +
        ", templateFilePath=" + templateFilePath +
        ", industry=" + industry +
        ", contentTypeId=" + contentTypeId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", version=" + version +
        ", contentTypeName=" + contentTypeName +
        "}";
    }
}

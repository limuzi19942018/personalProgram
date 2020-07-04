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
 * 模板组合表
 * </p>
 *
 * @author yongli
 * @since 2020-06-20
 */
@TableName("template_combination")
public class TemplateCombination extends Model<TemplateCombination> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 模板组合名称
     */
    @TableField("tem_combination_name")
    private String temCombinationName;
    /**
     * 模板组合标签名称
     */
    @TableField("combination_lable_name")
    private String combinationLableName;
    /**
     * 模板组合描述
     */
    @TableField("combination_describe")
    private String combinationDescribe;
    /**
     * 模板组合图片地址（存放阿里云）
     */
    @TableField("combination_picture_path")
    private String combinationPicturePath;
    /**
     * 资产基础法模板id(关联模板表)
     */
    @TableField("basic_template_id")
    private String basicTemplateId;
    /**
     * 收益法模板id
     */
    @TableField("income_template_id")
    private String incomeTemplateId;
    /**
     * 市场法模板id
     */
    @TableField("market_template_id")
    private String marketTemplateId;
    /**
     * 报告框架模板id
     */
    @TableField("report_template_id")
    private String reportTemplateId;
    /**
     * 说明框架模板id
     */
    @TableField("desc_template_id")
    private String descTemplateId;
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
     * 设置组合人员
     */
    @TableField("set_userName")
    private String setUsername;
    /**
     * 组合状态（1启用，2禁用，0删除）
     */
    @TableField("combination_status")
    private Integer combinationStatus;
    /**
     * 组合压缩包阿里云地址三大法
     */
    @TableField("combination_zip_path")
    private String combinationZipPath;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 框架压缩包阿里云地址
     */
    @TableField("framework_zip_path")
    private String frameworkZipPath;
    /**
     * 压缩包版本，定时任务的时候回根据压缩包版本判断是否需要压缩
     */
    @TableField("version_zip")
    private Integer versionZip;
    /**
     * 说明样式模板id
     */
    @TableField("desc_style_id")
    private String descStyleId;
    /**
     * 报告样式模板id
     */
    @TableField("report_style_id")
    private String reportStyleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemCombinationName() {
        return temCombinationName;
    }

    public void setTemCombinationName(String temCombinationName) {
        this.temCombinationName = temCombinationName;
    }

    public String getCombinationLableName() {
        return combinationLableName;
    }

    public void setCombinationLableName(String combinationLableName) {
        this.combinationLableName = combinationLableName;
    }

    public String getCombinationDescribe() {
        return combinationDescribe;
    }

    public void setCombinationDescribe(String combinationDescribe) {
        this.combinationDescribe = combinationDescribe;
    }

    public String getCombinationPicturePath() {
        return combinationPicturePath;
    }

    public void setCombinationPicturePath(String combinationPicturePath) {
        this.combinationPicturePath = combinationPicturePath;
    }

    public String getBasicTemplateId() {
        return basicTemplateId;
    }

    public void setBasicTemplateId(String basicTemplateId) {
        this.basicTemplateId = basicTemplateId;
    }

    public String getIncomeTemplateId() {
        return incomeTemplateId;
    }

    public void setIncomeTemplateId(String incomeTemplateId) {
        this.incomeTemplateId = incomeTemplateId;
    }

    public String getMarketTemplateId() {
        return marketTemplateId;
    }

    public void setMarketTemplateId(String marketTemplateId) {
        this.marketTemplateId = marketTemplateId;
    }

    public String getReportTemplateId() {
        return reportTemplateId;
    }

    public void setReportTemplateId(String reportTemplateId) {
        this.reportTemplateId = reportTemplateId;
    }

    public String getDescTemplateId() {
        return descTemplateId;
    }

    public void setDescTemplateId(String descTemplateId) {
        this.descTemplateId = descTemplateId;
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

    public String getSetUsername() {
        return setUsername;
    }

    public void setSetUsername(String setUsername) {
        this.setUsername = setUsername;
    }

    public Integer getCombinationStatus() {
        return combinationStatus;
    }

    public void setCombinationStatus(Integer combinationStatus) {
        this.combinationStatus = combinationStatus;
    }

    public String getCombinationZipPath() {
        return combinationZipPath;
    }

    public void setCombinationZipPath(String combinationZipPath) {
        this.combinationZipPath = combinationZipPath;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFrameworkZipPath() {
        return frameworkZipPath;
    }

    public void setFrameworkZipPath(String frameworkZipPath) {
        this.frameworkZipPath = frameworkZipPath;
    }

    public Integer getVersionZip() {
        return versionZip;
    }

    public void setVersionZip(Integer versionZip) {
        this.versionZip = versionZip;
    }

    public String getDescStyleId() {
        return descStyleId;
    }

    public void setDescStyleId(String descStyleId) {
        this.descStyleId = descStyleId;
    }

    public String getReportStyleId() {
        return reportStyleId;
    }

    public void setReportStyleId(String reportStyleId) {
        this.reportStyleId = reportStyleId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TemplateCombination{" +
        ", id=" + id +
        ", temCombinationName=" + temCombinationName +
        ", combinationLableName=" + combinationLableName +
        ", combinationDescribe=" + combinationDescribe +
        ", combinationPicturePath=" + combinationPicturePath +
        ", basicTemplateId=" + basicTemplateId +
        ", incomeTemplateId=" + incomeTemplateId +
        ", marketTemplateId=" + marketTemplateId +
        ", reportTemplateId=" + reportTemplateId +
        ", descTemplateId=" + descTemplateId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", setUsername=" + setUsername +
        ", combinationStatus=" + combinationStatus +
        ", combinationZipPath=" + combinationZipPath +
        ", version=" + version +
        ", frameworkZipPath=" + frameworkZipPath +
        ", versionZip=" + versionZip +
        ", descStyleId=" + descStyleId +
        ", reportStyleId=" + reportStyleId +
        "}";
    }
}

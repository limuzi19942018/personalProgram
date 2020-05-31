package com.example.demo_activity.test1.model;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 阿里云存放文件表
 * </p>
 *
 * @author yongli
 * @since 2020-05-30
 */
@TableName("aliyun_file")
public class AliyunFile extends Model<AliyunFile> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径存放阿里云的路径
     */
    private String filePath;
    private Date createTime;
    private Date updateTime;
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
        return "AliyunFile{" +
        ", id=" + id +
        ", fileName=" + fileName +
        ", filePath=" + filePath +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", status=" + status +
        "}";
    }
}

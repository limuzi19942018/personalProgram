package com.example.demo_activity.test1.service;

import com.example.demo_activity.test1.model.AliyunFile;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo_activity.test1.tips.SuccessTip;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 阿里云存放文件表 服务类
 * </p>
 *
 * @author yongli
 * @since 2020-05-30
 */
public interface IAliyunFileService extends IService<AliyunFile> {

    SuccessTip uploadFile(MultipartFile file)throws Exception;

    SuccessTip multiUploadFile(List<MultipartFile> files);

    SuccessTip multiUploadFileByNIO(List<MultipartFile> files);
}

package com.yunfeng.mall.fastdfs.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2020-03-10
 */

public interface FdfsService {

    String upload(MultipartFile file, String fileExtName) throws Exception;
}

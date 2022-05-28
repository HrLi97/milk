package com.lhr.milk.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lhr
 * @Date:2022/5/28 10:14
 * @Version 1.0
 */
public interface FileService {
    String fileUpload(MultipartFile file);

}

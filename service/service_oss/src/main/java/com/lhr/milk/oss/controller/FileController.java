package com.lhr.milk.oss.controller;

import com.lhr.milk.common.result.Result;
import com.lhr.milk.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lhr
 * @Date:2022/5/28 10:14
 * @Version 1.0
 */
@Api("oss")
@RestController
@RequestMapping("/api/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件到阿里云oss
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation("上传文件")
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws IOException {
        String URL = fileService.fileUpload(file);
        return Result.ok(URL);
    }

}

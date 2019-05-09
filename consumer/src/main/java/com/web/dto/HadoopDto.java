package com.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @描述:
 * @版权: Copyright (c) 2016-2018
 * @公司: lumi
 * @作者: 泽林
 * @创建日期: 2019-05-04
 * @创建时间: 下午12:52
 */
@Data
public class HadoopDto {
    String path;
    MultipartFile file;
    String oldName;
    String newName;
    String uploadPath;
    String downloadPath;
    String sourcePath;
    String targetPath;
    String userId;
    String fullPath;
    String reaourceId;
    String fileCodeFromOter;
}

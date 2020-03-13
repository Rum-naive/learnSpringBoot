package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
    private Long fileId;        //主键自增
    private Long userId;        //文件上传者的编号
    private String fileName;    //文件的名称
    private Double fileSize;    //文件的大小
    private String fileType;    //文件的类型(文档或视频)
    private Date createTime;    //文件上传的时间
}

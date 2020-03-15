package com.example.demo.controller;

import com.example.demo.model.FileVO;
import com.example.demo.sevice.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class FileUploadController {
    //上传路径
    private static String UPLOAD_PATH = "File/image/upload";

    @Resource(name = "fileServiceImpl")
    FileService fileService;

    @RequestMapping(value = "/uploadImage/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(HttpServletRequest request,@PathVariable Long id) {
        try {
            MultipartFile image = ((MultipartHttpServletRequest) request).getFile("file");
            //获取文件名称
            String name = image.getOriginalFilename();
            //获取文件大小
            Double size = (double)image.getSize();
            // 获取文件后缀名 .XX
            int suffixIndex = name.lastIndexOf(".");
            String fileSuffix = name.substring(suffixIndex);
            //inputStream
            InputStream inputStream = image.getInputStream();
            // 获得客户端发送请求的完整url
            String url = request.getRequestURL().toString();
            // 获得去除接口前的url
            String urlVal = url.replace("/uploadImage", "");
            // 目录路径
            Path directory = Paths.get(UPLOAD_PATH);
            // 判断目录是否存在，不存在创建
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            // 判断文件是否存在,存在删除
            if (Files.exists(directory.resolve(name))) {
                File file = new java.io.File(UPLOAD_PATH + "/" + name);
                file.delete();
            }
            // 拷贝文件
            Files.copy(inputStream, directory.resolve(name));
            // url路径
            // String path = serverConfig.getUrl() + "/" + UPLOAD_PATH + "/" + name;
            String path = urlVal + "/" + UPLOAD_PATH + "/" + name;
            // 生成文件对象
            FileVO fileVO = new FileVO();
            fileVO.setCreateTime(new Date());
            fileVO.setFileName(name);
            fileVO.setFileSize(size);
            fileVO.setFilePath(path);
            fileVO.setFileType(fileSuffix);
            fileVO.setUserId(id);
            fileService.saveFile(fileVO);
            return "上传成功";
        } catch (Exception e) {
            return "上传失败";
        }
    }

    @GetMapping("/getImage/{name}")
    public void getImage(HttpServletResponse response, @PathVariable("name") String name) throws IOException {
        response.setContentType("image/jpeg;charset=utf-8");
        response.setHeader("Content-Disposition", "inline; filename=girls.png");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(Files.readAllBytes(Paths.get(UPLOAD_PATH).resolve(name)));
        outputStream.flush();
        outputStream.close();
    }
}
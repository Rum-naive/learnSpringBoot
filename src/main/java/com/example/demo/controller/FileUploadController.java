package com.example.demo.controller;

import com.example.demo.config.exception.AjaxResponse;
import com.example.demo.config.exception.CustomException;
import com.example.demo.config.exception.CustomExceptionType;
import com.example.demo.model.FileVO;
import com.example.demo.sevice.FileService;
import com.example.demo.utils.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/rest")
public class FileUploadController {
    //上传路径
    private static String UPLOAD_PATH = "File/image/upload";

    @Resource(name = "fileServiceImpl")
    FileService fileService;

    @RequestMapping(value = "/upload/{id}", method = POST)
    public AjaxResponse uploadImage(HttpServletRequest request, @PathVariable Long id) {
        try {
            MultipartFile fil = ((MultipartHttpServletRequest) request).getFile("file");
            //获取文件名称
            String name = fil.getOriginalFilename();
            //获取文件大小
            Double size = (double)fil.getSize();
            // 获取文件后缀名 .XX
            int suffixIndex = name.lastIndexOf(".");
            String fileSuffix = name.substring(suffixIndex);
            //inputStream
            InputStream inputStream = fil.getInputStream();
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
            if(fileSuffix.equals(".docx") || fileSuffix.equals(".mp4")){
                fileService.saveFile(fileVO);
                return AjaxResponse.success();
            }else {
                throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"上传的文件仅支持.doc和.mp4文件");
            }
        } catch (Exception e) {
             throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"上传失败,上传的文件仅支持.doc和.mp4文件");
        }
    }

    @RequestMapping(value = "/download", method = GET)
    public void download(@RequestBody FileVO fileVO) throws IOException {
        String filename = fileVO.getFileName();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(filename);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        FileUtil.download(filename, response);
    }

}
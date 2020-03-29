package com.example.demo.controller;

import com.example.demo.config.exception.AjaxResponse;
import com.example.demo.model.FileVO;
import com.example.demo.sevice.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
@RequestMapping("/rest")
public class FileController {
    @Resource(name = "fileServiceImpl")
    FileService fileService;

    //增加一个file ，使用POST方法
    @RequestMapping(value = "/file", method = POST, produces = "application/json")
    public AjaxResponse saveFile(@RequestBody FileVO fileVO) {

        fileService.saveFile(fileVO);

        return  AjaxResponse.success(fileVO);
    }

    //删除一个file，使用DELETE方法，参数是id
    @RequestMapping(value = "/file/{id}", method = DELETE, produces = "application/json")
    public AjaxResponse deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);

        return AjaxResponse.success(id);
    }

    //更新一个file，使用PUT方法，以id为主键进行更新
    @RequestMapping(value = "/file/{id}", method = PUT, produces = "application/json")
    public AjaxResponse updateFile(@PathVariable Long id, @RequestBody FileVO fileVO) {
        fileVO.setUserId(id);

        fileService.updateFile(fileVO);

        return AjaxResponse.success(fileVO);
    }

    //获取一个file，使用GET方法
    @RequestMapping(value = "/file/{id}", method = GET, produces = "application/json")
    public AjaxResponse getFile(@PathVariable Long id) {

        return AjaxResponse.success(fileService.getFile(id));
    }

    //获取所有user，使用GET方法
    @RequestMapping(value = "/file", method = GET, produces = "application/json")
    public AjaxResponse getAll() {

        return AjaxResponse.success(fileService.getAll());
    }
}

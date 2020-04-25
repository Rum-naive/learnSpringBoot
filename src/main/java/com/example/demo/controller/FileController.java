package com.example.demo.controller;

import com.example.demo.config.exception.AjaxResponse;
import com.example.demo.model.FileVO;
import com.example.demo.sevice.FileService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

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
        fileVO.setFileId(id);
        fileService.updateFile(fileVO);

        return AjaxResponse.success(fileVO);
    }

    //获取一个file，使用GET方法
    @RequestMapping(value = "/file/{id}", method = GET, produces = "application/json")
    public AjaxResponse getFile(@PathVariable Long id) {

        return AjaxResponse.success(fileService.getFile(id));
    }

    //模糊查询filename，使用GET方法
    @RequestMapping(value = "/file/query/{name}", method = GET, produces = "application/json")
    public AjaxResponse getFileLike(@PathVariable String name,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<FileVO> list =fileService.getFileLike(name);
        PageInfo<FileVO> pageInfo = new PageInfo<FileVO>(list);
        return AjaxResponse.success(pageInfo);
    }

    //获取所有file，使用GET方法
    @RequestMapping(value = "/file", method = GET, produces = "application/json")
    public AjaxResponse getAll(int pageNum,int pageSize) {
        List<FileVO> list1 =fileService.getAll();
        int total = (int) new PageInfo<>(list1).getTotal();
        PageHelper.startPage(pageNum,pageSize);
        List<FileVO> list2 =fileService.getAll();
        //PageInfo<FileVO> pageInfo = new PageInfo<FileVO>(list);
        //System.out.println(total);
        PageInfo<FileVO> pageInfo = new PageInfo<FileVO>(list2);
        pageInfo.setTotal(total);
        return AjaxResponse.success(pageInfo);
    }

    //获取所有该用户doc，使用GET方法
    @RequestMapping(value = "/doc/{used}", method = GET, produces = "application/json")
    public AjaxResponse getAllDoc(@PathVariable Long used,int pageNum,int pageSize) {
        List<FileVO> list1 =fileService.getAllDoc(used);
        int total = (int) new PageInfo<>(list1).getTotal();
        PageHelper.startPage(pageNum,pageSize);
        List<FileVO> list2 =fileService.getAllDoc(used);
        PageInfo<FileVO> pageInfo = new PageInfo<FileVO>(list2);
        pageInfo.setTotal(total);
        return AjaxResponse.success(pageInfo);
    }

    //获取所有该用户mp4，使用GET方法
    @RequestMapping(value = "/mp4/{used}", method = GET, produces = "application/json")
    public AjaxResponse getAllMp4(@PathVariable Long used,int pageNum,int pageSize) {
        List<FileVO> list1 =fileService.getAllMp4(used);
        int total = (int) new PageInfo<>(list1).getTotal();
        PageHelper.startPage(pageNum,pageSize);
        List<FileVO> list2 =fileService.getAllMp4(used);
        PageInfo<FileVO> pageInfo = new PageInfo<FileVO>(list2);
        pageInfo.setTotal(total);
        return AjaxResponse.success(pageInfo);
    }

    //模糊查询个人filename，使用GET方法
    @RequestMapping(value = "/file/query/{name}/{id}", method = GET, produces = "application/json")
    public AjaxResponse getFileLikeById(@PathVariable String name,@PathVariable Long id,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<FileVO> list =fileService.getFileLikeById(name,id);
        PageInfo<FileVO> pageInfo = new PageInfo<FileVO>(list);
        return AjaxResponse.success(pageInfo);
    }
}

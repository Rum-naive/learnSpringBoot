package com.example.demo.sevice;

import com.example.demo.config.exception.CustomException;
import com.example.demo.config.exception.CustomExceptionType;
import com.example.demo.generator.*;
import com.example.demo.model.FileVO;
import com.example.demo.model.UserVO;
import com.example.demo.utils.DozerUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    @Resource
    protected Mapper dozerMapper;

    @Resource
    private FileMapper fileMapper;

    @Override
    public FileVO saveFile(FileVO file) {
        File filePO = dozerMapper.map(file,File.class);
        fileMapper.insert(filePO);
        return null;
    }

    @Override
    public void deleteFile(Long id) {
        fileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateFile(FileVO file) {
        File userPO = dozerMapper.map(file,File.class);
        fileMapper.updateByPrimaryKeySelective(userPO);
    }

    @Override
    public FileVO getFile(Long id) {
        return dozerMapper.map(fileMapper.selectByPrimaryKey(id), FileVO.class);
    }

    @Override
    public List<FileVO> getFileLike(String name) {
        String query_name = "%" + name + "%";
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andFileNameLike(query_name);
        List<File> query_like = fileMapper.selectByExample(fileExample);
        return DozerUtils.mapList(query_like,FileVO.class);
    }

    @Override
    public List<FileVO> getAll() {
        List<File> files = fileMapper.selectByExample(null);
        return DozerUtils.mapList(files,FileVO.class);
    }

    @Override
    public List<FileVO> getAllDoc(Long id) {
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andFileTypeEqualTo(".docx").andUserIdEqualTo(id);
        List<File> docs = new ArrayList<File>();
        docs = fileMapper.selectByExample(fileExample);
        if (docs.isEmpty()){
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"您未上传文档文件");
        }
        return DozerUtils.mapList(docs,FileVO.class);
    }

    @Override
    public List<FileVO> getAllMp4(Long id) {
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andFileTypeEqualTo(".mp4").andUserIdEqualTo(id);
        List<File> Mp4 = new ArrayList<File>();
        Mp4 = fileMapper.selectByExample(fileExample);
        if (Mp4.isEmpty()){
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"您未上传视频文件");
        }
        return DozerUtils.mapList(Mp4,FileVO.class);
    }

    @Override
    public List<FileVO> getFileLikeById(String name, Long id) {
        String query_name = "%" + name + "%";
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andFileNameLike(query_name).andUserIdEqualTo(id);
        List<File> query_like = fileMapper.selectByExample(fileExample);
        return DozerUtils.mapList(query_like,FileVO.class);
    }
}

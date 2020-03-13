package com.example.demo.sevice;

import com.example.demo.generator.File;
import com.example.demo.generator.FileMapper;
import com.example.demo.generator.User;
import com.example.demo.generator.UserMapper;
import com.example.demo.model.FileVO;
import com.example.demo.model.UserVO;
import com.example.demo.utils.DozerUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<FileVO> getAll() {
        List<File> files = fileMapper.selectByExample(null);
        return DozerUtils.mapList(files,FileVO.class);
    }
}

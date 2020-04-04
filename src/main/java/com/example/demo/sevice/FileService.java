package com.example.demo.sevice;

import com.example.demo.model.FileVO;


import java.util.List;

public interface FileService {
    FileVO saveFile(FileVO file);

    void deleteFile(Long id);

    void updateFile(FileVO file);

    FileVO getFile(Long id);

    List<FileVO> getAll(Long id);

    List<FileVO> getAllDoc(Long id);

    List<FileVO> getAllMp4(Long id);
}

package com.example.SuperDuperDrive.services;


import com.example.SuperDuperDrive.mapper.FileMapper;
import com.example.SuperDuperDrive.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /* method for getting the stored file */
    public File getFile(Integer fileId, Integer userid) {
        return fileMapper.getFile(fileId, userid);
    }

    /* method for deleting a file */
    public void deleteFile(Integer fileId, Integer userid) {
        fileMapper.deleteFile(fileId, userid);
    }

    public boolean isExistingFile(MultipartFile file, Integer userid) {
        return fileMapper.fileExists(file.getOriginalFilename(), userid);
    }

    public List<File> getFileNames(Integer userid) {
        return fileMapper.getAllFilesforUser(userid);
    }

    public void storeFile(MultipartFile file, Integer userid) throws IOException {
        File fileObject = new File();
        fileObject.setUserid(userid);
        fileObject.setFilename(file.getOriginalFilename());
        fileObject.setFilesize(file.getSize());
        fileObject.setContenttype(file.getContentType());
        fileObject.setFiledata(file.getBytes());
        fileMapper.insert(fileObject);
    }
}

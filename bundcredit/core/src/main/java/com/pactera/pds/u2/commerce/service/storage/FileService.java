package com.pactera.pds.u2.commerce.service.storage;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    public String genFileName(MultipartFile file);
    public boolean saveFile(String id, String fileName, MultipartFile file) throws IOException;
    public boolean deleteFile(String id, String fileName) throws IOException;
    public File getFile(String id,String fileName);
    public File[] getFiles(String id);
    public String[] getFilesName(String id);
}

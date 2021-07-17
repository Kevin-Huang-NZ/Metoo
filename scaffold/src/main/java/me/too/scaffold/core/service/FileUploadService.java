package me.too.scaffold.core.service;

import org.springframework.web.multipart.MultipartFile;

import me.too.scaffold.core.model.FileUpload;
import me.too.scaffold.error.BusinessException;

public interface FileUploadService {
	void init() throws BusinessException;
	FileUpload store(MultipartFile file, FileUpload record) throws BusinessException;
}

package me.too.scaffold.core.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import me.too.scaffold.core.dao.FileUploadMapper;
import me.too.scaffold.core.model.FileUpload;
import me.too.scaffold.core.service.FileUploadService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;
import me.too.scaffold.GlobalConst;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	Logger logger = LoggerFactory.getLogger(FileUploadService.class);
	
	@Value("${file-upload.location}")
	private String location;
	
	@Autowired
	private FileUploadMapper mapper;
	
	private Path rootLocation;

	@Override
	public void init() throws BusinessException {
		try {
			this.rootLocation = Paths.get(this.location);
			Files.createDirectories(this.rootLocation);
		}
		catch (IOException e) {
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "文件上传路径创建失败，请联系管理员。");
		}
		
	}

	@Override
	public FileUpload store(MultipartFile file, FileUpload record) throws BusinessException {
		try {
			String uploadedFileName = getUUID();
			
			Path destinationFile = this.rootLocation.resolve(Paths.get(uploadedFileName)).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "上传文件路径不正确，请联系管理员。");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
			
			record.setDeleteStatus("0");
			record.setFileName(file.getOriginalFilename());
			record.setFullPath(GlobalConst.UPLOADED_FILE_ACCESS_URL_PREFIX + uploadedFileName);
			mapper.insertSelective(record);
			
			return record;
		}
		catch (IOException e) {
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "文件保存失败，请联系管理员。");
		}
	}


	private String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}

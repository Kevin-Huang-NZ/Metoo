package me.too.scaffold.web.controller;

import static me.too.scaffold.util.CheckUtil.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import me.too.scaffold.core.model.FileUpload;
import me.too.scaffold.core.service.FileUploadService;
import me.too.scaffold.error.BusinessException;
import me.too.scaffold.error.EmBusinessError;
import me.too.scaffold.web.response.Root;

@RestController
@RequestMapping("/api/file")
public class FileUploadController extends BaseController {

	Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private FileUploadService fileUploadService;
	
	@PostMapping("/upload")
	public Root handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("tableName") String tableName, @RequestParam("rowId") Long rowId) throws BusinessException {
		FileUpload params = new FileUpload(tableName, rowId);
		check(params);
		FileUpload fileUploaded = fileUploadService.store(file, params);

		return Root.create(fileUploaded);
	}
	
	private void check(FileUpload fileUpload) throws BusinessException {
		if (fileUpload == null || isEmpty(fileUpload.getTableName()) || fileUpload.getRowId() == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "未指定上传文件用途，上传失败。");
		}
	}
	
}

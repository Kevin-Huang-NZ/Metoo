package me.too.scaffold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import me.too.scaffold.core.service.FileUploadService;
import me.too.scaffold.core.service.SysRoleFunService;

@Component
public class InitializeRunner implements ApplicationRunner {

	@Autowired
	private SysRoleFunService sysRoleFunService;

	@Autowired
	private FileUploadService fileUploadService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		fileUploadService.init();
		sysRoleFunService.getAndCacheRoleFuns();
	}

}

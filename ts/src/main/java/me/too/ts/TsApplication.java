package me.too.ts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication(scanBasePackages = { "me.too.scaffold","me.too.ts" })
@MapperScan(basePackages = { "me.too.scaffold.core.dao", "me.too.ts.core.dao" })
@EnableCaching
public class TsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TsApplication.class, args);
	}

}

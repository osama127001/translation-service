package com.osama.translation_service;

import com.osama.translation_service.dao.TagDao;
import com.osama.translation_service.dao.TranslationDao;
import com.osama.translation_service.domain.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner init(TagDao tagDao) {
		return args -> {
			tagDao.save(new Tag("mobile"));
			tagDao.save(new Tag("web"));
			tagDao.save(new Tag("desktop"));
		};
	}

}

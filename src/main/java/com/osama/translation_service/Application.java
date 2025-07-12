package com.osama.translation_service;

import com.osama.translation_service.dao.LocaleDao;
import com.osama.translation_service.dao.TagDao;
import com.osama.translation_service.dao.TranslationDao;
import com.osama.translation_service.domain.Locale;
import com.osama.translation_service.domain.Tag;
import com.osama.translation_service.domain.Translation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner translationDataLoader(TranslationDao translationDao) {
		return args -> {
			InputStream is = getClass().getResourceAsStream("/translations.csv");
			if (is == null) {
				System.err.println("translations.csv not found in classpath.");
				return;
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				reader.lines().forEach(line -> {
					String[] parts = line.split(",");
					if (parts.length == 2) {
						String language = parts[0].trim();
						String code = parts[1].trim();
						Translation translation = new Translation(language, code);
						translationDao.save(translation);
					}
				});
			}
		};
	}

	@Bean
	public CommandLineRunner LocaleDataLoader(LocaleDao localeDao) {
		return args -> {
			InputStream is = getClass().getResourceAsStream("/locales.csv");
			if (is == null) {
				System.err.println("locales.csv not found in classpath.");
				return;
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				reader.lines().forEach(line -> {
					String[] parts = line.split(",");
					if (parts.length == 2) {
						String region = parts[0].trim();
						String code = parts[1].trim();
						Locale locale = new Locale(region, code);
						localeDao.save(locale);
					}
				});
			}
		};
	}

	@Bean
	public CommandLineRunner tagDataLoader(TagDao tagDao) {
		return args -> {
			InputStream is = getClass().getResourceAsStream("/tags.csv");
			if (is == null) {
				System.err.println("tags.csv not found in classpath.");
				return;
			}
			String line;
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				while ((line = reader.readLine()) != null) {
					String tagName = line.trim();
					if (!tagName.isEmpty()) {
						Tag tag = new Tag(tagName);
						tagDao.save(tag);
					}
				}
			}
		};
	}

}

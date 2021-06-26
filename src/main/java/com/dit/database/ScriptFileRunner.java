package com.dit.database;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * Custom sql script runner, this can be implemented in future to run multiple
 * scripts from folder.
 * 
 * @author anavulla
 * 
 *
 */
@Component
public class ScriptFileRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScriptFileRunner.class);

	@Autowired
	private DataSource dataSource;

	@Value("${mysql.schema.file}")
	private String SCHEMA_FILE;

	@Value("${mysql.schema.file.execute}")
	private Boolean SCHEMA_FILE_EXECUTE;

	@EventListener(ApplicationReadyEvent.class)
	public void dataInitialize() {
		if (SCHEMA_FILE_EXECUTE) {
			LOGGER.info("START:running schema file");

			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8",
					new ClassPathResource(SCHEMA_FILE));
			resourceDatabasePopulator.execute(dataSource);

			LOGGER.info("END:running schema file");
		}

	}

}

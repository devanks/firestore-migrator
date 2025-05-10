// src/main/java/…/config/SourceFirestoreConfig.java
package dev.devanks.solarman.migrator.config;

import dev.devanks.solarman.migrator.repository.source.SourceReadingRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;

@Configuration
@Profile("source")
@EnableReactiveFirestoreRepositories(
        basePackageClasses = SourceReadingRepository.class
)
class SourceFirestoreConfig { }

// src/main/java/…/config/DestFirestoreConfig.java
package dev.devanks.solarman.migrator.config;

import dev.devanks.solarman.migrator.repository.destination.DestReadingRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;

@Configuration
@Profile("dest")
@EnableReactiveFirestoreRepositories(
        basePackageClasses = DestReadingRepository.class
)
class DestFirestoreConfig { }

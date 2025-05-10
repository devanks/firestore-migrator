// src/main/java/dev/devanks/solarman/migrator/repository/source/SourceReadingRepository.java
package dev.devanks.solarman.migrator.repository.source;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import dev.devanks.solarman.migrator.model.source.SourceReading;
import org.springframework.stereotype.Repository;


@Repository
public interface SourceReadingRepository
        extends FirestoreReactiveRepository<SourceReading> {
}


// src/main/java/dev/devanks/solarman/migrator/repository/destination/DestReadingRepository.java
package dev.devanks.solarman.migrator.repository.destination;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import dev.devanks.solarman.migrator.model.destination.DestReading;
import org.springframework.stereotype.Repository;


@Repository
public interface DestReadingRepository
        extends FirestoreReactiveRepository<DestReading> {
}

package dev.devanks.solarman.migrator;

import com.google.cloud.Timestamp;
import dev.devanks.solarman.migrator.model.destination.DestReading;
import dev.devanks.solarman.migrator.model.source.SourceReading;
import dev.devanks.solarman.migrator.repository.destination.DestReadingRepository;
import dev.devanks.solarman.migrator.repository.source.SourceReadingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
@Slf4j
public class MigratorApplication {

    public static void main(String[] args) {

        /* ---------- 1. read EVERYTHING from source --------------- */
        ConfigurableApplicationContext source =
                new SpringApplicationBuilder(MigratorApplication.class)
                        .profiles("source")                              // only Source repo exists
                        .properties("spring.config.name=application-source")
                        .run(args);

        SourceReadingRepository sourceRepo = source.getBean(SourceReadingRepository.class);

        List<DestReading> payload =
                sourceRepo.findAll()
                        .map(MigratorApplication::toDest)
                        .collectList()
                        .block();           // block is fine in a one-off script

        source.close();


        /* ---------- 2. write it into destination ----------------- */
        ConfigurableApplicationContext dstCtx =
                new SpringApplicationBuilder(MigratorApplication.class)
                        .profiles("dest")                                // only Dest repo exists
                        .properties("spring.config.name=application-dest")
                        .run(args);

        DestReadingRepository destRepo = dstCtx.getBean(DestReadingRepository.class);

        log.info("list: {}", destRepo.findAll().collectList().block());

        assert payload != null;
        destRepo.deleteAll()                 // make runs idempotent
                .thenMany(destRepo.saveAll(payload))
                .blockLast();

        dstCtx.close();

        log.info("✔ Migration finished");
    }

    /* helper (same logic as before) */
    private static DestReading toDest(SourceReading sourceReading) {
        long nanos = sourceReading.getTimestamp() * 1_000_000_000L;
        return DestReading.builder()
                .id(String.valueOf(nanos))
                .currentPowerW(sourceReading.getCurrentPowerW())
                .dailyProductionKWh(sourceReading.getDailyProductionKWh())
                .online(sourceReading.isIsOnline())
                .readingTimestamp(Timestamp.ofTimeSecondsAndNanos(sourceReading.getTimestamp(), 0))
                .ingestedTimestamp(Timestamp.now())
                .build();
    }
}


// src/main/java/dev/devanks/solarman/migrator/model/destination/DestReading.java
package dev.devanks.solarman.migrator.model.destination;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collectionName = "solar_readings_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestReading {

    @DocumentId                  // we keep the same numeric key but convert to nanos
    private String id;   // e.g. "1746883355000000000"

    private double currentPowerW;
    private double dailyProductionKWh;
    private boolean online;

    private Timestamp readingTimestamp;
    private Timestamp ingestedTimestamp;
}

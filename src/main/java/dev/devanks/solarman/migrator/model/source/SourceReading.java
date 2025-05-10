// src/main/java/dev/devanks/solarman/migrator/model/source/SourceReading.java
package dev.devanks.solarman.migrator.model.source;

import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import lombok.Data;

/*
 *  Collection name in Project-1 is the root “documents” collection itself,
 *  so we leave collectionName empty -> Spring will use the simple class name.
 *  If your collection has an explicit name, set it here.
 */
@Document(collectionName = "(default)")      // "" == class-name i.e. sourceReading
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceReading {

    @Id
    private long Timestamp; //NOSONAR
    private double CurrentPowerW; //NOSONAR
    private double DailyProductionKWh; //NOSONAR
    private boolean IsOnline; //NOSONAR

}

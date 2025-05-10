# ☀️  Solarman Firestore Migrator (one-off)

This tiny project exists **only** to copy ~800 historic documents from one
Firestore database to another.  
It is **not** production quality, has **no** deployment story and can be
deleted the moment the migration is done.

|                    | Source                                    | Destination                            |
|--------------------|-------------------------------------------|----------------------------------------|
| GCP project-id     | `solarman-smartthings`                    | `solarman-data-ingestor`               |
| Collection / path  | root collection – doc-id = epoch seconds  | `solar_readings_history`               |
| Docs right now     | ≈ 800                                     | will be replaced                       |

---

## 1. Prerequisites

* Java 21
* Gradle 8 (wrapper included)
* `gcloud` CLI with Application-Default Credentials that can read/write **both**
  projects

```bash
# Make sure credentials work
gcloud auth application-default login
```

> The code uses **ADC** – no service-account keys are stored in the repo.

---

## 2. Running the migration

```bash
# build once
./gradlew bootJar

# execute the migration
java -jar build/libs/firestore-migrator-0.0.1-SNAPSHOT.jar
```

What happens internally:

1. Spring context with profile **`source`** starts  
   → reads all 800 docs → converts them → stores them in memory → context shuts down.
2. Second context with profile **`dest`** starts  
   → **deletes** every document in `solar_readings_history` (makes the run idempotent)  
   → writes the converted payload → context shuts down.
3. Console prints `✔ Migration finished`.

Total run-time: a few seconds.

---

## 3. Project layout (minimal)

```
src/main/java/dev/devanks/solarman/migrator
 ├── MigratorApplication.java   # boots two isolated Spring contexts
 ├── entity/
 │    ├── SourceReading.java
 │    └── DestReading.java
 ├── repo/
 │    ├── SourceReadingRepository.java
 │    └── DestReadingRepository.java
 └── config/
      ├── SourceFirestoreConfig.java  # enables only Source repo (profile=source)
      └── DestFirestoreConfig.java    # enables only Dest repo (profile=dest)

src/main/resources
 ├── application-source.yml          # project-id = solarman-smartthings
 └── application-dest.yml            # project-id = solarman-data-ingestor
```

No `FirestoreTemplate`, no raw Firestore SDK, only **reactive repositories**.

---

## 4. Re-running

Safe to run multiple times – the destination collection is wiped first.

---


**Enjoy the sunshine.** 🌞
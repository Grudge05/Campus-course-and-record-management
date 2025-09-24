package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    private Path dataDir;
    private Path backupDir;

    public BackupService(Path dataDir) {
        this.dataDir = dataDir;
        this.backupDir = Paths.get("backups");
    }

    public void performBackup() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path timestampedBackup = backupDir.resolve("backup_" + timestamp);
        Files.createDirectories(timestampedBackup);
        new ImportExportService().backupData(dataDir, timestampedBackup);
        System.out.println("Backup created at: " + timestampedBackup);
    }

    public void listBackups() throws IOException {
        if (!Files.exists(backupDir)) {
            System.out.println("No backups found.");
            return;
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(backupDir)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    System.out.println(entry.getFileName());
                }
            }
        }
    }

    public long getBackupSize(Path backupPath) throws IOException {
        return new ImportExportService().computeDirectorySize(backupPath);
    }
}

package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.database.DatabaseConnection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

class InventoryReportGeneratorTest {

    @Test
    void generateAndDisplayReports_ShouldNotThrowException() {
        // Arrange
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();
        InventoryReportGenerator reportGenerator = new InventoryReportGenerator(dbConnection);

        // Act & Assert
        assertDoesNotThrow(() -> reportGenerator.generateAndDisplayReports());
    }

    @Test
    void generateAndWriteReportToFile_ShouldCreateFile() throws IOException {
        // Arrange
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();
        InventoryReportGenerator reportGenerator = new InventoryReportGenerator(dbConnection);

        // Create a temporary directory and file
        Path tempDir = Files.createTempDirectory("tempTestDir");
        Path tempFile = Files.createTempFile(tempDir, "tempTestFile", ".txt");

        // Backup the original System.out and replace it to capture printed output
        Path originalFile = Path.of(reportGenerator.createFileNameWithCurrentDate());
        System.out.println(originalFile.toString());

        // Act
        assertDoesNotThrow(() -> reportGenerator.generateAndWriteReportToFile());

        // Assert
        assertTrue(Files.exists(tempFile));

        // Read the content of the file and check if it matches the expected format
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toFile()))) {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.matches("Product ID: \\d+, Name: .*, Quantity: \\d+, Price: \\d+\\.\\d+"));
        }

        // Restore the original System.out
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }
}

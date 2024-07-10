package com.example.demo.saveLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class csvlog {
    private static final String FILE_PATH;

    static {
        // Determine the project root directory dynamically
        String projectRoot = Paths.get("").toAbsolutePath().toString();
        // Define the logs directory path within the package
        String logDirPath = projectRoot + "/src/main/java/com/example/demo/saveLog/logs";
        // Ensure the logs directory exists
        File logDir = new File(logDirPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        // Define the complete file path
        FILE_PATH = logDirPath + "/material_price_list.csv";
    }

    public static synchronized void log(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

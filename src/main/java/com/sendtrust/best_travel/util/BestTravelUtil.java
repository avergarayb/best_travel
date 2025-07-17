package com.sendtrust.best_travel.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BestTravelUtil {

    private static final Random random = new Random();

    public static LocalDateTime getRandomSoon() {
        var randomHours = random.nextInt(5 - 2) + 2;
        var now = LocalDateTime.now();

        return now.plusHours(randomHours);
    }

    public static LocalDateTime getRandomLatter() {
        var randomHours = random.nextInt(12 - 6) + 6;
        var now = LocalDateTime.now();

        return now.plusHours(randomHours);
    }

    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    public static void writeNotification(String text, String path) throws IOException {
        var fileWriter = new FileWriter(path, true);
        var bufferedWrithe = new BufferedWriter(fileWriter);
        try (fileWriter; bufferedWrithe) {
            bufferedWrithe.write(text);
            bufferedWrithe.newLine();
        }
    }
}

package se.drachbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<String[]> locations = getLocations();
        AtomicInteger similarityScore = new AtomicInteger();
        ArrayList<String> leftList = new ArrayList<>();
        ArrayList<String> rightList = new ArrayList<>();

        locations.forEach(location -> leftList.add(location[0]));
        locations.forEach(location -> rightList.add(location[1]));

        leftList.forEach(location -> {
            int locationId = Integer.parseInt(location);
            int numberOfSimilarLocations = (int) rightList.stream().filter(rightLocationId -> rightLocationId.equals(location)).count();
            similarityScore.addAndGet(locationId * numberOfSimilarLocations);
        });

        System.out.println(similarityScore.get());
    }

    public static ArrayList<String[]> getLocations() {
        ArrayList<String[]> locations = new ArrayList<>();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("list.txt")) {
            if (inputStream == null) {
                System.out.println("File not found");
                throw new RuntimeException("File not found");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] location = line.split(" {3}");
                    locations.add(location);
                }
            }
        } catch (IOException e) {
            System.out.println("Kunde inte hämta list.txt");
            throw new RuntimeException(e);
        }
        return locations;
    }
}
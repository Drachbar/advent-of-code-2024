package se.drachbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String[]> locations = getLocations();
        int distance = 0;
        ArrayList<String> leftList = new ArrayList<>();
        ArrayList<String> rightList = new ArrayList<>();

        locations.forEach(location -> leftList.add(location[0]));
        locations.forEach(location -> rightList.add(location[1]));

        leftList.sort((a, b) -> { return -1 * b.compareTo(a); });
        rightList.sort((a, b) -> { return -1 * b.compareTo(a); });
        for (int i = 0; i < leftList.size(); i++) {
            int leftVal = Integer.parseInt(leftList.get(i));
            int rightVal = Integer.parseInt(rightList.get(i));
            distance += Math.abs(leftVal - rightVal);
        }
        System.out.println(distance);
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
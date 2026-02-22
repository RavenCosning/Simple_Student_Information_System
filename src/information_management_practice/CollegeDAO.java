package information_management_practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CollegeDAO {

    private final String FILE_PATH = "data/college.csv";

    public void saveColleges(List<College> colleges) {
        new File("data").mkdirs();

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (College college : colleges) {
                String line = 
                        sanitize(college.getCode()) + "," + 
                        sanitize(college.getName()) + 
                        System.lineSeparator();
                
                    writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<College> getAllColleges() {
        List<College> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return list;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.trim().isEmpty()) continue;

                // FIX: split only on first comma
                String[] parts = line.split(",", 2);

                if (parts.length == 2) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    list.add(new College(code, name));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }
    private String sanitize(String value) {
     if (value == null) return "";
        return value
            .replace(",", " ")
            .replace("\n", " ")
            .replace("\r", " ")
            .trim();}
}

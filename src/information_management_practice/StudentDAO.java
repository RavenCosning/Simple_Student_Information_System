package information_management_practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StudentDAO {

    private final String FILE_PATH = "data/student.csv";

    public void saveStudents(List<Student> students) {
        new File("data").mkdirs();

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Student student : students) {
                String line =
                        sanitize(student.getId()) + "," +
                        sanitize(student.getFirstName()) + "," +
                        sanitize(student.getLastName()) + "," +
                        sanitize(student.getProgramCode()) + "," +
                        student.getYear() + "," +
                        sanitize(student.getGender()) +
                        System.lineSeparator();

                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return list;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                // FIX: limit split to 6 parts so extra commas in last field don't break parsing badly
                String[] parts = line.split(",", 6);

                if (parts.length == 6) {
                    String id = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    String programCode = parts[3].trim();

                    int year;
                    try {
                        year = Integer.parseInt(parts[4].trim());
                    } catch (NumberFormatException ex) {
                        // Skip bad row instead of crashing
                        continue;
                    }

                    String gender = parts[5].trim();

                    list.add(new Student(id, firstName, lastName, programCode, year, gender));
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
        .trim();
}
}

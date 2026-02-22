package information_management_practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ProgramDAO {

    private final String FILE_PATH = "data/program.csv";

    public void savePrograms(List<Program> programs) {
        new File("data").mkdirs();

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Program program : programs) {
                String line =
                        sanitize(program.getCode()) + "," +
                        sanitize(program.getName()) + "," +
                        sanitize(program.getCollegeCode()) +
                        System.lineSeparator();

                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Program> getAllPrograms() {
        List<Program> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return list;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                // FIX: split only into 3 parts max (code, name, collegeCode)
                String[] parts = line.split(",", 3);

                if (parts.length == 3) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    String collegeCode = parts[2].trim();

                    list.add(new Program(code, name, collegeCode));
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

package fileio;

import dto.Student;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class StudentFile {

    public static List<Student> loadFromFile(ServletContext context) {
        List<Student> result = new ArrayList<>();

        // Try to load via ServletContext
        if (context != null) {
            try (InputStream is = context.getResourceAsStream("/WEB-INF/students.txt")) {
                if (is != null) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (line.trim().isEmpty()) continue;
                            Student s = parseStudent(line);
                            if (s != null && !isDuplicate(result, s.getId())) {
                                result.add(s);
                            }
                        }
                        return result;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading students from context: " + e.getMessage());
            }
        }

        // Fallback: direct file paths
        String[] fallbackPaths = {"web/WEB-INF/students.txt", "students.txt", "src/java/fileio/students.txt"};
        for (String path : fallbackPaths) {
            File f = new File(path);
            if (f.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.trim().isEmpty()) continue;
                        Student s = parseStudent(line);
                        if (s != null && !isDuplicate(result, s.getId())) {
                            result.add(s);
                        }
                    }
                    return result;
                } catch (Exception e) {
                    System.out.println("Error reading fallback file " + path + ": " + e.getMessage());
                }
            }
        }

        return result;
    }

    private static Student parseStudent(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 4) return null;

            String id = parts[0].trim();
            String name = parts[1].trim();
            String major = parts[2].trim();
            double gpa = Double.parseDouble(parts[3].trim());
            String courses = (parts.length >= 5) ? parts[4].trim() : "Chua dang ky";

            if (id.isEmpty() || name.isEmpty()) return null;
            return new Student(id, name, major, gpa, courses);
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean isDuplicate(List<Student> list, String id) {
        for (Student s : list) {
            if (s.getId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }
}

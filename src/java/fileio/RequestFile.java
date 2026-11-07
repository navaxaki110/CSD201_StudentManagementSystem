package fileio;

import dto.RegistrationRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class RequestFile {

    public static List<RegistrationRequest> loadFromFile(ServletContext context) {
        List<RegistrationRequest> list = new ArrayList<>();

        if (context != null) {
            try (InputStream is = context.getResourceAsStream("/WEB-INF/requests.txt")) {
                if (is != null) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (line.trim().isEmpty()) continue;
                            RegistrationRequest req = parseRequest(line);
                            if (req != null) list.add(req);
                        }
                        return list;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading requests from context: " + e.getMessage());
            }
        }

        String[] fallbackPaths = {"web/WEB-INF/requests.txt", "requests.txt", "src/java/fileio/requests.txt"};
        for (String path : fallbackPaths) {
            File f = new File(path);
            if (f.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.trim().isEmpty()) continue;
                        RegistrationRequest req = parseRequest(line);
                        if (req != null) list.add(req);
                    }
                    return list;
                } catch (Exception e) {
                    System.out.println("Error reading fallback requests " + path + ": " + e.getMessage());
                }
            }
        }

        return list;
    }

    private static RegistrationRequest parseRequest(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 2) return null;
            String studentId = parts[0].trim();
            String courseCode = parts[1].trim();
            String time = (parts.length >= 3) ? parts[2].trim() : LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            return new RegistrationRequest(studentId, courseCode, time);
        } catch (Exception e) {
            return null;
        }
    }
}

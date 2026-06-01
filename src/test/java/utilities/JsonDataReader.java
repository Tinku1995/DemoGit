package utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Map<String, String>> readTestData(String filePath) throws IOException {
        File jsonFile = new File(filePath);

        if (!jsonFile.exists()) {
            throw new IOException("Test data file not found: " + filePath);
        }

        String content = new String(java.nio.file.Files.readAllBytes(jsonFile.toPath())).trim();

        if (content.startsWith("[")) {
            return objectMapper.readValue(jsonFile,
                    new TypeReference<List<Map<String, String>>>() {});
        } else {
            Map<String, String> single = objectMapper.readValue(jsonFile,
                    new TypeReference<Map<String, String>>() {});
            return java.util.Collections.singletonList(single);
        }
    }
}

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonReportGenerator {

    public static void main(String[] args) {
        String valuesFilePath = "/Users/daniel/Desktop/task3/values.json";
        String testsFilePath = "/Users/daniel/Desktop/task3/tests.json";
        String reportFilePath = "/Users/daniel/Desktop/task3/report.json";

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode valuesArray = objectMapper.readTree(new File(valuesFilePath)).get("values");

            Map<Integer, String> valuesMap = new HashMap<>();
            for (JsonNode valueNode : valuesArray) {
                int id = valueNode.get("id").asInt();
                String value = valueNode.get("value").asText();
                valuesMap.put(id, value);
            }

            JsonNode testsTree = objectMapper.readTree(new File(testsFilePath)).get("tests");

            fillValues(testsTree, valuesMap);

            ObjectNode reportNode = objectMapper.createObjectNode();
            reportNode.set("tests", testsTree);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(reportFilePath), reportNode);

            System.out.println("Файл report.json успешно создан.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fillValues(JsonNode testNode, Map<Integer, String> valuesMap) {
        for (JsonNode node : testNode) {
            if (node.has("id")) {
                int id = node.get("id").asInt();
                if (valuesMap.containsKey(id)) {
                    ((ObjectNode) node).put("value", valuesMap.get(id));
                }
            }
            
            if (node.has("values")) {
                fillValues(node.get("values"), valuesMap);
            }
        }
    }
}
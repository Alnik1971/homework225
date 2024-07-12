package ru.netology;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sun.jdi.Type;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "C:\\Users\\a.y.nikitin\\IdeaProjects\\untitled39\\src\\main\\java\\ru\\netology\\data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        list.forEach(System.out::println);
        String json = listToJson(list);
        System.out.println(json);
        try {
            //FileWriter fw = new FileWriter("C:\\Users\\a.y.nikitin\\IdeaProjects\\untitled39\\src\\main\\java\\ru\\netology\\data.json");
            FileOutputStream fos = new FileOutputStream("C:\\Users\\a.y.nikitin\\IdeaProjects\\untitled39\\src\\main\\java\\ru\\netology\\data.json");
            byte[] bytes = json.getBytes();
            fos.write(bytes,0, bytes.length);

            //fw.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fileName2 = "C:\\Users\\a.y.nikitin\\IdeaProjects\\untitled39\\src\\main\\java\\ru\\netology\\data.xml";
        List<Employee> list2 = parseXML(fileName2);
        String json2 = listToJson(list2);
        try {
            //FileWriter fw = new FileWriter("C:\\Users\\a.y.nikitin\\IdeaProjects\\untitled39\\src\\main\\java\\ru\\netology\\data.json");
            FileOutputStream fos = new FileOutputStream("C:\\Users\\a.y.nikitin\\IdeaProjects\\untitled39\\src\\main\\java\\ru\\netology\\data2.json");
            byte[] bytes = json2.getBytes();
            fos.write(bytes,0, bytes.length);

            //fw.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static List<Employee> parseXML(String fileName) {
        List<Employee> le = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = factory.newDocumentBuilder();
            Document doc = documentbuilder.parse(new File(fileName));
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element employee = (Element) node;
                    NamedNodeMap map = employee.getAttributes();
                    le.add(new Employee(Long.parseLong(map.item(0).getNodeValue()), map.item(1).getNodeValue(), map.item(2).getNodeValue(),
                            map.item(3).getNodeValue(), Integer.parseInt(map.item(4).getNodeValue())));
                }
            }
            return le;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


    }
    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(fileName));
            ColumnPositionMappingStrategy<Employee> cpms = new ColumnPositionMappingStrategy<>();
            cpms.setType(Employee. class);
            cpms.setColumnMapping(columnMapping);
            CsvToBean<Employee> ctb = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(cpms)
                    .build();
            return ctb.parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        //Type listType = new TypeToken<List<T>>() {}.getType();
        String json = gson.toJson(list);
        return json;
    }
}



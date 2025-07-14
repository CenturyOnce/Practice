import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private final ObjectMapper mapper;
    private ArrayList<Media> mediaList;
    SQLiteManager sqlite = new SQLiteManager("media.db");

    public FileManager(ObjectMapper mapper, ArrayList<Media> mediaList){
        this.mapper = mapper;
        this.mediaList = mediaList;
    }

    public void readFromFile(String file) throws IOException, SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        List<Media> parsedList = mapper.readValue(
                new File(file),
                mapper.getTypeFactory().constructCollectionType(List.class, Media.class)
        );

        System.out.println("Прочитанный JSON: " + mapper.writeValueAsString(parsedList));
        while(choice < 1 || choice > 5) {
            System.out.println("Хотите ли вы сделать что-либо с этими данными?" +
                    "\n1 - Заменить содержимое текущего списка данными" +
                    "\n2 - Добавить данные в текущий список" +
                    "\n3 - Заменить содержимое БД данными" +
                    "\n4 - Добавить данные в текущую БД" +
                    "\n5 - Нет");
            choice = scanner.nextInt();
            if(choice == 1){
                mediaList.clear();
                for(Media media : parsedList) mediaList.add(media);
                System.out.println("Данные заменены");
            } else if(choice == 2){
                int addedCount = 0;

                for (Media newMedia : parsedList) {
                    if (!mediaList.contains(newMedia)) {
                        newMedia.setId(mediaList.size()+1);
                        mediaList.add(newMedia);
                        sqlite.addMedia(newMedia);
                        addedCount++;
                    }
                }

                System.out.println("Добавлено новых объектов: " + addedCount);
            } else if(choice == 3){
                sqlite.clearAllTables();
                for(Media media : parsedList) sqlite.addMedia(media);
                System.out.println("Данные успешно заменены");
            } else if(choice == 4){
                for(Media media : parsedList) sqlite.addMedia(media);
                sqlite.addedAndChanged();
                System.out.println("Данные успешно добавлены");
            } else if(choice == 5) break;
            else System.out.println("Такой опции нет!");
        }

    }

    public void writeInFile(String file, ArrayList<Media> list) throws IOException{
        ArrayNode jsonArray = mapper.valueToTree(list);

        for (JsonNode node : jsonArray) {
            ObjectNode mediaNode = (ObjectNode) node;
            mediaNode.put("mediaType", mediaNode.get("type").asText());
        }

        mapper.writeValue(new File(file), jsonArray);
        System.out.println("Данные успешно записаны!");
    }

    public List<String> findJsonFiles(String directory){
        List<String> jsonFiles = new ArrayList<>();

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "*.json")){
            for(Path entry : stream){
                if(Files.isRegularFile(entry)) jsonFiles.add(entry.getFileName().toString());
            }
        } catch (IOException e){
            System.err.println("Ошибка при чтении директории: " + e.getMessage());
        }
        return jsonFiles;
    }
}

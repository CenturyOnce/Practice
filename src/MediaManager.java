import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MediaManager {
    private final ObjectMapper mapper;
    private ArrayList<Media> mediaList;
    static SQLiteManager sqlite = new SQLiteManager("media.db");

    public MediaManager(ObjectMapper mapper, ArrayList<Media> mediaList){
        this.mapper = mapper;
        this.mediaList = mediaList;
    }

    public void readFromFile(int choice) throws IOException, SQLException {
        Scanner scanner = new Scanner(System.in);
        List<Media> parsedList = mapper.readValue(
                new File("media_library.json"),
                mapper.getTypeFactory().constructCollectionType(List.class, Media.class)
        );

        System.out.println("Прочитанный JSON: " + mapper.writeValueAsString(parsedList));
        while(choice < 1 || choice > 3) {
            System.out.println("Хотите ли вы сделать что-либо с этими данными?" +
                    "\n1 - Заменить содержимое списка данными" +
                    "\n2 - Добавить данные в список" +
                    "\n3 - Нет");
            choice = scanner.nextInt();
            if(choice == 1){
                mediaList.clear();
                for(Media media : parsedList) mediaList.add(media);
                System.out.println("Данные заменены");
            } else if(choice == 2){
                int addedCount = 0;

                for (Media newMedia : parsedList) {
                    if (!mediaList.contains(newMedia)) {
                        newMedia.setId(mediaList.size());
                        mediaList.add(newMedia);
                        sqlite.addMedia(newMedia);
                        addedCount++;
                    }
                }

                System.out.println("Добавлено новых объектов: " + addedCount);
            } else if(choice == 3) break;
            else System.out.println("Такой опции нет!");
        }

    }

    public void writeInFile() throws IOException{
        ArrayNode jsonArray = mapper.valueToTree(mediaList);

        for (JsonNode node : jsonArray) {
            ObjectNode mediaNode = (ObjectNode) node;
            mediaNode.put("mediaType", mediaNode.get("type").asText());
        }

        mapper.writeValue(new File("media_library.json"), jsonArray);
        System.out.println("Данные успешно записаны!");
    }

    public static boolean doesMediaExist(ArrayList<Media> mediaList, String type){
        int mediaCount = 0;
        for(Media media : mediaList){
            if(media.getType().equals(type)) mediaCount += 1;
        }
        if(mediaCount == 0) {
            System.out.println("Объектов с таким типом в списке нет.");
            return false;
        }
        return true;
    }

    public static void getAllMediaType(ArrayList<Media> mediaList, String type){
        if(!doesMediaExist(mediaList, type)) return;
        for(Media media : mediaList){
            if(media.getType().equals(type)) media.getInfo();
        }
    }

    public static void printMedia(ArrayList<Media> mediaList, String question, String type){
        if(!doesMediaExist(mediaList, type)) return;
        System.out.println(question);
        for(Media media : mediaList){
            if(media.getType().equals(type)) media.getIdName();
        }
    }
    public static int mediaChoice(int choice){
        Scanner scanner = new Scanner(System.in);
        while(choice < 0 || choice >2){
            System.out.println("С чем вы хотите выполнить действие?" +
                    "\n1 - Книга" +
                    "\n2 - Фильм");
            choice = scanner.nextInt();
        }
        return choice;
    }

    public static void deleteMedia(ArrayList<Media> mediaList, String type) throws SQLException {
        if(!doesMediaExist(mediaList, type)) return;
        int id = -1;
        while(id < 0 || id > mediaList.size()){
            printMedia(mediaList, "Какой объект вы хотите удалить?", type);
            id = idCheck(mediaList, type);
        }
        sqlite.deleteMedia(id);
        mediaList.remove(id-1);
        for (Media media : mediaList) sqlite.updateMedia(media);
        System.out.println("Объект успешно удалён.");
    }

    public static int idCheck(ArrayList<Media> mediaList, String type){
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        if(!mediaList.get(id-1).getType().equals(type)){
            System.out.println("Объекта выбранного типа с таким id нет!");
            id = -1;
        }
        return id;
    }

    public static void addMedia(ArrayList<Media> mediaList, String type) throws SQLException{
        int id = mediaList.size();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название: ");
        String name = scanner.nextLine().trim();
        System.out.println("Введите количество жанров: ");
        int amountOfGenres = scanner.nextInt();
        System.out.println("Введите жанры(" + amountOfGenres + ", каждый через Enter): ");
        scanner.nextLine();
        Set<String> genres = new HashSet<>();
        for(int i = 0; i < amountOfGenres; i++){
            String genre = scanner.nextLine().trim();
            genres.add(genre);
        }
        System.out.println("Введите год выхода: ");
        int pubYear = scanner.nextInt();
        System.out.println("Введите среднюю оценку: ");
        double rating = scanner.nextDouble();
        scanner.nextLine();
        if(type.equals("Книга")){
            System.out.println("Введите издателя: ");
            String publisher = scanner.nextLine().trim();
            System.out.println("Введите кол-во страниц: ");
            int amountOfPages = scanner.nextInt();
            System.out.println("Введите автора книги: ");
            scanner.nextLine();
            String author = scanner.nextLine().trim();
            Book book = new Book(id, name, author, genres, pubYear, publisher, amountOfPages, rating);
            mediaList.add(book);
            sqlite.addMedia(book);
        } else if(type.equals("Фильм")) {
            System.out.println("Введите кол-во создателей: ");
            int creatorsNum = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Введите создателей(" + creatorsNum + ", каждый через Enter): ");
            ArrayList<String> creators = new ArrayList<>();
            for(int i = 0; i < creatorsNum; i++){
                String creator = scanner.nextLine().trim();
                creators.add(creator);
            }
            System.out.println("Введите продолжительность фильма(в минутах): ");
            int durationMinutes = scanner.nextInt();
            Film film = new Film(id, name, genres, pubYear, creators, durationMinutes, rating);
            mediaList.add(film);
            sqlite.addMedia(film);
        }
    }

    public static void changeMediaMenu(ArrayList<Media> mediaList, String type){
        if(!doesMediaExist(mediaList, type)) return;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Что вы хотите изменить?" +
                "\n1 - Название" +
                "\n2 - Жанры" +
                "\n3 - Год выхода" +
                "\n4 - Средняя оценка");
        if(type.equals("Книга")) System.out.println("5 - Автор" +
                "\n6 - Издатель" +
                "\n7 - Кол-во страниц");
        else if (type.equals("Фильм")) {
            System.out.println("5 - Создатели" + "\n6 - Продолжительность фильма");
        }
        int opt = scanner.nextInt();
        int id = -1;
        if(opt == 1){
            while(id < 0 || id > mediaList.size()){
                printMedia(mediaList, "Название какого объекта вы хотите изменить?", type);
                id = idCheck(mediaList, type);
            }
            System.out.println("Напишите новое название объекиа: ");
            scanner.nextLine();
            String newName = scanner.nextLine().trim();
            mediaList.get(id-1).setName(newName);
        } else if(opt == 2){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Жанры какого объекта вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName() +
                            " - " + media.getGenres());
                }
                id = idCheck(mediaList, type);
            }
            mediaList.get(id-1).changeGenres();
        } else if(opt == 3){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Год выхода какго объекта вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName() +
                            " - " + media.getYear());
                }
                id = idCheck(mediaList, type);
            }
            System.out.println("Введите новый год выпуска: ");
            int newYear = scanner.nextInt();
            mediaList.get(id-1).setPubYear(newYear);
        } else if(opt == 4){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Среднюю оценку какого объекта вы хотите изменить?");
                for(Media media : mediaList)  System.out.println(media.getId() + " - " + media.getName() +
                        " - " + media.getRating());
                id = idCheck(mediaList, type);
            }
            System.out.println("Введите новую среднюю оценку: ");
            double newRating = scanner.nextDouble();
            mediaList.get(id-1).setRating(newRating);
        } else if(opt == 5 && type.equals("Фильм")){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Создателей какго фильма вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName() + " - "
                            + ((Film) media).getCreators());
                }
                id = idCheck(mediaList, type);
            }
            final Media currentMedia = mediaList.get(id-1);
            ((Film) currentMedia).changeCreators();
        } else if(opt == 6 && type.equals("Фильм")){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Продолжительность какго фильма вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName() + " - "
                            + ((Film) media).getDuration());
                }
                id = idCheck(mediaList, type);
            }
            System.out.println("Введите новую продолжительность фильма(в минутах): ");
            int minutes = scanner.nextInt();
            final Media currentMedia = mediaList.get(id-1);
            ((Film) currentMedia).setDuration(minutes);
        } else if(opt == 5 && type.equals("Книга")){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Автора какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type))System.out.println(media.getId() + " - " + media.getName() + " - " +
                            ((Book) media).getAuthor());
                }
                id = idCheck(mediaList, type);
            }
            System.out.println("Введите нового автора книги: ");
            scanner.nextLine();
            String newAuthor = scanner.nextLine().trim();
            final Media currentMedia = mediaList.get(id-1);
            ((Book) currentMedia).setAuthor(newAuthor);
        } else if(opt == 6 && type.equals("Книга")){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Издателя какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName()
                            + " - " + ((Book) media).getPublisher());
                }
                id = idCheck(mediaList, type);
            }
            System.out.println("Введите нового издатедя: ");
            scanner.nextLine();
            String newPublisher = scanner.nextLine().trim();
            final Media currentMedia = mediaList.get(id-1);
            ((Book) currentMedia).setPublisher(newPublisher);

        } else if(opt == 7 && type.equals("Книга")){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Кол-во страниц какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName() +
                            " - " +((Book) media).getPages());
                }
                id = idCheck(mediaList, type);
            }
            System.out.println("Введите новое кол-во страниц: ");
            int pages = scanner.nextInt();
            final Media currentMedia = mediaList.get(id-1);
            ((Book) currentMedia).setPages(pages);
        }
        else{
            System.out.println("Такой опции нет!");
            return;
        }
        sqlite.updateMedia(mediaList.get(id-1));
    }
}

import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class Main {
    public static void getAllMediaType(ArrayList<Media> mediaList, String type){
        for(Media media : mediaList){
            if(media.getType().equals(type)) media.getInfo();
        }
    }

    public static void addBook(ArrayList<Media> mediaList){
        int id = mediaList.size();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введиет название книги: ");
        String name = scanner.nextLine();
        System.out.println("Введите автора книги: ");
        String author = scanner.nextLine();
        System.out.println("Введите количество жанров: ");
        int amountOfGenres = scanner.nextInt();
        System.out.println("Введите жанры(" + amountOfGenres + ", каждый через Enter): ");
        scanner.nextLine();
        ArrayList<String> genres = new ArrayList<>();
        for(int i = 0; i < amountOfGenres; i++){
            String genre = scanner.nextLine();
            genres.add(genre);
        }
        System.out.println("Введите год выхода: ");
        int pubYear = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите издателя: ");
        String publisher = scanner.nextLine();
        System.out.println("Введите кол-во страниц: ");
        int amountOfPages = scanner.nextInt();
        System.out.println("Введите среднюю оценку: ");
        double rating = scanner.nextDouble();

        mediaList.add(new Book("Книга", id, name, author, genres, pubYear, publisher, amountOfPages, rating));
    }
    public static void addFilm(ArrayList<Media> mediaList){
        int id = mediaList.size();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введиет название фильма: ");
        String name = scanner.nextLine();
        System.out.println("Введите количество жанров: ");
        int amountOfGenres = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите жанры(" + amountOfGenres + ", каждый через Enter): ");
        ArrayList<String> genres = new ArrayList<>();
        for(int i = 0; i < amountOfGenres; i++){
            String genre = scanner.nextLine();
            genres.add(genre);
        }
        System.out.println("Введите год выхода: ");
        int pubYear = scanner.nextInt();
        System.out.println("Введите кол-во создателей: ");
        int creatorsNum = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите создателей(" + creatorsNum + ", каждый через Enter): ");
        ArrayList<String> creators = new ArrayList<>();
        for(int i = 0; i < creatorsNum; i++){
            String creator = scanner.nextLine();
            creators.add(creator);
        }
        System.out.println("Введите продолжительность фильма(в минутах): ");
        int durationMinutes = scanner.nextInt();
        System.out.println("Введите среднюю оценку: ");
        double rating = scanner.nextDouble();

        mediaList.add(new Film("Фильм", id, name, genres, pubYear, creators, durationMinutes, rating));
    }

    public static void showMenu(){
        System.out.println("Вы находитесь в медиатеке. Что вы хотите сделать?" +
                "\n0 - Посмотреть все объекты" +
                "\n1 - Получить информацию о книгах/фильмах" +
                "\n2 - Добавить книгу/фильм" +
                "\n3 - Изменить информацию о книге/фильме" +
                "\n4 - Удалить книгу/фильм" +
                "\n5 - Обновить файл JSON" +
                "\n6 - Считать информацию с файла" +
                "\n7 - Выход");
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

    public static int printMedia(ArrayList<Media> mediaList, String question, int id, String type){
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        for(Media media : mediaList){
            if(media.getType().equals(type)) media.getIdName();
        }
        return idCheck(mediaList, id, type);
    }

    public static int idCheck(ArrayList<Media> mediaList, int id, String type){
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextInt();
        if(!mediaList.get(id).getType().equals(type)){
            System.out.println("Объекта выбранного типа с таким id нет!");
            id = -1;
        }
        return id;
    }

    public static void changeBookMenu(ArrayList<Media> mediaList, String type){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Что вы хотите изменить?" +
                "\n1 - Название" +
                "\n2 - Автор" +
                "\n3 - Жанры" +
                "\n4 - Год выхода" +
                "\n5 - Издатель" +
                "\n6 - Кол-во страниц" +
                "\n7 - Средняя оценка");
        int opt = scanner.nextInt();
        int id = -1;
        if(opt == 1){
            while(id < 0 || id > mediaList.size()){
                id = printMedia(mediaList, "Название какой книги вы хотите изменить?", id, "Книга");
            }
            System.out.println("Напишите новое название книги: ");
            scanner.nextLine();
            String newName = scanner.nextLine();
            mediaList.get(id).setName(newName);
        } else if(opt == 2){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Автора какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type))System.out.println(media.getId() + " - " + media.getName() + " - " + media.getAuthor());
                }
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Напишите новое название книги: ");
            scanner.nextLine();
            String newAuthor = scanner.nextLine();
            mediaList.get(id).setAuthor(newAuthor);
        } else if(opt == 3){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Жанры какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) media.getIdGenres();
                }
                id = idCheck(mediaList, id, type);
            }
            mediaList.get(id).changeGenres();
        } else if (opt == 4) {
            while(id < 0 || id > mediaList.size()){
                System.out.println("Год выхода какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) media.getIdYear();
                }
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите новый год выпуска книги: ");
            int newYear = scanner.nextInt();
            mediaList.get(id).setPubYear(newYear);
        } else if (opt == 5){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Издателя какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) media.getIdPublisher();
                }
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите нового издатедя: ");
            scanner.nextLine();
            String newPublisher = scanner.nextLine();
            mediaList.get(id).setPublisher(newPublisher);
        } else if (opt == 6){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Кол-во страниц какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) media.getIdPages();
                }
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите новое кол-во страниц: ");
            int pages = scanner.nextInt();
            mediaList.get(id).setPages(pages);
        } else if (opt == 7){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Среднюю оценку какой книги вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) media.getIdRating();
                }
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите новую среднюю оценку: ");
            double newRating = scanner.nextDouble();
            mediaList.get(id).setRating(newRating);
        } else System.out.println("Такой опции нет!");
    }
    public static void changeFilmMenu(ArrayList<Media> mediaList, String type){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Что вы хотите изменить?" +
                "\n1 - Название" +
                "\n2 - Жанры" +
                "\n3 - Год выхода" +
                "\n4 - Создаели" +
                "\n5 - Продолжительность фильма" +
                "\n6 - Средняя оценка");
        int opt = scanner.nextInt();
        int id = -1;
        if(opt == 1){
            while(id < 0 || id > mediaList.size()){
                id = printMedia(mediaList, "Название какого фильма вы хотите изменить?", id, "Фильм");
            }
            System.out.println("Напишите новое название фильма: ");
            scanner.nextLine();
            String newName = scanner.nextLine();
            mediaList.get(id).setName(newName);
        } else if(opt == 2){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Жанры какого фильма вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) media.getIdGenres();
                }
                id = idCheck(mediaList, id, type);
            }
            mediaList.get(id).changeGenres();
        } else if(opt == 3){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Год выхода какго фильма вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) media.getIdYear();
                }
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите новый год выпуска фильма: ");
            int newYear = scanner.nextInt();
            mediaList.get(id).setPubYear(newYear);
        } else if(opt == 4){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Создателей какго фильма вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName() + " - " + media.getCreators());
                }
                id = idCheck(mediaList, id, type);
            }
            mediaList.get(id).changeCreators();
        } else if(opt == 5){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Продолжительность какго фильма вы хотите изменить?");
                for(Media media : mediaList) {
                    if(media.getType().equals(type)) System.out.println(media.getId() + " - " + media.getName() + " - " + media.getDuration());
                }
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите новую продолжительность фильма(в минутах): ");
            int minutes = scanner.nextInt();
            mediaList.get(id).setDuration(minutes);
        } else if(opt == 6){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Среднюю оценку какого фильма вы хотите изменить?");
                for(Media media : mediaList)  media.getIdRating();
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите новую среднюю оценку: ");
            double newRating = scanner.nextDouble();
            mediaList.get(id).setRating(newRating);
        } else System.out.println("Такой опции нет!");
    }

    public static void deleteMedia(ArrayList<Media> mediaList, String type){
        Scanner scanner = new Scanner(System.in);
        int id = -1;
        while(id < 0 || id > mediaList.size()){
            id = printMedia(mediaList, "Какой фильм вы хотите удалить", id, type);
        }
        mediaList.remove(id);
        for(int i = 0; i < mediaList.size(); i++) mediaList.get(i).setId(i);
        System.out.println("Фильм успешно удалён.");
    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Scanner scanner = new Scanner(System.in);

        ArrayList<Media> mediaList = new ArrayList<>();
        mediaList.add(new Book("Книга", mediaList.size(), "Мистик", "Фрэнк Оувэл", new ArrayList<>(Arrays.asList("Детектив", "Мистика", "Комедия")),
                2016, "Букинист", 235, 4.5));
        mediaList.add(new Film("Фильм",mediaList.size(), "Бесконечная история", new ArrayList<>(Arrays.asList("Приключения", "Фэнтези", "Комедия", "Экшен")),
                2019, new ArrayList<>(Arrays.asList("Нэйтан Блэк", "Брайс Папенбрук")),153, 4.2));
        mediaList.add(new Film("Фильм",mediaList.size(), "На другой стороне", new ArrayList<>(Arrays.asList("Приключения", "Хоррор", "Экшен")),
                2021, new ArrayList<>(Arrays.asList("Кристи Голд", "Майкл Ковак")),210, 3.7));

        int option = -1;
        while(option != 7) {

            int choice = -1;
            showMenu();

            option = scanner.nextInt();
            switch (option){
                case 0:
                    for(Media media : mediaList) media.getInfo();
                    break;
                case 1:
                    choice = mediaChoice(choice);
                    if(choice == 1) getAllMediaType(mediaList, "Книга");
                    else if(choice == 2) getAllMediaType(mediaList, "Фильм");
                    break;
                case 2:
                    choice = mediaChoice(choice);
                    if(choice == 1) addBook(mediaList);
                    else if(choice == 2) addFilm(mediaList);
                    break;
                case 3:
                    choice = mediaChoice(choice);
                    if(choice == 1) changeBookMenu(mediaList, "Книга");
                    else if(choice == 2) changeFilmMenu(mediaList, "Фильм");
                    break;
                case 4:
                    choice = mediaChoice(choice);
                    if(choice == 1) deleteMedia(mediaList, "Книга");
                    else if(choice == 2) deleteMedia(mediaList, "Фильм");
                    break;
                case 5:
                    try {
                        ArrayNode jsonArray = mapper.valueToTree(mediaList);

                        for (JsonNode node : jsonArray) {
                            ObjectNode mediaNode = (ObjectNode) node;
                            mediaNode.put("mediaType", mediaNode.get("type").asText());
                        }

                        mapper.writeValue(new File("media_library.json"), jsonArray);
                        System.out.println("Данные успешно записаны!");
                    } catch (IOException e) {
                        System.err.println("Ошибка при записи файла: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    try {
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
                                mediaList = new ArrayList<>(parsedList);
                                System.out.println("Данные занесены в список");
                            } else if(choice == 2){
                                int addedCount = 0;

                                for (Media newMedia : parsedList) {
                                    if (!mediaList.contains(newMedia)) {
                                        newMedia.setId(mediaList.size());
                                        mediaList.add(newMedia);
                                        addedCount++;
                                    }
                                }

                                System.out.println("Добавлено новых объектов: " + addedCount);
                            } else if(choice == 3) break;
                            else System.out.println("Такой опции нет!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Такой опции нет! Введите другую!");
            }
        }

    }
}
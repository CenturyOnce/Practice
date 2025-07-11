import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class Main {
    public static void showMenu(){
        String menu = "Вы находитесь в медиатеке. Что вы хотите сделать?" +
                "\n0 - Посмотреть все объекты" +
                "\n1 - Получить информацию о книгах/фильмах" +
                "\n2 - Добавить книгу/фильм" +
                "\n3 - Изменить информацию о книге/фильме" +
                "\n4 - Удалить книгу/фильм" +
                "\n5 - Обновить файл JSON" +
                "\n6 - Считать информацию с файла" +
                "\n7 - Выход" +
                "\n8 - Очистить таблицы" +
                "\n9 - Вывести данные из таблицы" +
                "\n10 - Записать данные в таблицы" +
                "\n11 - Поиск в датабазе";
        System.out.println(new String(menu.getBytes(), StandardCharsets.UTF_8));
    }

    public static void pauseProgram(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Нажмите Enter чтобы продолжить...");
        scanner.nextLine();
    }

    public static void main(String[] args) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Scanner scanner = new Scanner(System.in);

        SQLiteManager sqlLite = new SQLiteManager("media.db");

        ArrayList<Media> mediaList = new ArrayList<>();
        mediaList.add(new Book(mediaList.size()+1, "Мистик", "Фрэнк Оувэл", new HashSet<>(Set.of("Детектив", "Мистика", "Комедия")),
                2016, "Букинист", 235, 4.5));
        mediaList.add(new Film(mediaList.size()+1, "Бесконечная история", new HashSet<>(Set.of("Приключения", "Фэнтези", "Комедия", "Экшен")),
                2019, new ArrayList<>(Arrays.asList("Нэйтан Блэк", "Брайс Папенбрук")),153, 4.2));
        mediaList.add(new Film(mediaList.size()+1, "На другой стороне", new HashSet<>(Set.of("Приключения", "Хоррор", "Экшен")),
                2021, new ArrayList<>(Arrays.asList("Кристи Голд", "Майкл Ковак")),210, 3.7));
        int option = -1;
        while(option != 7) {
            int choice = -1;
            showMenu();
            MediaManager manager = new MediaManager(mapper, mediaList);

            option = scanner.nextInt();
            switch (option){
                case 0:
                    for(Media media : mediaList) media.getInfo();
                    pauseProgram();
                    break;
                case 1:
                    choice = manager.mediaChoice(choice);
                    if(choice == 1) manager.getAllMediaType(mediaList, Book.TYPE);
                    else if(choice == 2) manager.getAllMediaType(mediaList, Film.TYPE);
                    pauseProgram();
                    break;
                case 2:
                    choice = manager.mediaChoice(choice);
                    if(choice == 1) manager.addMedia(mediaList, Book.TYPE);
                    else if(choice == 2) manager.addMedia(mediaList, Film.TYPE);
                    break;
                case 3:
                    choice = manager.mediaChoice(choice);
                    if(choice == 1) manager.changeMediaMenu(mediaList, Book.TYPE);
                    else if(choice == 2) manager.changeMediaMenu(mediaList, Film.TYPE);
                    break;
                case 4:
                    choice = manager.mediaChoice(choice);
                    if(choice == 1) MediaManager.deleteMedia(mediaList, Book.TYPE);
                    else if(choice == 2) MediaManager.deleteMedia(mediaList, Film.TYPE);
                    break;
                case 5:
                    while(choice < 0 || choice > 2){
                        System.out.println("Вы точно хотите изменить содержание файла?" +
                                "\n1 - Да" +
                                "\n2 - Нет");
                        choice = scanner.nextInt();
                        if(choice == 1) manager.writeInFile();
                        else if(choice == 2) break;
                    }
                    break;
                case 6:
                    manager.readFromFile(choice);
                    break;
                case 7:
                    System.exit(0);
                    break;
                case 8:
                    while(choice < 0 || choice > 2){
                        System.out.println("Вы точно хотите очистить таблицы?" +
                                "\n1 - Да" +
                                "\n2 - Нет");
                        choice = scanner.nextInt();
                        if(choice == 1) sqlLite.clearAllTables();
                        else if(choice == 2) break;
                    }
                    break;
                case 9:
                    List<Media> sqlList = sqlLite.getAllMedia();
                    for (Media media : sqlList) media.getInfo();
                    pauseProgram();
                    break;
                case 10:
                    sqlLite.clearAllTables();
                    for (Media media : mediaList) {
                        sqlLite.addMedia(media);
                    }
                    System.out.println("Таблицы были успешно обновлены.");
                    break;
                case 11:
                    System.out.println("Введите какой-либо термин, по которому можно найти объекты в таблице: ");
                    scanner.nextLine();
                    String searchTerm = "%" + scanner.nextLine().trim() + "%";
                    List<Media> sqlSearchList = sqlLite.searchByName(searchTerm);
                    if(sqlSearchList.size() == 0) System.out.println("Совпадений нет.");
                    else for (Media media : sqlSearchList) media.getInfo();
                    pauseProgram();
                    break;
                default:
                    System.out.println("Такой опции нет! Введите другую!");
            }
        }

    }
}
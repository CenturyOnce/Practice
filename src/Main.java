import java.sql.SQLException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class Main {
    public static void showMenu(){
        System.out.println("Вы находитесь в медиатеке. Что вы хотите сделать?" +
                "\n0 - Посмотреть все объекты" +
                "\n1 - Получить информацию о книгах/фильмах" +
                "\n2 - Добавить книгу/фильм" +
                "\n3 - Изменить информацию о книге/фильме" +
                "\n4 - Удалить книгу/фильм" +
                "\n5 - Обновить файл JSON" +
                "\n6 - Считать информацию с файла" +
                "\n7 - Выход" +
                "\n8 - Очистить таблицы" +
                "\n9 - Вывести данные из таблицы");
    }

    public static void main(String[] args) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Scanner scanner = new Scanner(System.in);

        SQLiteManager sqlLite = new SQLiteManager("media.db");

        ArrayList<Media> mediaList = new ArrayList<>();
        mediaList.add(new Book(mediaList.size(), "Мистик", "Фрэнк Оувэл", new HashSet<>(Set.of("Детектив", "Мистика", "Комедия")),
                2016, "Букинист", 235, 4.5));
        mediaList.add(new Film(mediaList.size(), "Бесконечная история", new HashSet<>(Set.of("Приключения", "Фэнтези", "Комедия", "Экшен")),
                2019, new ArrayList<>(Arrays.asList("Нэйтан Блэк", "Брайс Папенбрук")),153, 4.2));
        mediaList.add(new Film(mediaList.size(), "На другой стороне", new HashSet<>(Set.of("Приключения", "Хоррор", "Экшен")),
                2021, new ArrayList<>(Arrays.asList("Кристи Голд", "Майкл Ковак")),210, 3.7));
        for(int i = 0; i < mediaList.size(); i++){
            sqlLite.addMedia(mediaList.get(i));
        }
        int option = -1;
        while(option != 7) {
            int choice = -1;
            showMenu();
            MediaManager manager = new MediaManager(mapper, mediaList);

            option = scanner.nextInt();
            switch (option){
                case 0:
                    for(Media media : mediaList) media.getInfo();
                    break;
                case 1:
                    choice = manager.mediaChoice(choice);
                    if(choice == 1) manager.getAllMediaType(mediaList, Book.TYPE);
                    else if(choice == 2) manager.getAllMediaType(mediaList, Film.TYPE);
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
                    manager.writeInFile();
                    break;
                case 6:
                    manager.readFromFile(choice);
                    break;
                case 7:
                    System.exit(0);
                    break;
                case 8:
                    sqlLite.clearAllTables();
                    break;
                case 9:
                    sqlLite.getAllMedia(); //List создай здесь
                    break;
                default:
                    System.out.println("Такой опции нет! Введите другую!");
            }
        }

    }
}
import java.io.File;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

abstract class Media{
    protected int id;
    protected String type;
    protected String name;
    protected ArrayList<String> genres;
    protected int pubYear;
    protected double rating;

    public Media(){}
    public Media(String type, int id, String name, ArrayList<String> genres, int pubYear, double rating){
        this.type = type;
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.pubYear = pubYear;
        this.rating = rating;
    }

    public void getInfo(){
        System.out.println("ID: " + id + "\nТип: " + type + "\nНазвание: " + name + "\nЖанры: " + genres + "\nГод выхода: " + pubYear +
                "\nСредняя оценка: " + rating);
        getSecondaryInfo();
    } void getSecondaryInfo(){}

    void getIdName(){
        System.out.println(id + " - " + name);
    }
    String getAuthor(){ return ""; }
    void changeAuthor(String author){}
    void getIdPublisher(){}
    void changePublisher(String publisher){}
    void getIdPages(){}
    void changePages(int amountOfPages){}
    ArrayList<String> getCreators(){ return genres; }
    void changeCreators(){}
    int getDuration(){ return 0; }
    public void changeDuration(int durationMinutes){}
    public void getIdYear(){
        System.out.println(id + " - " + name + " - " + pubYear);
    }
    public void getIdGenres(){
        System.out.println(id + " - " + name + " - " + getGenres());
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return String.format(name);
    } public void changeName(String name){
        this.name = name;
    }

    public int getYear(){
        return pubYear;
    } public void changeYear(int year){
        pubYear = year;
    }

    public void getIdRating(){
        System.out.println(id  + " - " + name + " - " + rating);
    }
    public double getRating(){
        return rating;
    } public void changeRating(double rating){
        this.rating = rating;
    }

    public int getId(){
        return id;
    } public void updateId(ArrayList<Media> mediaList){
        id = mediaList.size() - 1;
    }

    public String getGenres(){
        return String.format(String.join(", ", genres));
    } public void changeGenres(){
        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        while(opt <= 0 || opt > 3){
            System.out.println("Что вы хотите изменить в жанрах?\n" +
                    "1 - Удалить жанр\n" +
                    "2 - Добавить жанр\n" +
                    "3 - Заменить жанр");
            opt = scanner.nextInt();
        }
        int del = 0;
        if(opt == 1){
            while(del <= 0 || del > genres.size()) {
                System.out.println("Какой жанр хотите удалить?");
                for (int i = 1; i < genres.size() + 1; i++) {
                    System.out.println(i + " - " + genres.get(i - 1));
                }
                del = scanner.nextInt();
            }
            genres.remove(del - 1);
        } else if(opt == 2){
            System.out.println("Введите жанр, который хотите добавить: ");
            scanner.nextLine();
            String newGenre = scanner.nextLine();
            genres.add(newGenre);
        } else if(opt == 3){
            while(del <= 0 || del > genres.size()) {
                System.out.println("Какой жанр хотите заменить?");
                for (int i = 1; i < genres.size() + 1; i++) {
                    System.out.println(i + " - " + genres.get(i - 1));
                }
                del = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println("Введите новый жанр для замены: ");
            String newGenre = scanner.nextLine();
            genres.set(del-1, newGenre);
        }
    }

}

class Book extends Media{
    private String author;
    private String publisher;
    private int amountOfPages;

    public Book(){}
    public Book(String type, int id, String name, String author, ArrayList<String> genres, int pubYear,
                String publisher, int amountOfPages, double rating){
        super(type, id, name, genres, pubYear, rating);
        this.author = author;
        this.publisher = publisher;
        this.amountOfPages = amountOfPages;
    }

    @Override
    public void getSecondaryInfo(){
        System.out.println("Автор: " + author + "\nИздательство: " + publisher + "\nКол-во страниц: " + amountOfPages);
    }

    @Override
    public String getAuthor(){
        return author;
    }
    @Override
    public void changeAuthor(String author){
        this.author = author;
    }

    @Override
    public void getIdPublisher(){
        System.out.println(id + " - " + name + " - " + publisher);
    }
    public String getPublisher(){
        return publisher;
    }
    @Override
    public void changePublisher(String publisher){
        this.publisher = publisher;
    }

    @Override
    public void getIdPages(){
        System.out.println(id + " - " + name + " - " + amountOfPages);
    }

    public int getPages(){
        return amountOfPages;
    }
    @Override
    public void changePages(int amountOfPages){
        this.amountOfPages = amountOfPages;
    }

    @Override
    public void getIdGenres(){
        System.out.println(id + " - " + getName() + " - " + getGenres());
    }
}

class Film extends Media{
    private ArrayList<String> creators;
    private int durationMinutes;

    public Film(){}
    public Film(String type, int id, String name, ArrayList<String> genres, int pubYear, ArrayList<String> creators, int durationMinutes, double rating){
        super(type, id, name, genres, pubYear, rating);
        this.creators = creators;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public void getSecondaryInfo(){
        System.out.println("Создатели: " + creators + "\nПродолжительность(в минутах): " + durationMinutes);
    }

    @Override
    public int getDuration(){
        return durationMinutes;
    }
    @Override
    public void changeDuration(int durationMinutes){
        this.durationMinutes = durationMinutes;
    }

    @Override
    public ArrayList<String> getCreators(){
        return creators;
    }
    @Override
    public void changeCreators(){
        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        while(opt <= 0 || opt > 3){
            System.out.println("Что вы хотите изменить в создателях?\n" +
                    "1 - Удалить создателя\n" +
                    "2 - Добавить создателя\n" +
                    "3 - Заменить создателя");
            opt = scanner.nextInt();
        }
        int del = 0;
        if(opt == 1){
            while(del <= 0 || del >  creators.size()) {
                System.out.println("Какого создателя хотите удалить?");
                for (int i = 1; i < creators.size() + 1; i++) {
                    System.out.println(i + " - " + creators.get(i - 1));
                }
                del = scanner.nextInt();
            }
            creators.remove(del - 1);
        } else if(opt == 2){
            System.out.println("Введите создателя, которого хотите добавить: ");
            scanner.nextLine();
            String newGenre = scanner.nextLine();
            creators.add(newGenre);
        } else if(opt == 3){
            while(del <= 0 || del > creators.size()) {
                System.out.println("Какого создателя хотите заменить?");
                for (int i = 1; i < creators.size() + 1; i++) {
                    System.out.println(i + " - " + creators.get(i - 1));
                }
                del = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println("Введите нового создателя для замены: ");
            String newGenre = scanner.nextLine();
            creators.set(del-1, newGenre);
        }
    }
}

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
            mediaList.get(id).changeName(newName);
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
            mediaList.get(id).changeAuthor(newAuthor);
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
            mediaList.get(id).changeYear(newYear);
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
            mediaList.get(id).changePublisher(newPublisher);
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
            mediaList.get(id).changePages(pages);
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
            mediaList.get(id).changeRating(newRating);
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
            mediaList.get(id).changeName(newName);
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
            mediaList.get(id).changeYear(newYear);
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
            mediaList.get(id).changeDuration(minutes);
        } else if(opt == 6){
            while(id < 0 || id > mediaList.size()){
                System.out.println("Среднюю оценку какого фильма вы хотите изменить?");
                for(Media media : mediaList)  media.getIdRating();
                id = idCheck(mediaList, id, type);
            }
            System.out.println("Введите новую среднюю оценку: ");
            double newRating = scanner.nextDouble();
            mediaList.get(id).changeRating(newRating);
        } else System.out.println("Такой опции нет!");
    }

    public static void deleteBook(ArrayList<Media> mediaList, String type){
        int id = -1;
        while(id < 0 || id > mediaList.size()){
            id = printMedia(mediaList, "Какую книгу вы хотите удалить?", id, type);
        }
        mediaList.remove(id);
        for(Media media : mediaList) media.updateId(mediaList);
        System.out.println("Книга успешно удалёна.");
    }
    public static void deleteFilm(ArrayList<Media> mediaList, String type){
        Scanner scanner = new Scanner(System.in);
        int id = -1;
        while(id < 0 || id > mediaList.size()){
            id = printMedia(mediaList, "Какой фильм вы хотите удалить", id, type);
        }
        mediaList.remove(id);
        for(Media media : mediaList) media.updateId(mediaList);
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

        int option = 0;
        while(option != 7) {

            int choice = -1;
            showMenu();

            option = scanner.nextInt();
            switch (option){
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
                    if(choice == 1) deleteBook(mediaList, "Книга");
                    else if(choice == 2) deleteFilm(mediaList, "Фильм");
                    break;
                case 5:
                    try {
                        Book testBook = new Book("Книга", mediaList.size(), "Test", "textAuthor",
                                new ArrayList<>(Arrays.asList("test1", "test2")), 2000, "testPublisher", 100, 0.0);
                        mapper.writeValue(new File("test.json"), testBook);


                        mapper.writeValue(new File("media_library.json"), mediaList);
                        System.out.println("Данные успешно записаны!");

                        String json = mapper.writeValueAsString(mediaList);
                        System.out.println(json);

                    } catch (IOException e) {
                        System.err.println("Ошибка при записи файла: " + e.getMessage());
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.err.println("Неожиданная ошибка: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case 6:

                    break;
                case 7:
                    break;
                default:
                    System.out.println("Такой опции нет! Введите другую!");
            }
        }

    }
}
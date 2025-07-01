import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Book{
    private int id;
    private String name;
    private String author;
    private ArrayList<String> genres = new ArrayList<>();
    private int pubYear;
    private String publisher;
    private int amountOfPages;
    private double rating;

    public Book(int id, String name, String author, ArrayList<String> genres, int pubYear, String publisher, int amountOfPages, double rating){
        this.id = id;
        this.name = name;
        this.author = author;
        this.genres = genres;
        this.pubYear = pubYear;
        this.publisher = publisher;
        this.amountOfPages = amountOfPages;
        this.rating = rating;
    }

    public void getInfo(){
        System.out.println("Тип: Книга\n"+"Id: "+id + "\nИмя: " + name + "\nАвтор: " + author + "\nЖанры: " +
                genres + "\nГод выхода: " + pubYear + "\nИздательство: " + publisher + "\nКол-во страниц: " + amountOfPages + "\nСредняя оценка: " + rating);
    }

    public void getName(){
        System.out.println(id + " - " + name);
    }

    public void changeName(){

    }
}

class Film{
    private int id;
    private String name;
    private ArrayList<String> genres = new ArrayList<>();
    private int pubYear;
    private ArrayList<String> creators = new ArrayList<>();
    private int durationMinutes;
    private double rating;

    public Film(int id, String name, ArrayList<String> genres, int pubYear, ArrayList<String> creators, int durationMinutes, double rating){
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.pubYear = pubYear;
        this.creators = creators;
        this.durationMinutes = durationMinutes;
        this.rating = rating;
    }

    public void getInfo(){
        System.out.println("Тип: Фильм\n"+"Id: " + id + "\nИмя: " + name + "\nЖанры: " + genres + "\nГод выхода: " +
                pubYear + "\nСоздатели: " + creators + "\nПродолжительность(в минутах): " + durationMinutes + "\nСредняя оценка: " + rating);
    }

    public void getName(){
        System.out.println(id + " - " + name);
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void getAllBooks(ArrayList<Book> books){
        for(Book book : books){
            book.getInfo();
        }
    }
    public static void getAllFilms(ArrayList<Film> films){
        for(Film film : films){
            film.getInfo();
        }
    }

    public static void addBook(ArrayList<Book> books){
        int id = books.size();
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

        books.add(new Book(id, name, author, genres, pubYear, publisher, amountOfPages, rating));
    }

    public static void addFilm(ArrayList<Film> films){
        int id = films.size();
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

        films.add(new Film(id, name, genres, pubYear, creators, durationMinutes, rating));
    }

    public static void showMenu(){
        System.out.println("Вы находитесь в медиатеке. Что вы хотите сделать?" +
                "\n1 - Получить информацию о всех книгах" +
                "\n2 - Получить информацию о всех фильмах" +
                "\n3 - Добавить книгу" +
                "\n4 - Добавить Фильм" +
                "\n5 - Изменить информацию о книге" +
                "\n6 - Изменить информацию о фильме" +
                "\n7 - Выход");
    }

    public static void changeBookMenu(ArrayList<Book> books){
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
        if(opt == 1){
            for(Book book : books){
                System.out.println("Название какой книги вы хотите изменить?");
                book.getName();
            }
        }
    }
    public static void changeFilmMenu(ArrayList<Film> films){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Что вы хотите изменить?" +
                "\n1 - Название" +
                "\n2 - Жанры" +
                "\n3 - Год выхода" +
                "\n4 - Создаели" +
                "\n5 - Продолжительность фильма" +
                "\n6 - Средняя оценка");
        int opt = scanner.nextInt();
        int id = 0;
        if(opt == 1){
            System.out.println("Название какого фильма вы хотите изменить?");
            for(Film film : films){

            }
        } else if(opt == 2){

        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Film> films = new ArrayList<>();
        books.add(new Book(books.size(), "Мистик", "Фрэнк Оувэл", new ArrayList<>(Arrays.asList("Детектив", "Мистика", "Комедия")),
                2016, "Букинист", 235, 4.5));
        films.add(new Film(films.size(), "Бесконечная история", new ArrayList<>(Arrays.asList("Приключения", "Фэнтези", "Комедия", "Экшен")),
                2019, new ArrayList<>(Arrays.asList("Нэйтан Блэк", "Брайс Папенбрук")),153, 4.2));
        films.add(new Film(films.size(), "На другй стороне", new ArrayList<>(Arrays.asList("Приключения", "Хоррор", "Экшен")),
                2021, new ArrayList<>(Arrays.asList("Кристи Голд", "Майкл Ковак")),210, 3.7));
        int option = 0;

        while(option != 7) {
            showMenu();
            option = scanner.nextInt();
            if (option == 1) {
                getAllBooks(books);
            } else if (option == 2) {
                getAllFilms(films);
            } else if (option == 3) {
                addBook(books);
            } else if (option == 4) {
                addFilm(films);
            } else if (option == 5) {

            } else if (option == 6){

            } else if(option != 7){
                System.out.println("Такой опции нет! Введите другую!");
            }
        }

    }
}
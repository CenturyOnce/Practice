import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

abstract class Media{
    protected String type;
    protected String name;
    protected ArrayList<String> genres;
    protected int pubYear;
    protected double rating;

    public Media(String name, ArrayList<String> genres, int pubYear, double rating){
        this.name = name;
        this.genres = genres;
        this.pubYear = pubYear;
        this.rating = rating;
    }

    public void getMainInfo(){
        System.out.println("Название: " + name + "\nЖанры: " + genres + "\nГод выхода: " + pubYear + "\nСредняя оценка: " + rating);
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

    public double getRating(){
        return rating;
    } public void changeRating(double rating){
        this.rating = rating;
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
    private final int id;
    private String author;
    private String publisher;
    private int amountOfPages;

    public Book(int id, String name, String author, ArrayList<String> genres, int pubYear, String publisher, int amountOfPages, double rating){
        super(name, genres, pubYear, rating);
        type = "Книга";
        this.id = id;
        this.author = author;
        this.publisher = publisher;
        this.amountOfPages = amountOfPages;
    }

    public void getInfo(){
        System.out.println("Тип: Книга" + "\nId: "+id);
        getMainInfo();
        System.out.println("Автор: " + author + "\nИздательство: " + publisher + "\nКол-во страниц: " + amountOfPages);
    }

    public void getIdName(){
        System.out.println(id + " - " + getName());
    }

    public void getAuthor(){
        System.out.println(id + " - " + author);
    } public void changeAuthor(String author){
        this.author = author;
    }

    public void getPublisher(){
        System.out.println(id + " - " + getName() + " - " + publisher);
    } public void changePublisher(String publisher){
        this.publisher = publisher;
    }

    public void getPages(){
        System.out.println(id + " - " + getName() + " - " + amountOfPages);
    } public void changePages(int amountOfPages){
        this.amountOfPages = amountOfPages;
    }

    public void getIdGenres(){
        System.out.println(id + " - " + getName() + " - " + getGenres());
    }

    public void getIdYear(){
        System.out.println(id + " - " + getName() + " - " + getYear());
    }

    public void getIdRating(){
        System.out.println(id + " - " + getName() + " - " + getRating());
    }
}

class Film extends Media{
    private final int id;
    private ArrayList<String> creators;
    private int durationMinutes;

    public Film(int id, String name, ArrayList<String> genres, int pubYear, ArrayList<String> creators, int durationMinutes, double rating){
        super(name, genres, pubYear, rating);
        type = "Фильм";
        this.id = id;
        this.creators = creators;
        this.durationMinutes = durationMinutes;
    }

    public void getInfo(){
        System.out.println("Тип: Фильм" + "\nId: ");
        getMainInfo();
        System.out.println("Создатели: " + creators + "\nПродолжительность(в минутах): " + durationMinutes);
    }

    public void getIdName(){
        System.out.println(id + " - " + getName());
    }

    public void getIdGenres(){
        System.out.println(id + " - " + getName() + " - " + getGenres());
    }

    public void getIdYear(){
        System.out.println(id + " - " + getName() + " - " + getYear());
    }

    public void getDuration(){
        System.out.println(id + " - " + getName() + " - " + durationMinutes);
    } public void changeDuration(int durationMinutes){
        this.durationMinutes = durationMinutes;
    }

    public void getIdRating(){
        System.out.println(id + " - " + getName() + " - " + getRating());
    }

    public void getIdCreators(){
        System.out.println(id + " - " + getName() + " - " + creators);
    } public void changeCreators(){
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
        int id = -1;
        if(opt == 1){
            while(id < 0 || id > books.size()){
                System.out.println("Название какой книги вы хотите изменить?");
                for(Book book : books)  book.getIdName();
                id = scanner.nextInt();
            }
            System.out.println("Напишите новое название книги: ");
            scanner.nextLine();
            String newName = scanner.nextLine();
            books.get(id).changeName(newName);
        } else if(opt == 2){
            while(id < 0 || id > books.size()){
                System.out.println("Автора какой книги вы хотите изменить?");
                for(Book book : books)  book.getAuthor();
                id = scanner.nextInt();
            }
            System.out.println("Напишите новое название книги: ");
            scanner.nextLine();
            String newAuthor = scanner.nextLine();
            books.get(id).changeAuthor(newAuthor);
        } else if(opt == 3){
            while(id < 0 || id > books.size()){
                System.out.println("Жанры какой книги вы хотите изменить?");
                for(Book book : books)  book.getIdGenres();
                id = scanner.nextInt();
            }
            books.get(id).changeGenres();
        } else if (opt == 4) {
            while(id < 0 || id > books.size()){
                System.out.println("Год выхода какой книги вы хотите изменить?");
                for(Book book : books)  book.getIdYear();
                id = scanner.nextInt();
            }
            System.out.println("Введите новый год выпуска книги: ");
            int newYear = scanner.nextInt();
            books.get(id).changeYear(newYear);
        } else if (opt == 5){
            while(id < 0 || id > books.size()){
                System.out.println("Издателя какой книги вы хотите изменить?");
                for(Book book : books)  book.getPublisher();
                id = scanner.nextInt();
            }
            System.out.println("Введите нового издатедя: ");
            scanner.nextLine();
            String newPublisher = scanner.nextLine();
            books.get(id).changePublisher(newPublisher);
        } else if (opt == 6){
            while(id < 0 || id > books.size()){
                System.out.println("Кол-во страниц какой книги вы хотите изменить?");
                for(Book book : books)  book.getPages();
                id = scanner.nextInt();
            }
            System.out.println("Введите новое кол-во страниц: ");
            int pages = scanner.nextInt();
            books.get(id).changePages(pages);
        } else if (opt == 7){
            while(id < 0 || id > books.size()){
                System.out.println("Среднюю оценку какой книги вы хотите изменить?");
                for(Book book : books)  book.getIdRating();
                id = scanner.nextInt();
            }
            System.out.println("Введите новую среднюю оценку: ");
            double newRating = scanner.nextDouble();
            books.get(id).changeRating(newRating);
        } else System.out.println("Такой опции нет!");
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
        int id = -1;
        if(opt == 1){
            while(id < 0 || id > films.size()){
                System.out.println("Название какого фильма вы хотите изменить?");
                for(Film film : films)  film.getIdName();
                id = scanner.nextInt();
            }
            System.out.println("Напишите новое название фильма: ");
            scanner.nextLine();
            String newName = scanner.nextLine();
            films.get(id).changeName(newName);
        } else if(opt == 2){
            while(id < 0 || id > films.size()){
                System.out.println("Жанры какого фильма вы хотите изменить?");
                for(Film film : films)  film.getIdGenres();
                id = scanner.nextInt();
            }
            films.get(id).changeGenres();
        } else if(opt == 3){
            while(id < 0 || id > films.size()){
                System.out.println("Год выхода какго фильма вы хотите изменить?");
                for(Film film : films)  film.getIdYear();
                id = scanner.nextInt();
            }
            System.out.println("Введите новый год выпуска фильма: ");
            int newYear = scanner.nextInt();
            films.get(id).changeYear(newYear);
        } else if(opt == 4){
            while(id < 0 || id > films.size()){
                System.out.println("Создателей какго фильма вы хотите изменить?");
                for(Film film : films)  film.getIdCreators();
                id = scanner.nextInt();
            }
            films.get(id).changeCreators();
        } else if(opt == 5){
            while(id < 0 || id > films.size()){
                System.out.println("Продолжительность какго фильма вы хотите изменить?");
                for(Film film : films)  film.getDuration();
                id = scanner.nextInt();
            }
            System.out.println("Введите новую продолжительность фильма(в минутах): ");
            int minutes = scanner.nextInt();
            films.get(id).changeDuration(minutes);
        } else if(opt == 6){
            while(id < 0 || id > films.size()){
                System.out.println("Среднюю оценку какого фильма вы хотите изменить?");
                for(Film film : films)  film.getIdRating();
                id = scanner.nextInt();
            }
            System.out.println("Введите новую среднюю оценку: ");
            double newRating = scanner.nextDouble();
            films.get(id).changeRating(newRating);
        } else System.out.println("Такой опции нет!");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Film> films = new ArrayList<>();
        books.add(new Book(books.size(), "Мистик", "Фрэнк Оувэл", new ArrayList<>(Arrays.asList("Детектив", "Мистика", "Комедия")),
                2016, "Букинист", 235, 4.5));
        films.add(new Film(films.size(), "Бесконечная история", new ArrayList<>(Arrays.asList("Приключения", "Фэнтези", "Комедия", "Экшен")),
                2019, new ArrayList<>(Arrays.asList("Нэйтан Блэк", "Брайс Папенбрук")),153, 4.2));
        films.add(new Film(films.size(), "На другой стороне", new ArrayList<>(Arrays.asList("Приключения", "Хоррор", "Экшен")),
                2021, new ArrayList<>(Arrays.asList("Кристи Голд", "Майкл Ковак")),210, 3.7));
        int option = 0;

        while(option != 7) {
            showMenu();
            option = scanner.nextInt();
            switch (option){
                case 1:
                    getAllBooks(books);
                    break;
                case 2:
                    getAllFilms(films);
                    break;
                case 3:
                    addBook(books);
                    break;
                case 4:
                    addFilm(films);
                    break;
                case 5:
                    changeBookMenu(books);
                    break;
                case 6:
                    changeFilmMenu(films);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Такой опции нет! Введите другую!");
            }
        }

    }
}
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "mediaType"  // поле в JSON, по которому определяется тип
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Film.class, name = "Фильм"),
        @JsonSubTypes.Type(value = Book.class, name = "Книга")
})
abstract class Media {
    protected int id;
    protected String type;
    protected String name;
    protected ArrayList<String> genres;
    protected int year;
    protected double rating;

    public Media() {}

    public Media(String type, int id, String name, ArrayList<String> genres, int year, double rating) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.year = year;
        this.rating = rating;
    }

    public void getInfo() {
        System.out.println("ID: " + id + "\nТип: " + type + "\nНазвание: " + name + "\nЖанры: " + genres + "\nГод выхода: " + year +
                "\nСредняя оценка: " + rating);
        getSecondaryInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return id == media.id &&  // Если нужно учитывать ID, иначе удалите эту строку
                Double.compare(media.rating, rating) == 0 &&
                year == media.year &&
                Objects.equals(name, media.name) &&
                Objects.equals(genres, media.genres);
    }

    void getSecondaryInfo() {}

    void getIdName() {
        System.out.println(id + " - " + name);
    }

    String getAuthor() {
        return "";
    }
    void setAuthor(String author) {}
    void getIdPublisher() {}
    void setPublisher(String publisher) {}
    void getIdPages() {}
    int getPages(){ return 0; }
    void setPages(int amountOfPages) {}
    ArrayList<String> getCreators() {
        return genres;
    }
    void changeCreators() {}
    int getDuration() {
        return 0;
    }
    public void setDuration(int durationMinutes) {}

    public void getIdYear() {
        System.out.println(id + " - " + name + " - " + year);
    }

    public void getIdGenres() {
        System.out.println(id + " - " + name + " - " + getGenres());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public ArrayList<String> getGenres() { return genres; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return String.format(name); }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setPubYear(int pubYear) { this.year = pubYear; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public void getIdRating() {
        System.out.println(id + " - " + name + " - " + rating);
    }

    public void changeGenres() {
        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        while (opt <= 0 || opt > 3) {
            System.out.println("Что вы хотите изменить в жанрах?\n" +
                    "1 - Удалить жанр\n" +
                    "2 - Добавить жанр\n" +
                    "3 - Заменить жанр");
            opt = scanner.nextInt();
        }
        int del = 0;
        if (opt == 1) {
            while (del <= 0 || del > genres.size()) {
                System.out.println("Какой жанр хотите удалить?");
                for (int i = 1; i < genres.size() + 1; i++) {
                    System.out.println(i + " - " + genres.get(i - 1));
                }
                del = scanner.nextInt();
            }
            genres.remove(del - 1);
        } else if (opt == 2) {
            System.out.println("Введите жанр, который хотите добавить: ");
            scanner.nextLine();
            String newGenre = scanner.nextLine();
            genres.add(newGenre);
        } else if (opt == 3) {
            while (del <= 0 || del > genres.size()) {
                System.out.println("Какой жанр хотите заменить?");
                for (int i = 1; i < genres.size() + 1; i++) {
                    System.out.println(i + " - " + genres.get(i - 1));
                }
                del = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println("Введите новый жанр для замены: ");
            String newGenre = scanner.nextLine();
            genres.set(del - 1, newGenre);
        }
    }
}

import java.util.*;

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
    protected String name;
    protected Set<String> genres;
    protected int year;
    protected double rating;

    protected Media() {}

    protected Media(int id, String name, Set<String> genres, int year, double rating) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.year = year;
        this.rating = rating;
    }

    public void getInfo() {
        System.out.println("ID: " + id + "\nТип: " + getType() + "\nНазвание: " + name + "\nЖанры: " + genres + "\nГод выхода: " + year +
                "\nСредняя оценка: " + rating);
        getSecondaryInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return id == media.id &&
                Double.compare(media.rating, rating) == 0 &&
                year == media.year &&
                Objects.equals(name, media.name);
    }

    void getSecondaryInfo() {}

    void getIdName() {
        System.out.println(id + " - " + name);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Set<String> getGenres() { return genres; }
    public void setGenres(Set<String> genres) { this.genres = genres; }

    public String getType() { return ""; }
    public void setType(String type) {}

    public String getName() { return String.format(name); }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setPubYear(int pubYear) { this.year = pubYear; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }


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
        String del = null;
        Iterator<String> iterator = genres.iterator();
        if (opt == 1) {
            while (!genres.contains(del)) {
                System.out.println("Какой жанр хотите удалить?");
                for (int i = 0; i < genres.size() && iterator.hasNext(); i++) {
                    String element = iterator.next();
                    System.out.println(element);
                }
                scanner.nextLine();
                del = scanner.nextLine();
                if(!genres.contains(del)) System.out.println("Такого жанра в списке нет!");
            }
            genres.remove(del);
            System.out.println("Жанр успешно удалён.");
        } else if (opt == 2) {
            System.out.println("Введите жанр, который хотите добавить: ");
            scanner.nextLine();
            String newGenre = scanner.nextLine();
            if(genres.contains(newGenre)) System.out.println("Такой жанр уже есть!");
            else {
                genres.add(newGenre);
                System.out.println("Жанр успешно добавлен");
            }
        } else if (opt == 3) {
            String element;
            while (!genres.contains(del)) {
                System.out.println("Какой жанр хотите заменить?");
                scanner.nextLine();
                for (int i = 0; i < genres.size() && iterator.hasNext(); i++) {
                    element = iterator.next();
                    System.out.println(element);
                }
                del = scanner.nextLine();
                if(!genres.contains(del)) System.out.println("Такого жанра в списке нет!");
            }
            genres.remove(del);
            String newGenre = null;
            while(!genres.contains(newGenre)) {
                System.out.println("Введите новый жанр для замены: ");
                newGenre = scanner.nextLine();
                if(genres.contains(newGenre)) System.out.println("Такой жанр уже есть!");
                else {
                    genres.add(newGenre);
                    System.out.println("Жанр успешно заменён.");
                }
            }
        }
    }
}

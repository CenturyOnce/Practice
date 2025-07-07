import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

class Film extends Media {
    final public static String TYPE = "Фильм";

    private ArrayList<String> creators;
    private int duration;

    public Film() {}
    public Film(int id, String name, Set<String> genres, int year, ArrayList<String> creators, int duration, double rating) {
        super(id, name, genres, year, rating);
        this.creators = creators;
        this.duration = duration;
    }

    @Override
    public void getSecondaryInfo() {
        System.out.println("Создатели: " + creators + "\nПродолжительность(в минутах): " + duration);
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }
    @Override
    public String getType() {
        return TYPE;
    }
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getCreators() {
        return creators;
    }
    public void setCreators(ArrayList<String> creators) { this.creators = creators; }

    public void changeCreators() {
        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        while (opt <= 0 || opt > 3) {
            System.out.println("Что вы хотите изменить в создателях?\n" +
                    "1 - Удалить создателя\n" +
                    "2 - Добавить создателя\n" +
                    "3 - Заменить создателя");
            opt = scanner.nextInt();
        }
        int del = 0;
        if (opt == 1) {
            while (del <= 0 || del > creators.size()) {
                System.out.println("Какого создателя хотите удалить?");
                for (int i = 1; i < creators.size() + 1; i++) {
                    System.out.println(i + " - " + creators.get(i - 1));
                }
                del = scanner.nextInt();
            }
            creators.remove(del - 1);
        } else if (opt == 2) {
            System.out.println("Введите создателя, которого хотите добавить: ");
            scanner.nextLine();
            String newGenre = scanner.nextLine();
            creators.add(newGenre);
        } else if (opt == 3) {
            while (del <= 0 || del > creators.size()) {
                System.out.println("Какого создателя хотите заменить?");
                for (int i = 1; i < creators.size() + 1; i++) {
                    System.out.println(i + " - " + creators.get(i - 1));
                }
                del = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println("Введите нового создателя для замены: ");
            String newGenre = scanner.nextLine();
            creators.set(del - 1, newGenre);
        }
    }
}

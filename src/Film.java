import java.util.ArrayList;
import java.util.Scanner;

class Film extends Media {
    private ArrayList<String> creators;
    private int duration;

    public Film() {}
    public Film(String type, int id, String name, ArrayList<String> genres, int year, ArrayList<String> creators, int duration, double rating) {
        super(type, id, name, genres, year, rating);
        this.creators = creators;
        this.duration = duration;
    }

    @Override
    public void getSecondaryInfo() {
        System.out.println("Создатели: " + creators + "\nПродолжительность(в минутах): " + duration);
    }

    @Override
    public void setType(String type) {
        super.setType(type);  // сохраняет значение в родительском классе
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public ArrayList<String> getCreators() {
        return creators;
    }
    public void setCreators(ArrayList<String> creators) { this.creators = creators; }

    @Override
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

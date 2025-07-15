import java.util.*;

public class Game extends Media{
    final public static String TYPE = "Игра";

    private String developer;
    private String publisher;
    private Set<String> platforms;
    private Set<String> supLanguages;

    public Game(){}
    public Game(int id, String name, Set<String> genres, int year, String developer, String publisher, Set<String> platforms,
                Set<String> supLanguages, double rating){
        super(id, name, genres, year, rating);
        this.developer = developer;
        this.publisher = publisher;
        this.platforms = platforms;
        this.supLanguages = supLanguages;
    }

    @Override
    public void getSecondaryInfo(){
        System.out.println("Разработчик: " + developer + "\nИздательство: " + publisher + "\nПлатформы: " + platforms +
                "\nПоддерживаемые языки: " + supLanguages);
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public String getDeveloper(){return developer;}
    public void setDeveloper(String developer){this.developer = developer;}

    @Override
    public String getPublisher(){return publisher;}
    @Override
    public void setPublisher(String publisher){this.publisher = publisher;}

    public Set<String> getPlatforms(){return platforms;}
    public void setPlatforms(Set<String> platforms){this.platforms = platforms;}

    public Set<String> getSupLanguages(){return supLanguages;}
    public void setSupLanguages(Set<String> supLanguages){this.supLanguages = supLanguages;}

    public void changePlatforms(){
        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        while (opt <= 0 || opt > 3) {
            System.out.println("Что вы хотите изменить в платформах?\n" +
                    "1 - Удалить платформу\n" +
                    "2 - Добавить платформу\n" +
                    "3 - Заменить платформу");
            opt = scanner.nextInt();
            scanner.nextLine();
        }
        String del = null;
        Iterator<String> iterator = platforms.iterator();
        if (opt == 1) {
            while (!platforms.contains(del)) {
                System.out.println("Какую платформу хотите удалить?");
                for (int i = 0; i < platforms.size() && iterator.hasNext(); i++) {
                    String element = iterator.next();
                    System.out.println(element);
                }
                del = scanner.nextLine();
                if(!platforms.contains(del)) System.out.println("Такой платформы в списке нет!");
            }
            platforms.remove(del);
            System.out.println("Платформа успешно удалёна.");
        } else if (opt == 2) {
            System.out.println("Введите платформу, которую хотите добавить: ");
            String newPlatform = scanner.nextLine().trim();
            if(platforms.contains(newPlatform)) System.out.println("Такая платформа уже есть!");
            else {
                platforms.add(newPlatform);
                System.out.println("Платформа успешно добавлена");
            }
        } else if (opt == 3) {
            String element;
            while (!platforms.contains(del)) {
                System.out.println("Какую платформу хотите заменить?");
                for (int i = 0; i < platforms.size() && iterator.hasNext(); i++) {
                    element = iterator.next();
                    System.out.println(element);
                }
                del = scanner.nextLine();
                if(!platforms.contains(del)) System.out.println("Такой платформы в списке нет!");
            }
            platforms.remove(del);
            String newPlatform = null;
            while(!platforms.contains(newPlatform)) {
                System.out.println("Введите новую платформу для замены: ");
                newPlatform = scanner.nextLine().trim();
                if(platforms.contains(newPlatform)) System.out.println("Такая платформа уже есть!");
                else {
                    platforms.add(newPlatform);
                    System.out.println("Платформа успешно заменёна.");
                }
            }
        }
    }
    public void changeSupLanguages(){
        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        while (opt <= 0 || opt > 3) {
            System.out.println("Что вы хотите изменить в языках?\n" +
                    "1 - Удалить язык\n" +
                    "2 - Добавить язык\n" +
                    "3 - Заменить язык");
            opt = scanner.nextInt();
            scanner.nextLine();
        }
        String del = null;
        Iterator<String> iterator = supLanguages.iterator();
        if (opt == 1) {
            while (!supLanguages.contains(del)) {
                System.out.println("Какой язык хотите удалить?");
                for (int i = 0; i < supLanguages.size() && iterator.hasNext(); i++) {
                    String element = iterator.next();
                    System.out.println(element);
                }
                del = scanner.nextLine();
                if(!supLanguages.contains(del)) System.out.println("Такого языка в списке нет!");
            }
            supLanguages.remove(del);
            System.out.println("Язык успешно удалён.");
        } else if (opt == 2) {
            System.out.println("Введите язык, который хотите добавить: ");
            String newLanguage = scanner.nextLine().trim();
            if(supLanguages.contains(newLanguage)) System.out.println("Такой язык уже есть!");
            else {
                supLanguages.add(newLanguage);
                System.out.println("Язык успешно добавлен");
            }
        } else if (opt == 3) {
            String element;
            while (!supLanguages.contains(del)) {
                System.out.println("Какой язык хотите заменить?");
                for (int i = 0; i < supLanguages.size() && iterator.hasNext(); i++) {
                    element = iterator.next();
                    System.out.println(element);
                }
                del = scanner.nextLine();
                if(!supLanguages.contains(del)) System.out.println("Такого языка в списке нет!");
            }
            supLanguages.remove(del);
            String newLanguage = null;
            while(!supLanguages.contains(newLanguage)) {
                System.out.println("Введите новый язык для замены: ");
                newLanguage = scanner.nextLine().trim();
                if(supLanguages.contains(newLanguage)) System.out.println("Такой язык уже есть!");
                else {
                    supLanguages.add(newLanguage);
                    System.out.println("Язык успешно заменён.");
                }
            }
        }
    }
}

import java.util.Set;

class Book extends Media {
    final public static String TYPE = "Книга";

    private String author;
    private String publisher;
    private int pages;

    public Book(){}
    public Book(int id, String name, String author, Set<String> genres, int year,
                String publisher, int pages, double rating){
        super(id, name, genres, year, rating);
        this.author = author;
        this.publisher = publisher;
        this.pages = pages;
    }

    @Override
    public void getSecondaryInfo(){
        System.out.println("Автор: " + author + "\nИздательство: " + publisher + "\nКол-во страниц: " + pages);
    }

    @Override
    public void setType(String type) {
        super.setType(type);  // сохраняет значение в родительском классе
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public String getAuthor(){ return author; }
    public void setAuthor(String author){
        this.author = author;
    }

    public int getPages(){ return pages; }
    public void setPages(int pages){
        this.pages = pages;
    }

    public String getPublisher(){ return publisher; }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
}
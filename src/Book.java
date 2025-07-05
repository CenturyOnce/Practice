import java.util.ArrayList;

class Book extends Media{
    private String author;
    private String publisher;
    private int pages;

    public Book(){}
    public Book(String type, int id, String name, String author, ArrayList<String> genres, int year,
                String publisher, int pages, double rating){
        super(type, id, name, genres, year, rating);
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
    public String getAuthor(){ return author; }
    @Override
    public void setAuthor(String author){
        this.author = author;
    }

    @Override
    public int getPages(){ return pages; }
    @Override
    public void setPages(int pages){
        this.pages = pages;
    }

    public String getPublisher(){ return publisher; }
    @Override
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    @Override
    public void getIdPublisher(){
        System.out.println(id + " - " + name + " - " + publisher);
    }

    @Override
    public void getIdPages(){
        System.out.println(id + " - " + name + " - " + pages);
    }

    @Override
    public void getIdGenres(){
        System.out.println(id + " - " + getName() + " - " + getGenres());
    }
}
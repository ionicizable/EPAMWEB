import java.util.ArrayList;
import java.util.List;

public class Book {
    String title;
    List<Author> authors;
    int numberOfPages;

    public Book() {
        this.authors = new ArrayList<Author>();
    }
}

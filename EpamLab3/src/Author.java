import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Author {
    String name;
    short age;
    List<Book> books;

    public Author(String name, short age) {
        this.name = name;
        this.age = age;
        this.books = new ArrayList<Book>();
    }
}

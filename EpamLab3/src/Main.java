import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ArrayList<Author> authors = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        readAuthors(authors, books);
        authors.forEach(a -> {
            printAuthor(a);
        });
        Supplier<Stream<Book>> bookSupply = () -> books.stream();
        Stream<Author> authorStream = authors.stream();
        System.out.println("------Книги с числом страниц > 200:------");
        //bookSupply.get().filter(b -> b.numberOfPages > 200).forEach(b -> printBook(b));
        bookSupply.get().filter(b -> b.numberOfPages > 200)
                .peek(b-> System.out.println("Эта книга имеет больше 200 страниц:"+b.title));
        System.out.println("------Книги с единственным автором:------");
        bookSupply.get().filter(b -> b.authors.size() == 1).forEach(b -> printBook(b));
        System.out.println("------Книга с максимальным числом страниц:------");
        Book maxbook = bookSupply.get().max(Comparator.comparingInt(b -> b.numberOfPages)).get();
        printBook(maxbook);
        System.out.println("------Книга с минимальным числом страниц:------");
        Optional<Book> minbook = Optional.of(bookSupply.get().min(Comparator.comparingInt(b -> b.numberOfPages)).get());
        if (minbook.isPresent()) {
            printBook(minbook.get());
        } else {
            System.out.println("Такая книга отсутствует");
        }
        System.out.println("------Книги упорядочены по количеству страниц:------");
        bookSupply.get().sorted(Comparator.comparingInt(b -> b.numberOfPages)).forEach(a -> printBook(a));
        System.out.println("------Книги упорядочены по названию:------");
        bookSupply.get().sorted(Comparator.comparing(b -> b.title)).forEach(a -> printBook(a));


    }

    public static void printAuthor(Author author) {
        System.out.println(author.name + " " + author.age);
        author.books.forEach(b -> System.out.println("  " + b.title + " " + b.numberOfPages));
    }

    public static void printBook(Book book) {
        System.out.println(book.title + " " + book.numberOfPages);
        book.authors.forEach(a -> System.out.println("  " + a.name + " " + a.age));
    }

    public static void readAuthors(ArrayList<Author> authors, ArrayList<Book> books) {

        try (BufferedReader reader = new BufferedReader(new FileReader("data/Authors.txt"))) {

            String buffer;
            while ((buffer = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(buffer, "-");
                String name = st.nextToken();
                short age = Short.parseShort(st.nextToken());
                authors.add(new Author(name, age));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader bookReader = new BufferedReader(new FileReader("data/Books.txt"))) {
            String buffer;
            while ((buffer = bookReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(buffer, "-");
                String title = st.nextToken();
                int numberOfPages = Integer.parseInt(st.nextToken());
                Book book = new Book();
                book.title = title;
                book.numberOfPages = numberOfPages;
                while (st.hasMoreElements()) {
                    Author author = null;
                    String authorName = st.nextToken();
                    for (Author item : authors
                    ) {
                        if (item.name.equals(authorName)) {
                            author = item;
                            author.books.add(book);
                            book.authors.add(author);
                            break;
                        }
                    }
                }
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

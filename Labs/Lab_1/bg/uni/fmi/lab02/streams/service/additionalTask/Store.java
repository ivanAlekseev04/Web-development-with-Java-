package bg.uni.fmi.lab02.streams.service.additionalTask;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Store implements StoreAPI {
    private Set<Book> books;

    public Store() {
        books = new HashSet<>();
    }

    @Override
    public boolean add(Book o) {
        if(books.contains(o)) {
            return false;
        } else {
            books.add(o);
            return true;
        }
    }

    @Override
    public void remove(Book o) {
        books.remove(o);
    }

    @Override
    public List<Book> getAllBooksByAuthor(String author) {
        return books.stream().filter(b -> b.getAuthor().equals(author)).toList();
    }

    @Override
    public List<Book> getAllBooksPublishedAfter(LocalDate from) {
        return books.stream().filter(b -> b.getPublishedYear().isAfter(from)).toList();
    }

    @Override
    public List<Book> getAllBooksBetween(LocalDate from, LocalDate to) {
      return books
              .stream()
              .filter(b -> !b.getPublishedYear().isBefore(from) && !b.getPublishedYear().isAfter(to)).toList();
    }

    @Override
    public void clear() {
        books.clear();
    }

    @Override
    public Map<String, List<Book>> getAllBooksGroupByAuthor() {
        return books
                .stream()
                .collect(Collectors.groupingBy(Book::getAuthor));
    }

    @Override
    public Map<String, List<Book>> getAllBooksGroupByPublisher() {
        return books
                .stream()
                .collect(Collectors.groupingBy(Book::getPublisher));
    }

    @Override
    public List<Book> getAllBooksFilterBy(Predicate<Book> bookPredicate) {
        return books.stream().filter(bookPredicate).toList();
    }
}

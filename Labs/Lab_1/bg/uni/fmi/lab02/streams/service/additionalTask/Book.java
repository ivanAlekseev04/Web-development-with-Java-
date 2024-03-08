package bg.uni.fmi.lab02.streams.service.additionalTask;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private BigDecimal price;
    private String publisher;
    private LocalDate publishedYear;

    public Book(String title, String author, BigDecimal price, String publisher, LocalDate publishedYear) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPublishedYear() {
        return publishedYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title) && author.equals(book.author) && publishedYear.equals(book.publishedYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, publishedYear);
    }
}
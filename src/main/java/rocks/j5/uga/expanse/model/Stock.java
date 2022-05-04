package rocks.j5.uga.expanse.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stock")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock  extends EntityWithUUID
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }

    public Publisher getPublisher()
    {
        return publisher;
    }

    public void setPublisher(Publisher publisher)
    {
        this.publisher = publisher;
    }

}
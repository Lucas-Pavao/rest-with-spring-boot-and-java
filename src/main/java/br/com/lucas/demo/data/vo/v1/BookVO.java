package br.com.lucas.demo.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id","author","launcheDate","price","title"})
public class BookVO extends RepresentationModel<BookVO> implements Serializable {


    @Mapping("id")
    @JsonProperty("id")
    private Long key;
    private String author;
    private Date launcheDate;
    private Double price;
    private String title;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLauncheDate() {
        return launcheDate;
    }

    public void setLauncheDate(Date launcheDate) {
        this.launcheDate = launcheDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookVO book = (BookVO) o;
        return Objects.equals(key, book.key) && Objects.equals(author, book.author) && Objects.equals(launcheDate, book.launcheDate) && Objects.equals(price, book.price) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, author, launcheDate, price, title);
    }
}

package io.github.karMiguel.library.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@JsonPropertyOrder({"id", "author", "launchDate", "price", "title"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookVo extends RepresentationModel<BookVo> {


    @JsonProperty("id")
    private Long key;
    private String author;
    private Date launchDate;
    private Double price;
    private String title;
}

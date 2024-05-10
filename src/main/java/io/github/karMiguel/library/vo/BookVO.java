package io.github.karMiguel.library.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BookVO extends RepresentationModel<BookVO> {

    @JsonProperty("id")
    private Long key;

    @NotBlank(message = "O autor não pode estar em branco")
    private String author;

    @NotNull(message = "A data de lançamento não pode ser nula")
    private Date launchDate;

    @NotNull(message = "O preço não pode ser nulo")
    private Double price;

    @NotBlank(message = "O título não pode estar em branco")
    private String title;
}

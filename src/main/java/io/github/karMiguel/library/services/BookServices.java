package io.github.karMiguel.library.services;

import io.github.karMiguel.library.controllers.BookController;
import io.github.karMiguel.library.vo.BookVo;
import io.github.karMiguel.library.exceptions.RequiredObjectIsNullException;
import io.github.karMiguel.library.exceptions.ResourceNotFoundException;
import io.github.karMiguel.library.mapper.BookMapper;
import io.github.karMiguel.library.model.Book;
import io.github.karMiguel.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookVo> findAll() {
        logger.info("Finding all books!");

        var books = BookMapper.parseListObjects( repository.findAll(), BookVo.class);
        books
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return books;

    }

    public BookVo findById(Long id) {
        logger.info("Finding one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = BookMapper.parseObject(entity, BookVo.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;

    }

    public BookVo create(BookVo book) {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");
        var entity = BookMapper.parseObject(book, Book.class);
        var savedEntity = repository.save(entity);
        var vo = BookMapper.parseObject(savedEntity, BookVo.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVo update(BookVo book) {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var updatedEntity = repository.save(entity);
        var vo = BookMapper.parseObject(updatedEntity, BookVo.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }


}

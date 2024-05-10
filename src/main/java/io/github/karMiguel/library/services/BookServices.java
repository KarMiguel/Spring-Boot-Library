package io.github.karMiguel.library.services;

import io.github.karMiguel.library.controllers.BookController;
import io.github.karMiguel.library.exceptions.RequiredObjectIsNullException;
import io.github.karMiguel.library.exceptions.ResourceNotFoundException;
import io.github.karMiguel.library.mapper.BookMapper;
import io.github.karMiguel.library.model.Book;
import io.github.karMiguel.library.repository.BookRepository;
import io.github.karMiguel.library.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    private BookRepository repository;

    @Autowired
    private PagedResourcesAssembler<BookVO> assembler;

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
        logger.info("Finding all books!");

        var bookPage = repository.findAll(pageable);
        var bookVosPage = bookPage.map(b -> BookMapper.parseObject(b, BookVO.class));

        // Adding self link to each BookVO
        bookVosPage.forEach(p -> p.add(linkTo(methodOn(BookController.class)
                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(BookController.class)
                .findAll(pageable.getPageNumber(),pageable.getPageSize(),"asc"))
                .withSelfRel();
        return assembler.toModel(bookVosPage,link);
    }
    public PagedModel<EntityModel<BookVO>> findByTitle(String title, Pageable pageable) {
        logger.info("Finding all books!");

        var bookPage = repository.findBookByTitle(title,pageable);
        var bookVosPage = bookPage.map(b -> BookMapper.parseObject(b, BookVO.class));

        // Adding self link to each BookVO
        bookVosPage.forEach(p -> p.add(linkTo(methodOn(BookController.class)
                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(BookController.class)
                .findAll(pageable.getPageNumber(),pageable.getPageSize(),"asc"))
                .withSelfRel();
        return assembler.toModel(bookVosPage,link);
    }


    public BookVO findById(Long id) {
        logger.info("Finding one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = BookMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;

    }

    public BookVO create(BookVO book) {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");
        var entity = BookMapper.parseObject(book, Book.class);
        var savedEntity = repository.save(entity);
        var vo = BookMapper.parseObject(savedEntity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {
        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var updatedEntity = repository.save(entity);
        var vo = BookMapper.parseObject(updatedEntity, BookVO.class);
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

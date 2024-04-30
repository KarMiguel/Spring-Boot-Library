package io.github.karMiguel.library.mapper;

import io.github.karMiguel.library.vo.BookVo;
import io.github.karMiguel.library.model.Book;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {
    private static ModelMapper mapper = new ModelMapper();

    static {
        mapper.createTypeMap(
                        Book.class,
                        BookVo.class)
                .addMapping(Book::getId, BookVo::setKey);
        mapper.createTypeMap(
                        BookVo.class,
                        Book.class)
                .addMapping(BookVo::getKey, Book::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }

}

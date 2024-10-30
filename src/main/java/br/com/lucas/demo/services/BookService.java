package br.com.lucas.demo.services;

import br.com.lucas.demo.Controllers.BookController;
import br.com.lucas.demo.data.vo.v1.BookVO;
import br.com.lucas.demo.exception.RequiredObjectIsNullException;
import br.com.lucas.demo.exception.ResouceNotFoundException;
import br.com.lucas.demo.mapper.DozerMapper;
import br.com.lucas.demo.model.Book;
import br.com.lucas.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.internal.Logger;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service

public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired()
    BookRepository repository;


    public List<BookVO> findAll (){
        logger.warn("find all book");

        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books.stream().forEach(
                p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel())
        );
        return books;
    }

    public BookVO findById (Long id) {
        logger.warn("find one book");

        var entity = repository.findById(id).orElseThrow(()-> new ResouceNotFoundException("no records founds for this id!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return  vo;

    }

    public BookVO create (BookVO book){

        if(book == null){
            throw  new RequiredObjectIsNullException();
        }
        logger.warn("create a book");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return  vo;
    }

    public BookVO update (BookVO book){
        if(book == null){
            throw  new RequiredObjectIsNullException();
        }
        logger.warn("update a book");
        var entity = repository.findById(book.getKey()).orElseThrow(()-> new ResouceNotFoundException("no records founds for this id!"));

        entity.setAuthor(book.getAuthor());
        entity.setLauncheDate(book.getLauncheDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return  vo;
    }

    public void delete (Long id){
        logger.warn("delete a book");
        Book entity = repository.findById(id).orElseThrow(()-> new ResouceNotFoundException("no records founds for this id!"));
        repository.delete(entity);
    }








}

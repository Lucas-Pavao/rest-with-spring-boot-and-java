package br.com.lucas.demo.unittestes.mockito.sevices;


import br.com.lucas.demo.data.vo.v1.BookVO;
import br.com.lucas.demo.exception.RequiredObjectIsNullException;
import br.com.lucas.demo.model.Book;
import br.com.lucas.demo.repositories.BookRepository;
import br.com.lucas.demo.services.BookService;
import br.com.lucas.demo.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;
    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById (){
        Book book = input.mockEntity(1);
        book.setId(1L);
        when (repository.findById(1L)).thenReturn(Optional.of(book));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("some author 1",result.getAuthor());
        assertEquals(new Date(),result.getLauncheDate());
        assertEquals(25D,result.getPrice());
        assertEquals("some title 1",result.getTitle());
    }

    @Test
    void testFindAll(){

        List<Book> list = input.mockEntityList();

        when (repository.findAll()).thenReturn(list);

        var people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var bookOne = people.get(1);
        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("some author 1",bookOne.getAuthor());
        assertEquals(new Date(),bookOne.getLauncheDate());
        assertEquals(25D,bookOne.getPrice());
        assertEquals("some title 1",bookOne.getTitle());

        var bookfFour = people.get(4);
        assertNotNull(bookfFour);
        assertNotNull(bookfFour.getKey());
        assertNotNull(bookfFour.getLinks());
        assertTrue(bookfFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
        assertEquals("some author 4",bookfFour.getAuthor());
        assertEquals(new Date(),bookfFour.getLauncheDate());
        assertEquals(25D,bookfFour.getPrice());
        assertEquals("some title 4",bookfFour.getTitle());

        var bookSeven = people.get(7);
        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getKey());
        assertNotNull(bookSeven.getLinks());
        assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
        assertEquals("some author 7",bookSeven.getAuthor());
        assertEquals(new Date(),bookSeven.getLauncheDate());
        assertEquals(25D,bookSeven.getPrice());
        assertEquals("some title 7",bookSeven.getTitle());

    }

    @Test
    void testCreate(){
        Book entity = input.mockEntity(1);
        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);


        lenient().when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("some author 1",result.getAuthor());
        assertEquals(new Date(),result.getLauncheDate());
        assertEquals(25D,result.getPrice());
        assertEquals("some title 1",result.getTitle());
    }

    @Test
    void testCreateWithNoBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testUpdate (){
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when (repository.findById(1L)).thenReturn(Optional.of(entity));
        lenient().when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("some author 1",result.getAuthor());
        assertEquals(new Date(),result.getLauncheDate());
        assertEquals(25D,result.getPrice());
        assertEquals("some title 1",result.getTitle());
    }

    @Test
    void testUpdateWithNoBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void  testDelete(){
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when (repository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);

    }


}

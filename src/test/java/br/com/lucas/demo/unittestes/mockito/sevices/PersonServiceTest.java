package br.com.lucas.demo.unittestes.mockito.sevices;


import br.com.lucas.demo.data.vo.v1.PersonVO;
import br.com.lucas.demo.exception.RequiredObjectIsNullException;
import br.com.lucas.demo.model.Person;
import br.com.lucas.demo.repositories.PersonRepository;
import br.com.lucas.demo.services.PersonService;
import br.com.lucas.demo.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson  input;

    @InjectMocks
    private PersonService service;
    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() throws Exception{
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById (){
        Person person = input.mockEntity(1);
        person.setId(1L);
        when (personRepository.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test 1",result.getAddress());
        assertEquals("First Name Test 1",result.getFirstName());
        assertEquals("Last Name Test 1",result.getLastName());
        assertEquals("Female",result.getGender());
    }

    @Test
    void testFindAll(){

        List<Person> list = input.mockEntityList();

        when (personRepository.findAll()).thenReturn(list);

        var people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);
        assertNotNull(personOne);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());
        assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test 1", personOne.getAddress());
        assertEquals("First Name Test 1", personOne.getFirstName());
        assertEquals("Last Name Test 1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personfFour = people.get(4);
        assertNotNull(personfFour);
        assertNotNull(personfFour.getKey());
        assertNotNull(personfFour.getLinks());
        assertTrue(personfFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
        assertEquals("Addres Test 4", personfFour.getAddress());
        assertEquals("First Name Test 4", personfFour.getFirstName());
        assertEquals("Last Name Test 4", personfFour.getLastName());
        assertEquals("Male", personfFour.getGender());

        var personfSeven = people.get(7);
        assertNotNull(personfSeven);
        assertNotNull(personfSeven.getKey());
        assertNotNull(personfSeven.getLinks());
        assertTrue(personfSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
        assertEquals("Addres Test 7", personfSeven.getAddress());
        assertEquals("First Name Test 7", personfSeven.getFirstName());
        assertEquals("Last Name Test 7", personfSeven.getLastName());
        assertEquals("Female", personfSeven.getGender());

    }

    @Test
    void testCreate(){
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);


        lenient().when(personRepository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());
    }

    @Test
    void testCreateWithNoPerson(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testUpdate (){
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when (personRepository.findById(1L)).thenReturn(Optional.of(entity));
        lenient().when(personRepository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());
    }

    @Test
    void testUpdateWithNoPerson(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void  testDelete(){
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when (personRepository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);

    }


}

package br.com.lucas.demo.services;

import br.com.lucas.demo.Controllers.PersonController;
import br.com.lucas.demo.data.vo.v1.PersonVO;
import br.com.lucas.demo.exception.RequiredObjectIsNullException;
import br.com.lucas.demo.exception.ResouceNotFoundException;
import br.com.lucas.demo.mapper.DozerMapper;
import br.com.lucas.demo.model.Person;
import br.com.lucas.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.internal.Logger;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service

public class PersonService {

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired()
    PersonRepository repository;


    public List<PersonVO> findAll (){
        logger.warn("find all person");

        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons.stream().forEach(
                p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel())
        );
        return persons;
    }

    public PersonVO findById (Long id) {
        logger.warn("find one person");

        var entity = repository.findById(id).orElseThrow(()-> new ResouceNotFoundException("no records founds for this id!"));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return  vo;

    }

    public PersonVO create (PersonVO person){

        if(person == null){
            throw  new RequiredObjectIsNullException();
        }
        logger.warn("create a person");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return  vo;
    }

    public PersonVO update (PersonVO person){
        if(person == null){
            throw  new RequiredObjectIsNullException();
        }
        logger.warn("update a person");
        var entity = repository.findById(person.getKey()).orElseThrow(()-> new ResouceNotFoundException("no records founds for this id!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return  vo;
    }

    public void delete (Long id){
        logger.warn("delete a person");
        Person entity = repository.findById(id).orElseThrow(()-> new ResouceNotFoundException("no records founds for this id!"));
        repository.delete(entity);
    }








}

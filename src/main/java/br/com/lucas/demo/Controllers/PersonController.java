package br.com.lucas.demo.Controllers;

import br.com.lucas.demo.data.vo.v1.PersonVO;


import br.com.lucas.demo.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for manage people")
public class PersonController {

    @Autowired
    private PersonService service;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Finds a people", description = "Finds a people",
    tags = {"People"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                content = @Content(schema = @Schema(implementation = PersonVO.class))),
            @ApiResponse(description = "No Content",responseCode = "204", content = {@Content}),
            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public PersonVO findById (@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Finds all people", description = "Finds all people",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = PersonVO.class))) }),

            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public List<PersonVO> findAll(){
        return service.findAll();
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://erudio.com.br"})
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a people", description = "Create a people",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonVO.class))),

            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public PersonVO create(@RequestBody PersonVO person){
       return service.create(person);
    }


    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update a people", description = "Update a people",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonVO.class))),

            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public PersonVO update(@RequestBody PersonVO person){
        return service.create(person);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a people", description = "Delete a people",
            tags = {"People"}, responses = {
            @ApiResponse(description = "No content",responseCode = "204", content = @Content()),
            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public ResponseEntity<?> delete (@PathVariable(value = "id") Long id){
         service.delete(id);
         return ResponseEntity.noContent().build();
    }




}

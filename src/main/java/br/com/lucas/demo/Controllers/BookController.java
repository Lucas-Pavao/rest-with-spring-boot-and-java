package br.com.lucas.demo.Controllers;

import br.com.lucas.demo.data.vo.v1.BookVO;
import br.com.lucas.demo.services.BookService;
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

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for manage book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Finds a book", description = "Finds a book",
    tags = {"Book"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                content = @Content(schema = @Schema(implementation = BookVO.class))),
            @ApiResponse(description = "No Content",responseCode = "204", content = {@Content}),
            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public BookVO findById (@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Finds all book", description = "Finds all book",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = BookVO.class))) }),

            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public List<BookVO> findAll(){
        return service.findAll();
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a book", description = "Create a book",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BookVO.class))),

            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public BookVO create(@RequestBody BookVO book){
       return service.create(book);
    }


    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update a book", description = "Update a book",
            tags = {"Book"}, responses = {
            @ApiResponse(description = "Sucess",responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BookVO.class))),

            @ApiResponse(description = "Bad Request",responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Unauthorized",responseCode = "401", content = {@Content}),
            @ApiResponse(description = "Not Found",responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error",responseCode = "500", content = {@Content})
    })
    public BookVO update(@RequestBody BookVO book){
        return service.create(book);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a book", description = "Delete a book",
            tags = {"Book"}, responses = {
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

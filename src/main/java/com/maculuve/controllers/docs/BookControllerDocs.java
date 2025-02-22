package com.maculuve.controllers.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.maculuve.data.dto.v1.BookDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface BookControllerDocs {
    @Operation(summary = "Finds all Book", description = "Finds all Book", tags = { "Book" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))) }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server", responseCode = "500", content = @Content)

    })
    public List<BookDTO> findAll();

    @Operation(summary = "Finds Book by Id", description = "Finds Book by Id", tags = { "Book" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server", responseCode = "500", content = @Content)

    })
    public BookDTO findById(@PathVariable(value = "id") Long id);

    @Operation(summary = "Adds a new Book", description = "Adds a new Book by passing in a JSON, XML", tags = {
            "Book" }, responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server", responseCode = "500", content = @Content)

    })
    public BookDTO store(@RequestBody BookDTO bookDTO);

    @Operation(summary = "Updated a Book", description = "Update a Book by passing in a JSON, XML", tags = {
            "Book" }, responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookDTO.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server", responseCode = "500", content = @Content)

    })
    public BookDTO update(@RequestBody BookDTO bookDTO);

    @Operation(summary = "Delete a  Book", description = "Delete a new Book by passing in a JSON, XML", tags = {
            "Book" }, responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content(schema = @Schema(implementation = BookDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server", responseCode = "500", content = @Content)

    })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id);
}

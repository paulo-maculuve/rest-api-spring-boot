package com.maculuve.controllers.docs;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.maculuve.data.dto.v1.PersonDTO;
import com.maculuve.file.exporter.MediaTypes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PersonControllerDocs {
        @Operation(summary = "Find All People", description = "Finds All People", tags = { "People" }, responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = {
                                        @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
                        }),
                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "15") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction);

        @Operation(summary = "Export People", description = "Export page of People in XLSX, CSV and PDF format", tags = { "People" }, responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = {
                                        @Content(mediaType = MediaTypes.APPLICATION_XLSX_VALUE),
                                        @Content(mediaType = MediaTypes.APPLICATION_CSV_VALUE),
                                        @Content(mediaType = MediaTypes.APPLICATION_PDF_VALUE)
                        }),
                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        })
        ResponseEntity<Resource> exportPage(
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "15") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction,
                        HttpServletRequest request);

        @Operation(summary = "Find People By firstName", description = "Finds People By firstName", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = {
                                                        @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
                                        }),
                                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                        })
        ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
                        @PathVariable(value = "firstName") String findPersonByName,
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "15") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction);

        @Operation(summary = "Finds a Person", description = "Find a specific person by your ID", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
                                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                        })
        PersonDTO findById(@PathVariable("id") Long id);

        @Operation(summary = "Massive People creation", description = "Massive People creation with upload of xslx or csv", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = {
                                                        @Content(schema = @Schema(implementation = PersonDTO.class))
                                        }),
                                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                        })
        List<PersonDTO> multCreation(
                        @RequestParam("file") MultipartFile file);

        @Operation(summary = "Adds a new Person", description = "Adds a new person by passing in a JSON, XML or YML representation of the person.", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                        })
        PersonDTO store(@RequestBody PersonDTO person);

        @Operation(summary = "Updates a person's information", description = "Updates a person's information by passing in a JSON, XML or YML representation of the updated person.", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
                                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                        })
        PersonDTO update(@RequestBody PersonDTO person);

        @Operation(summary = "Disable a Person", description = "Disable a specific person by your ID", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
                                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                        })
        PersonDTO disablePerson(@PathVariable("id") Long id);

        @Operation(summary = "Deletes a Person", description = "Deletes a specific person by their ID", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
                        })
        ResponseEntity<?> delete(@PathVariable("id") Long id);
}
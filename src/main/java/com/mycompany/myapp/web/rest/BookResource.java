package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Book;

import com.mycompany.myapp.repository.BookRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Book.
 */
@RestController
@RequestMapping("/api")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);
        
    @Inject
    private BookRepository bookRepository;

    /**
     * POST  /books : Create a new book.
     *
     * @param book the book to create
     * @return the ResponseEntity with status 201 (Created) and with body the new book, or with status 400 (Bad Request) if the book has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/books",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws URISyntaxException {
        log.debug("REST request to save Book : {}", book);
        if (book.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("book", "idexists", "A new book cannot already have an ID")).body(null);
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.created(new URI("/api/books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("book", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /books : Updates an existing book.
     *
     * @param book the book to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated book,
     * or with status 400 (Bad Request) if the book is not valid,
     * or with status 500 (Internal Server Error) if the book couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/books",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Book> updateBook(@RequestBody Book book) throws URISyntaxException {
        log.debug("REST request to update Book : {}", book);
        if (book.getId() == null) {
            return createBook(book);
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("book", book.getId().toString()))
            .body(result);
    }

    /**
     * GET  /books : get all the books.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of books in body
     */
    @RequestMapping(value = "/books",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Book> getAllBooks() {
        log.debug("REST request to get all Books");
        List<Book> books = bookRepository.findAll();
        return books;
    }

    /**
     * GET  /books/:id : get the "id" book.
     *
     * @param id the id of the book to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the book, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/books/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        log.debug("REST request to get Book : {}", id);
        Book book = bookRepository.findOne(id);
        return Optional.ofNullable(book)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /books/:id : delete the "id" book.
     *
     * @param id the id of the book to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/books/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.debug("REST request to delete Book : {}", id);
        bookRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("book", id.toString())).build();
    }

}

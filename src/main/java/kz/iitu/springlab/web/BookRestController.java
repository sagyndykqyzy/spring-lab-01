
package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    // GET: All books
    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit) {

        return service.findAll(author)
                .stream()
                .limit(limit)
                .toList();
    }

    // GET: Book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // POST: Create book
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = service.create(book);

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(saved);
    }

    // PUT: Replace book
    @PutMapping("/{id}")
    public ResponseEntity<Book> replace(
            @PathVariable long id,
            @RequestBody Book book) {

        return service.replace(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // DELETE: Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {

        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-year/{year:[0-9]{4}}")
    public ResponseEntity<List<Book>> getBooksByYear(
            @PathVariable int year) {

        List<Book> books = service.findAll(null)
                .stream()
                .filter(book -> book.year() == year)
                .toList();

        return ResponseEntity.ok(books);
    }

}

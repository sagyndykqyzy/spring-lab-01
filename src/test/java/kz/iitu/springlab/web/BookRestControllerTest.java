
package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import kz.iitu.springlab.catalog.Book;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService service;

    @Test
    @WithMockUser(roles = "USER")
    void existingBookReturns200() throws Exception {
        Book book = new Book(
                1L,
                "Dune",
                "Frank Herbert",
                1965
        );

        given(service.findById(1L))
                .willReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dune"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void missingBookReturns404WithProblemDetail() throws Exception {
        given(service.findById(999L))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Book Not Found"))
                .andExpect(jsonPath("$.detail")
                        .value("Book with ID 999 was not found"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void invalidBookReturns400() throws Exception {
        String invalidBook = """
            {
                "title": "",
                "author": "Frank Herbert",
                "year": 1965
            }
            """;

        mockMvc.perform(post("/api/books")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBook))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

}

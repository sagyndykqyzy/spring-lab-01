
package kz.iitu.springlab.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public record Book(
        Long id,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @Min(value = 1000, message = "Year must be at least 1000")
        @Max(value = 2100, message = "Year must not exceed 2100")
        int year
) {}

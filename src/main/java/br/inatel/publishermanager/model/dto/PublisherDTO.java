package br.inatel.publishermanager.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {

    @NotEmpty(message = "ID must not be empty.")
    @Size(min = 3, max = 10, message = "ID must contain from 3 to 10 characters.")
    private String id;

    @NotEmpty(message = "Name must not be empty.")
    private String name;
}

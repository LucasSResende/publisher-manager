package br.inatel.publishermanager.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    @NotEmpty(message = "Host must not be empty.")
    private String host;

    @NotEmpty(message = "Port must not be empty.")
    private String port;
}

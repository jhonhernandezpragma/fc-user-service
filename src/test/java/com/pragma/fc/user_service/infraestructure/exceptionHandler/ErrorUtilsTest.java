package com.pragma.fc.user_service.infraestructure.exceptionHandler;

import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ErrorUtilsTest {

    @Test
    void shouldBuildErrorResponseWithCorrectFields() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test-endpoint");

        String errorMessage = "Something went wrong";

        ResponseEntity<ApiError> response = ErrorUtils.buildError(HttpStatus.BAD_REQUEST, errorMessage, request);

        ApiError body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getMessage()).isEqualTo(errorMessage);
        assertThat(body.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getError()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(body.getPath()).isEqualTo("/test-endpoint");
    }
}

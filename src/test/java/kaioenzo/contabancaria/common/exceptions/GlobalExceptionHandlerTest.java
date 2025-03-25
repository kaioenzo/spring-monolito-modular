package kaioenzo.contabancaria.common.exceptions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testProcessarBaseException_ReturnsCorrectResponseEntity_WhenExceptionIsThrown() {
        // Arrange
        String expectedMessage = "Test exception message";
        int expectedStatus = HttpStatus.BAD_REQUEST.value();
        BaseException baseException = new BaseException(expectedMessage, expectedStatus);

        // Act
        ResponseEntity<ProblemDetail> responseEntity = globalExceptionHandler.processarBaseException(baseException);

        // Assert
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(expectedStatus);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(expectedMessage);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void testProcessarBaseException_ReturnsInternalServerError_WhenStatusIsInvalid() {
        // Arrange
        String expectedMessage = "Test exception message with invalid status";
        int invalidStatus = 600; // Invalid HTTP status
        BaseException baseException = Mockito.spy(new BaseException(expectedMessage, invalidStatus));

        // Mockito override for invalid status mapping
        Mockito.when(baseException.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // Act
        ResponseEntity<ProblemDetail> responseEntity = globalExceptionHandler.processarBaseException(baseException);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getDetail()).isEqualTo(expectedMessage);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
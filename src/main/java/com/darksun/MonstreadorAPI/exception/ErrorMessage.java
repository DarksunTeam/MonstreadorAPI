package com.darksun.MonstreadorAPI.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
	private HttpStatus    statusCode;
	private LocalDateTime timestamp;
	private String        message;
	private String        description;
}

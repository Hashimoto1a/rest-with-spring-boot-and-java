package br.com.hashimoto1a.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}

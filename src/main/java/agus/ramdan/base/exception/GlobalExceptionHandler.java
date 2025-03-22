package agus.ramdan.base.exception;

import io.micrometer.tracing.Tracer;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final Tracer tracer;
    private String getTraceId() {
        String traceId = MDC.get("traceId");
        if (traceId == null && tracer.currentSpan() != null && tracer.currentSpan().context() != null) {
            traceId = tracer.currentSpan().context().traceId();
        }
        return traceId;
    }

    private String getSpanId() {
        String spanId = MDC.get("spanId");
        if (spanId == null && tracer.currentSpan() != null) {
            spanId = tracer.currentSpan().context().spanId();
        }
        return spanId;
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Errors> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        String traceId = getTraceId();
//        String spanId = getSpanId();
//        return new ResponseEntity<>(ex.create(traceId,spanId,request.getDescription(false)), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Errors> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String traceId = getTraceId();
        String spanId = getSpanId();
        log.warn("trace_id={},span_id={}:{}", traceId, spanId, "Message Error");
        val error = new Errors(new Date(), "Message Error", traceId, spanId, request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Errors> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String traceId = getTraceId();
        String spanId = getSpanId();
        String errorMsg = ErrorMessage.get(ex,null);
        log.error(String.format("trace_id=%s,span_id=%s:%s", traceId, spanId, errorMsg), ex);
        val error = new Errors(new Date(), errorMsg, traceId, spanId, request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Errors> handleMethodArgumentNotValidException(ConstraintViolationException ex, WebRequest request) {
        String traceId = getTraceId();
        String spanId = getSpanId();
        log.warn(String.format("trace_id=%s,span_id=%s:%s", traceId, spanId, "ConstraintViolationException"), ex);
        val errors = ex.getConstraintViolations().stream()
                .map(violation -> new ErrorValidation(violation.getMessage(), String.valueOf(violation.getPropertyPath()), violation.getInvalidValue()))
                .collect(Collectors.toList()).toArray(new ErrorValidation[0]);
        val error = new Errors(new Date(), "Validation Error", traceId, spanId, request.getDescription(false));
        error.setErrors(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String traceId = getTraceId();
        String spanId = getSpanId();
        log.error(String.format("trace_id=%s,span_id=%s:%s", traceId, spanId, "MethodArgumentNotValidException"), ex);
        BindingResult result = ex.getBindingResult();
        val errors = result.getFieldErrors().stream()
                .map(violation -> new ErrorValidation(violation.getDefaultMessage(), violation.getField(), violation.getRejectedValue()))
                .collect(Collectors.toList()).toArray(new ErrorValidation[0]);
        val error = new Errors(new Date(), "Validation Error", traceId, spanId, request.getDescription(false));
        error.setErrors(errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> noContentException(NoContentException ex, WebRequest request) {
        String traceId = getTraceId();
        String spanId = getSpanId();
        log.error(String.format("trace_id=%s,span_id=%s:%s", traceId, spanId, ex.getMessage()), ex);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(XxxException.class)
    public ResponseEntity<Errors> xxxException(XxxException ex, WebRequest request) {
        String traceId = getTraceId();
        String spanId = getSpanId();
        log.error(String.format("trace_id=%s,span_id=%s:%s", traceId, spanId, ex.getMessage()), ex);
        return new ResponseEntity<>(ex.getOrCreate(traceId, spanId, request.getDescription(false)), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> globleExcpetionHandler(Exception ex, WebRequest request) {
        String traceId = getTraceId();
        String spanId = getSpanId();
        log.error(String.format("trace_id=%s,span_id=%s:%s", traceId, spanId, ex.getMessage()), ex);
        val error = new Errors(new Date(), "Internal Server Error Please Contact Helpdesk", traceId, spanId, request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
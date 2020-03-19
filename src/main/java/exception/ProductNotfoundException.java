package exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

public class ProductNotfoundException extends RuntimeException {
    private final HttpStatus status;
    @Nullable
    private final String reason;
    private static final long serialVersionUID = 1L;

    public ProductNotfoundException(@Nullable String reason, HttpStatus status) {
        this.status = status;
        this.reason = reason;
    }


    public HttpStatus getStatus() {
        return status;
    }

    @Nullable
    public String getReason() {
        return reason;
    }
}

package ir.baarmaan.general.exception.unchecked;

import ir.baarmaan.general.exception.BaarmaanRuntimeException;
import lombok.Data;

@Data
public class ResourceNotFoundException extends BaarmaanRuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}

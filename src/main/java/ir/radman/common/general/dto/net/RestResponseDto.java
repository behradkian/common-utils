package ir.radman.common.general.dto.net;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponseDto {

    private int statusCode;
    private String body;
    private boolean hasException;
    private String message;

}

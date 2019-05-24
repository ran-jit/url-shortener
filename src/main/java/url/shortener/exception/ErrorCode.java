package url.shortener.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
final public class ErrorCode implements Serializable {
    private Integer code;
    private String message;

    public static final ErrorCode INVALID_ID = ErrorCode.builder().code(1001).build();
}

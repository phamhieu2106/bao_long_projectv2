package org.example.sharedlibrary.base_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrapperResponse {

    private boolean isSuccessful;
    private String message;
    private int status;
    private Object data;

    public static WrapperResponse success(HttpStatus status, Object data) {
        return new WrapperResponse(true, status.getReasonPhrase()
                , HttpStatus.OK.value(), data);
    }

    public static WrapperResponse fail(String message, HttpStatus status) {
        return new WrapperResponse(false, message
                , status.value(), null);
    }

}

package common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
public class Error {
    private String message;
    private int num_error;
    private LocalDate localdate;
}


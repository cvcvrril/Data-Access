package model.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExamError {
    private final int number;
    private final String message;
    private final LocalDateTime date;

    public ExamError(int number, String message) {
        this.number = number;
        this.message = message;
        this.date = LocalDateTime.now();
    }

}

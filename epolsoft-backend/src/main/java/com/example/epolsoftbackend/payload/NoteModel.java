package com.example.epolsoftbackend.payload;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NoteModel {
    private Long bookId;
    private String bookName;
    private String bookShortDescription;
    private String bookDescription;
    private String bookFileName;
    private Long topicId;
    private String topicName;
    private Long authorId;
    private String authorName;
}

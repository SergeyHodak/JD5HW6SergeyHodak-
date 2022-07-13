package lectures.forum;

import lombok.Data;

@Data
public class Message {
    private String id;
    private String author;
    private String content;
}
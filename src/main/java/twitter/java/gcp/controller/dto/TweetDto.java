package twitter.java.gcp.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class TweetDto {
    Long id;
    String content;
    Instant lastModifiedDateTime;
    AuthorDto author;
}

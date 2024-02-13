package ru.practicum.private_api.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    @NotBlank
    String text;
    @NotBlank
    @Size(min = 1, max = 50)
    String author;
    LocalDateTime created;
}

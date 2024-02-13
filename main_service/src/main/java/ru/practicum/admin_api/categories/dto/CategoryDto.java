package ru.practicum.admin_api.categories.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    @NotBlank
    @Size(min = 1, max = 50)
    String name;
}

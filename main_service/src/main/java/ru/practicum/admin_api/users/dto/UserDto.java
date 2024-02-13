package ru.practicum.admin_api.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    @NotBlank
    @Size(min = 2, max = 250)
    String name;
    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "Неправильный адрес электронной почты")
    @Size(min = 6, max = 254)
    String email;
}

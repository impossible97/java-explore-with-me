package ru.practicum.admin_api.users.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUserDto {

    @NotBlank
    @Size(min = 2, max = 250)
    String name;
    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "Неправильный адрес электронной почты")
    @Size(min = 6, max = 254)
    String email;
}

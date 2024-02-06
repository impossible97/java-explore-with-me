package ru.practicum.private_api.request.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.request.dto.RequestDto;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeStatusRequestResult {

    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}

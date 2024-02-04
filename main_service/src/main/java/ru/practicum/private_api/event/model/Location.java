package ru.practicum.private_api.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "locations", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    double lat;
    double lon;
}


package ru.practicum.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistics", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
public class EndpointHit {
    @Id
    @SequenceGenerator(name = "statistics_sequence", sequenceName = "statistics_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistics_sequence")
    long id;
    @Column(name = "applicationName")
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}

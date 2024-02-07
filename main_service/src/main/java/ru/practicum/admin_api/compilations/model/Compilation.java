package ru.practicum.admin_api.compilations.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilation", schema = "public")
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OneToMany
    @JoinColumn(name = "event_id", unique = true)
    List<Event> events;
    Boolean pinned;
    String title;
}

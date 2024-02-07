package ru.practicum.private_api.events.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.admin_api.categories.model.Category;
import ru.practicum.admin_api.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "events", schema = "public")
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String title;
    @Column(length = 1000)
    String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;
    int confirmedRequests;
    LocalDateTime createdOn;
    @Column(length = 1000)
    String description;
    LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User initiator;
    @ElementCollection
    @CollectionTable(name = "location")
    @MapKeyColumn(name = "lat") // Широта
    @Column(name = "lon") // Долгота
    Map<Float, Float> location;
    Boolean paid;
    int participantLimit;
    LocalDateTime publishedOn;
    @Column(columnDefinition = "varchar(255) default 'true'")
    Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    EventState state;
    int views;
}

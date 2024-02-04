package ru.practicum.private_api.event.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.admin_api.categories.model.Category;
import ru.practicum.admin_api.user.model.User;

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
    String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;
    int confirmedRequests;
    LocalDateTime createdOn;
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
    boolean paid;
    int participantLimit;
    LocalDateTime publishedOn;
    boolean requestModeration;
    @Enumerated(EnumType.STRING)
    EventState eventState;
    int views;
}

package it.epicode.weeklyProjectW7.repository;

import it.epicode.weeklyProjectW7.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends JpaRepository<Event, Integer>, PagingAndSortingRepository<Event, Integer> {
}

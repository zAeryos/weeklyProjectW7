package it.epicode.weeklyProjectW7.service;

import it.epicode.weeklyProjectW7.exception.NotFoundException;
import it.epicode.weeklyProjectW7.model.Event;
import it.epicode.weeklyProjectW7.model.EventRequest;
import it.epicode.weeklyProjectW7.model.User;
import it.epicode.weeklyProjectW7.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserService     userService;

    public Page<Event> getAllEvents(Pageable pageable){
        return  eventRepository.findAll(pageable);
    }

    public Event getEventById(int id) throws NotFoundException{
        return eventRepository.findById(id).
                orElseThrow(()->new NotFoundException("Event with id = " + id + " not found"));
    }

    public Event saveEvent(EventRequest eventRequest) throws NotFoundException{
        Event event = new Event (
                                   eventRequest.getTitle(),
                                   eventRequest.getDescription(),
                                   eventRequest.getLocation(),
                                   eventRequest.getDate(),
                                   eventRequest.getMaxSpots()
                                );

        return eventRepository.save(event);

    }

    public Event updateEvent(int id, EventRequest eventRequest) throws NotFoundException {

        Event   event   = getEventById(id);

        event.setTitle          (eventRequest.getTitle());
        event.setDescription    (eventRequest.getDescription());
        event.setLocation       (eventRequest.getLocation());
        event.setDate           (eventRequest.getDate());
        event.setMaxSpots       (eventRequest.getMaxSpots());

        return eventRepository.save(event);

    }

    public Event addUserToEvent(int id, EventRequest eventRequest) throws NotFoundException {

        Event   event   = getEventById(id);
        User    user    = userService.getUserById(eventRequest.getUser_id());

        event.addUser(user);

        return eventRepository.save(event);

    }

    public void deleteEvent(int id) throws NotFoundException{
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

}

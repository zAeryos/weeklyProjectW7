package it.epicode.weeklyProjectW7.controller;

import it.epicode.weeklyProjectW7.exception.BadRequestException;
import it.epicode.weeklyProjectW7.model.Event;
import it.epicode.weeklyProjectW7.model.EventRequest;
import it.epicode.weeklyProjectW7.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public Page<Event> getAll(Pageable pageable){

        return eventService.getAllEvents(pageable);

    }

    @GetMapping("/events/{id}")
    public Event getBlogPostById(@PathVariable int id) {

        return eventService.getEventById(id);

    }

    @PostMapping("/events")
    public Event saveBlogPost(@RequestBody @Validated EventRequest eventRequest, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return eventService.saveEvent(eventRequest);

    }

    @PutMapping("/events/{id}")
    public Event updateBlogPost(@PathVariable int id, @RequestBody @Validated EventRequest eventRequest, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return eventService.updateEvent(id, eventRequest);

    }

    @PatchMapping("/events/patchlist/{id}")
    public Event addUserToEvent(@PathVariable int id, @RequestBody @Validated EventRequest eventRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return eventService.addUserToEvent(id, eventRequest);

    }

    @DeleteMapping("/events/{id}")
    public void deleteBlogPost(@PathVariable int id) {

        eventService.deleteEvent(id);

    }
}

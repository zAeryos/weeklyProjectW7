package it.epicode.weeklyProjectW7.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class EventRequest {

    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Location is mandatory")
    private String location;
    @NotBlank(message = "Date is mandatory")
    private Date date;
    @NotBlank(message = "Max amount of spots is mandatory")
    private int maxSpots;
    @NotBlank(message = "User id is mandatory")
    private int user_id;

}

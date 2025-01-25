package com.hms.Payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DTO {

    private Long id;

    private String name;

    @Size(min = 3,max = 10,message = "more then 3")
    private String username;

    @JsonIgnore
    @Size(min = 7, max = 16,message = "should be more then 7")
    private String password;

    @Email
    private String email;

}

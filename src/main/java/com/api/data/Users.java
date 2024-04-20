package com.api.data;

import lombok.*;

@NoArgsConstructor      // no-args constructor - Lombok will automatically generate a constructor with no parameters for the Users class
@AllArgsConstructor    // all-args constructor - Lombok will automatically generate a constructor that includes all fields of the class as parameters.
@Data                 // When you apply @Data to a class, Lombok will generate getters and setters for all fields, a toString method, an equals and hashCode
                     // implementation, and a constructor that includes all non-initialized fields as parameters.
@Builder             // builder
@EqualsAndHashCode  // equals and hashcode

public class Users {

    private String id;
    private String name;
    private String email;
    private String gender;
    private String status;


}
package com.ahmadjaelani.demo.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(max = 100)
    private String id;

    @Size(max = 100)
    private String username;

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String password;

    @Size(max = 200)
    private String address;
}

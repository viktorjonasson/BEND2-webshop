package org.example.BEND2webshop.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {
    @NotNull
    @Length(min=2, max=30)
    private String username, password;

    @Pattern(regexp = "Admin|Customer")
    private String role;
}

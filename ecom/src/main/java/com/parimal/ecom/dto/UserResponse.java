package com.parimal.ecom.dto;

import com.parimal.ecom.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private UserRole role;
        private AddressDTO address;

        // Getters and Setters
}

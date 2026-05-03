package com.parimal.ecom.service;

import com.parimal.ecom.dto.AddressDTO;
import com.parimal.ecom.dto.UserRequest;
import com.parimal.ecom.dto.UserResponse;
import com.parimal.ecom.model.Address;
import com.parimal.ecom.model.User;
import com.parimal.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> fetchUser(Long id) {
            return userRepository.findById(id);

    }

    @Override
    public boolean updateUser(UserRequest userRequest, Long id) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            User user = convertUserRequestToUser(userRequest);
            
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
            existingUser.setAddress(user.getAddress());
            
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
            Optional<User> existingUserOpt = userRepository.findById(id);
            if (existingUserOpt.isPresent()) {
                userRepository.deleteById(id);
                return true;
            }
            return false;
    }

    @Override
    public UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        
        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO(
                    user.getAddress().getStreet(),
                    user.getAddress().getCity(),
                    user.getAddress().getState(),
                    user.getAddress().getCountry(),
                    user.getAddress().getZipCode()
            );
            userResponse.setAddress(addressDTO);
        }
        return userResponse;
    }

    @Override
    public User convertUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipCode(userRequest.getAddress().getZipCode());
            user.setAddress(address);
        }
        return user;
    }
}

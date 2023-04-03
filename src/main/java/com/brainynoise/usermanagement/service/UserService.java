package com.brainynoise.usermanagement.service;

import com.brainynoise.usermanagement.entity.Result;
import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUsersByEmail(String email) {
        return repository.findById(email).orElse(null);
    }

    public User getUsersByEmployeeId(Integer employeeId) {
        return repository.findByIdEmployee(employeeId);
    }

    public List<User> getUsersByDocument(String document) {
        return repository.findAllByDocument(document);
    }

    public String deleteUser (String email) {
        repository.deleteById(email);
        return "El usuario ha sido eliminado";
    }

    public User updateUser(User user) {
        User existingUser = repository.findById(user.getEmail()).orElse(null);
        existingUser.setName(user.getName());
        existingUser.setName2(user.getName2());
        existingUser.setLastname(user.getLastname());
        existingUser.setLastname2(user.getLastname2());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setRole(user.getRole());
        existingUser.setDoctype(user.getDoctype());
        existingUser.setDocument(user.getDocument());
        existingUser.setJobtitle(user.getJobtitle());
        existingUser.setArea(user.getArea());
        existingUser.setObservations(user.getObservations());
        existingUser.setCreationDate(user.getCreationDate());

        return repository.save(existingUser);
    }
}

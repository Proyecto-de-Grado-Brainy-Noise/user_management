package com.brainynoise.usermanagement.service;

import com.brainynoise.usermanagement.entity.Credential;
import com.brainynoise.usermanagement.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository repository ;

    public Credential saveCredential(Credential credential) {
        return repository.save(credential);
    }

    public Credential getCredentialByEmail(String email) {
        return repository.findById(email).orElse(null);
    }
}

package com.brainynoise.usermanagement.repository;

import com.brainynoise.usermanagement.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, String> {
}
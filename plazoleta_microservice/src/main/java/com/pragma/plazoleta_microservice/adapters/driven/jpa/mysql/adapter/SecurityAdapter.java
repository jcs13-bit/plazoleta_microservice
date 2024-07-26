package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class SecurityAdapter implements ISecurityPersistencePort {
    @Override
    public Long getIdUser() {
        return getAuthenticatedUserId();
    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }


}

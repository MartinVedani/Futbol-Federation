package com.mindhub.futbol_federation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface JugadorRepository extends JpaRepository<Jugador,Long> {
}

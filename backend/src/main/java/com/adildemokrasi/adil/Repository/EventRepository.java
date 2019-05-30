package com.adildemokrasi.adil.Repository;

import com.adildemokrasi.adil.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByTitle(String name);
}

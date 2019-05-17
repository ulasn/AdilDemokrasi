package com.adildemokrasi.adil.Repository;

import com.adildemokrasi.adil.Entity.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NgoRepository extends JpaRepository<NGO, Integer> {

    Optional<NGO> findNgoByName(String name);

    List<NGO> findByNameIgnoreCaseStartingWith(String searchQuery);
}

package com.adildemokrasi.adil.Repository;

import com.adildemokrasi.adil.Entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}

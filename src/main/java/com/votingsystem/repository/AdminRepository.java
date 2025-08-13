package com.votingsystem.repository;

import com.votingsystem.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    @Query("SELECT a FROM Admin a WHERE a.name LIKE %:name%")
    List<Admin> findByNameContaining(@Param("name") String name);

    @Query("SELECT COUNT(e) FROM Election e WHERE e.admin.id = :adminId")
    long countElectionsByAdminId(@Param("adminId") Long adminId);
}
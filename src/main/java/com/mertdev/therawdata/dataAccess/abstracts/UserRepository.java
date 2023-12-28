package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;

import java.util.Optional;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mertdev.therawdata.entities.concretes.User;



@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	User findByEmail(String email);
	Optional<User> findAllByEmail(String email);
	public boolean existsByEmail(String email);
	List<User> findByFirstnameStartingWith(String firstname);
	List<User> findByLastnameStartingWith(String lastname);
	List<User> findByFirstnameStartingWithAndLastnameStartingWith(String firstname, String lastname);
	User getByUniqueName(String uniqueName);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.firstname) LIKE LOWER(:initials) OR LOWER(u.lastname) LIKE LOWER(:initials) OR LOWER(u.uniqueName) LIKE LOWER(:initials)")
    List<User> findByInitials(@Param("initials") String initials);

}

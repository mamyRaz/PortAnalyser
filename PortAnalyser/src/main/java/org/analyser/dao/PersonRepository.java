package org.analyser.dao;

import org.analyser.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
	public Person findPersonById(Long id);
	public Person findPersonByLastname(String lastname);
	public Person findPersonByFirstname(String firstname);
	public Person findPersonByEmail(String email);
	public Person findPersonByTelephone(String telephone);
}

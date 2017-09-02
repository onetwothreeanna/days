package com.annayoungyeun.days.models.data;

import com.annayoungyeun.days.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    User findByUsername(String username); //CRUD bookmark

    User findById(int id);

}
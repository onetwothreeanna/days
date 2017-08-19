package com.annayoungyeun.days.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface Chronicle extends CrudRepository<Chronicle, Integer> {

    List<Chronicle> findByUserId(int userId);
}

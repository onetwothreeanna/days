package com.annayoungyeun.days.models.data;


import com.annayoungyeun.days.models.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LocationDao extends CrudRepository<Location, Integer> {
    List<Location> findByUserId(int userId);

}

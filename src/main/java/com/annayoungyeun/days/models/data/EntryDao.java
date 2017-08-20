package com.annayoungyeun.days.models.data;


import com.annayoungyeun.days.models.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EntryDao extends CrudRepository<Entry, Integer> {
//    List<Entry> findByUserIdOrderByIdDesc(int userId);
//    List<Entry> findByChronicleIdOrderByIdDesc(int chronicleId);
}

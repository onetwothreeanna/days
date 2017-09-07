package com.annayoungyeun.days.models.data;

import com.annayoungyeun.days.models.Archive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ArchiveDao extends CrudRepository<Archive, Integer> {
}

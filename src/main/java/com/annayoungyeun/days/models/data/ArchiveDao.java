package com.annayoungyeun.days.models.data;

import com.annayoungyeun.days.models.Archive;
import com.annayoungyeun.days.models.Bundle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ArchiveDao extends CrudRepository<Archive, Integer> {
    Archive findByUserId(int userId);

}

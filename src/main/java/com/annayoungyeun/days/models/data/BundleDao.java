package com.annayoungyeun.days.models.data;

import com.annayoungyeun.days.models.Bundle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BundleDao extends CrudRepository<Bundle, Integer> {
}

package com.example.finalteammockdata.domain.introduce.repository;

import com.example.finalteammockdata.domain.introduce.entity.Introduce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IntroduceRepository extends JpaRepository<Introduce, Long> {
    Page<Introduce> findByField(String field, Pageable pageable);
}

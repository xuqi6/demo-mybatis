package com.example.dao;

import com.example.bean.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao extends JpaRepository<Test, Integer> {


    @Query("select b from Test b where (b.name > ?1 or null = ?1) ")
    List<Test> list(String name, Pageable pageRequest);

    @Query("select b from Test b where (b.name > ?1 or null = ?1) ")
    List<Test> list(String name);
}

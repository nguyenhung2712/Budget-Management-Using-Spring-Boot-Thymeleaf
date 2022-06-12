package com.nguyenhung.bta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenhung.bta.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}

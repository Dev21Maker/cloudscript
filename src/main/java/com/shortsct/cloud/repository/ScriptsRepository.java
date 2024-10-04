package com.shortsct.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shortsct.cloud.model.Script;

public interface ScriptsRepository extends JpaRepository<Script, Long> {}

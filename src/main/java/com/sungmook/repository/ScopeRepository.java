package com.sungmook.repository;

import com.sungmook.domain.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface ScopeRepository extends JpaRepository<Scope, Long> {

    Scope findByType(Scope.Type global);
}

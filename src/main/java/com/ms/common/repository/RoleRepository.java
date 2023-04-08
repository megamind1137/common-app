package com.ms.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.common.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByNameAndEnabledTrue(String name);

	boolean existsByNameAndEnabledTrue(String name);

	Optional<Role> findByRoleIdAndEnabledTrue(Integer id);

	List<Role> findByEnabledTrue();

}

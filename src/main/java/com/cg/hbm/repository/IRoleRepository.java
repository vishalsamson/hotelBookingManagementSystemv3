package com.cg.hbm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.hbm.entity.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer>{

}

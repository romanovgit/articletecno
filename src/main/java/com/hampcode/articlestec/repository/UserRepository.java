package com.hampcode.articlestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hampcode.articlestec.model.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Long>{

	Account findByUserName(String userName);
}

package com.project.memo.auth.token;

import com.project.memo.domain.memoContent.memoContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

}

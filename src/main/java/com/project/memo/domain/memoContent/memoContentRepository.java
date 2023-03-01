package com.project.memo.domain.memoContent;

import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface memoContentRepository extends JpaRepository<memoContent, Integer> {
    @Query(value = "SELECT * from memoContent where name=:user", nativeQuery = true)
    memoContent findByUser(@Param("user") String user);
}

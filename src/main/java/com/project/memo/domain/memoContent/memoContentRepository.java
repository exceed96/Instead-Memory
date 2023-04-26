package com.project.memo.domain.memoContent;

import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface memoContentRepository extends JpaRepository<memoContent, Integer> {
    @Query(value = "SELECT * from memoContent where email=:email", nativeQuery = true)
    List<memoContent> findByUser(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value ="DELETE from memoContent where uuid=:uuid", nativeQuery = true)
    int menuDelete(@Param("uuid") String uuid);

}

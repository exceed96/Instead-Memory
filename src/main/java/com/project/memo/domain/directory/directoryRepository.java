package com.project.memo.domain.directory;

import com.project.memo.domain.memoContent.memoContent;
import com.project.memo.web.DTO.dirDTO.DirResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface directoryRepository extends JpaRepository<directory, Integer> {

    @Query(value = "SELECT * from directory where email=:email", nativeQuery = true)
    List<directory> getDir(@Param("email") String email);

    @Query(value = "SELECT count(dirName) from directory where dirName=:dirName and email=:email", nativeQuery = true)
    int sameName(@Param("dirName") String dirName,@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value ="DELETE from directory where uuid=:uuid", nativeQuery = true)
    int dirDelete(@Param("uuid") String uuid);

    @Transactional
    @Modifying
    @Query(value ="UPDATE directory set dirName=:dirName where uuid=:uuid", nativeQuery = true)
    int updateDirName(@Param("uuid") String uuid, @Param("dirName") String dirName);
}

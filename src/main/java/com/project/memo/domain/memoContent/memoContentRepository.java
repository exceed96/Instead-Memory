package com.project.memo.domain.memoContent;

import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface memoContentRepository extends JpaRepository<memoContent, String> {
    @Query(value = "SELECT * from memoContent where email=:email", nativeQuery = true)
    List<memoContent> findByUser(@Param("email") String email);

    @Query(value = "SELECT * from memoContent where uuid=:uuid", nativeQuery = true)
    List<memoContent> findByUserMemo(@Param("uuid") String uuid);
    @Transactional
    @Modifying
    @Query(value ="DELETE from memoContent where uuid=:uuid", nativeQuery = true)
    int menuDelete(@Param("uuid") String uuid);

    @Query(value = "SELECT * from memoContent where uuid=:uuid", nativeQuery = true)
    List<memoContent> findMemoLine(@Param("uuid") String uuid);

    @Transactional
    @Modifying
    @Query(value ="UPDATE memoContent set important=:important , title=:title , content=:content where uuid=:uuid", nativeQuery = true) //, title=:title, content:=content
    int memoUpdated(@Param("uuid") String uuid, @Param("important") boolean important,@Param("title") String title,@Param("content") String content);

//    findImportant important하나만 찾아서 리턴
    @Query(value = "SELECT important from memoContent where uuid=:uuid", nativeQuery = true)
    boolean findImportant(@Param("uuid") String uuid);
//    updateImportant
    @Transactional
    @Modifying
    @Query(value ="UPDATE memoContent set important=:important where uuid=:uuid", nativeQuery = true)
    int updateImportant(@Param("uuid") String uuid, @Param("important") boolean important);
    @Transactional
    @Modifying
    @Query(value ="UPDATE memoContent set directory_id=:dirUUID where uuid=:memoUUID", nativeQuery = true)
    int updateDrectory_id(@Param("dirUUID") String dirUUID, @Param("memoUUID") String memoUUID);

    @Transactional
    @Modifying
    @Query(value ="UPDATE memoContent set directory_id=null where directory_id=:uuid", nativeQuery = true)
    int saveNull(@Param("uuid") String uuid);

    @Transactional
    @Modifying
    @Query(value ="UPDATE memoContent set trash=:trash where uuid=:uuid", nativeQuery = true)
    int updateTrash(@Param("trash") boolean trash,@Param("uuid") String uuid);

    @Transactional
    @Modifying
    @Query(value ="DELETE from memoContent where email=:email and trash = true", nativeQuery = true)
    int deleteAll(@Param("email") String email);

}

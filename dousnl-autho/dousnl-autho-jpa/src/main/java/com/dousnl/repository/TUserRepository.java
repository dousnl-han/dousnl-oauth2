package com.dousnl.repository;

import com.dousnl.common.JpaCommonRepository;
import com.dousnl.domain.TUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/1 13:58
 */
@Repository
public interface TUserRepository extends JpaCommonRepository<TUserEntity, Integer> {

    @Query("select e from TUserEntity e where e.id=:id")
    TUserEntity findTUserEntityById(@Param("id") Integer id);

    @Modifying
    @Query("update TUserEntity e set e.roleId=:#{#user.roleId} where e.id=:#{#user.id}")
    int updateUser(@Param("user") TUserEntity user);

    @Query(value = "select * from t_user where id=:id",nativeQuery = true)
    TUserEntity findTUserEntityId(@Param("id") Integer id);
}

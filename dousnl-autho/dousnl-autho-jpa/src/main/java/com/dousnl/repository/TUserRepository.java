package com.dousnl.repository;

import com.dousnl.common.JpaCommonRepository;
import com.dousnl.domain.TUserEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.util.List;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/1 13:58
 */
@Repository
public interface TUserRepository extends JpaCommonRepository<TUserEntity, Integer> {

    @QueryHints({@QueryHint(name="org.hibernate.cacheable",value = "true")})
    @Query("select e from TUserEntity e where e.id=:id")
    TUserEntity findById(@Param("id") Integer id);

    @Modifying
    @Query("update TUserEntity e set e.roleId=:#{#user.roleId} where e.id=:#{#user.id}")
    int updateUser(@Param("user") TUserEntity user);

    @QueryHints({@QueryHint(name="org.hibernate.cacheable",value = "true")})
    @Query(value = "select * from t_user where id=:id",nativeQuery = true)
    TUserEntity findTUserEntityId(@Param("id") Integer id);

    @QueryHints({@QueryHint(name="org.hibernate.cacheable",value = "true")})
    @Query("select e from TUserEntity e where e.roleId=:id")
    TUserEntity findByRoleId(@Param("id") Integer id);


    //@QueryHints({@QueryHint(name="org.hibernate.cacheable",value = "true")})
    @Query(value="select e.id,e.username from t_user e join (select t.id,max(role_id) from t_user t" +
            " GROUP BY id ) a where e.id=a.id and e.id in(:ids)",nativeQuery = true)
    List<Object[]> findTUserByRoleId(@Param("ids") List<Integer> ids);
}

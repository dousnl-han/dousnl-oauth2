package com.dousnl.common;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/1 14:53
 */
@NoRepositoryBean
public interface JpaCommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {
}

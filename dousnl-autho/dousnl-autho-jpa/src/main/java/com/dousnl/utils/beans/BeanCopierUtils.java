package com.dousnl.utils.beans;

import com.dousnl.utils.enums.ErrorEnums;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 对象copy的公共类
 *
 * @author 赵海涛
 * @version $ID:BeanCopierUtils, v0.1 2018/3/30 赵海涛 Exp $
 */
@Slf4j
public class BeanCopierUtils {

    private BeanCopierUtils() {
    }

    /**
     * BeanCopier实例的缓存
     */
    protected static final Map<String, BeanCopier> BEAN_COPIER_MAP = new HashMap<>();

    /**
     * 拷贝对象
     *
     * @param source
     * @param target
     * @return
     */
    public static <T> T copyOne2One(Object source, Class<T> target) {
        if (Objects.isNull(source)) {
            return null;
        }
        T instance = null;
        try {
            instance = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(ErrorEnums.SYSTEM_BEAN_COPY_ERROR.getDesc(), e);
        }
        copyProperties(source, instance);
        return instance;
    }

    /**
     * copy properties
     *
     * @param source
     * @param target
     */
    private static void copyProperties(Object source, Object target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = BEAN_COPIER_MAP.get(beanKey);
        if (copier == null) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_MAP.put(beanKey, copier);
        }
        copier.copy(source, target, null);
    }

    /**
     * 根据两个class名组装成key
     *
     * @param class1
     * @param class2
     * @return
     */
    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    /**
     * 拷贝集合
     *
     * @param source
     * @param target
     * @return
     */
    public static <T> List<T> copyList2List(List<?> source, Class<T> target) {
        if (Objects.isNull(source)) {
            return null;
        }
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        source.forEach(obj -> result.add(copyOne2One(obj, target)));
        return result;
    }

    /**
     * 分页查询的时候，把Page对象的list转换
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    private static <T> List<T> copyPage2Page(List<?> source, Class<T> target) {
        if (Objects.isNull(source)) {
            return null;
        }
        if (CollectionUtils.isEmpty(source)) {
            return new Page<>();
        }
        List<T> result = new Page<>();
        source.forEach(obj -> result.add(copyOne2One(obj, target)));
        return result;
    }

    /**
     * 分页查询对象转换
     *
     * @param pageInfo 待转换对象
     * @param target   转换的泛型类型
     * @param <T>      泛型类型
     * @return
     */
    public static <T> PageInfo<T> copyPageInfo(PageInfo<?> pageInfo, Class<T> target) {
        if (Objects.isNull(pageInfo)) {
            return null;
        }
        PageInfo<T> info = copyPageInfo(pageInfo);
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return info;
        }
        List<T> result = copyPage2Page(pageInfo.getList(), target);
        info.setList(result);
        return info;
    }

    private static <T> PageInfo<T> copyPageInfo(PageInfo<?> pageInfo) {
        PageInfo<T> info = new PageInfo<>();
        info.setPageNum(pageInfo.getPageNum());
        info.setPageSize(pageInfo.getPageSize());
        info.setSize(pageInfo.getSize());
        info.setStartRow(pageInfo.getStartRow());
        info.setEndRow(pageInfo.getEndRow());
        info.setPages(pageInfo.getPages());
        info.setPrePage(pageInfo.getPrePage());
        info.setNextPage(pageInfo.getNextPage());
        info.setIsFirstPage(pageInfo.isIsFirstPage());
        info.setIsLastPage(pageInfo.isIsLastPage());
        info.setHasPreviousPage(pageInfo.isHasPreviousPage());
        info.setHasNextPage(pageInfo.isHasNextPage());
        info.setNavigatePages(pageInfo.getNavigatePages());
        info.setNavigatepageNums(pageInfo.getNavigatepageNums());
        info.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        info.setNavigateLastPage(pageInfo.getNavigateLastPage());
        info.setTotal(pageInfo.getTotal());
        return info;
    }

    /**
     * 拷贝map
     *
     * @param source key是String类型
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> copyMap2Map(Map<String, ?> source, Class<T> target) {
        if (Objects.isNull(source)) {
            return null;
        }
        Map<String, T> map = new HashMap<>();
        source.forEach((k, v) -> map.put(k, copyOne2One(v, target)));
        return map;
    }

}

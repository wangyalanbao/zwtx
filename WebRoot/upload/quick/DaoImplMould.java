package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.TestMouldDao;
import com.hxlm.health.web.entity.TestMould;
// 从这里引入包
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao - 快捷模板
 *
 * Created by testPerson on createTime.
 *
 */
@Repository("testMouldDaoImpl")
public class TestMouldDaoImpl extends BaseDaoImpl<TestMould, Long>  implements TestMouldDao {

        // 按条件查询
}
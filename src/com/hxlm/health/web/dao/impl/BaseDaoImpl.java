/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Filter;
import com.hxlm.health.web.Order;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.BaseDao;
import com.hxlm.health.web.entity.OrderEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dao - 基类
 * 
 * 
 * 
 */
public abstract class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	/** 实体类类型 */
	private Class<T> entityClass;

	/** 别名数 */
	private static volatile long aliasCount = 0;

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) parameterizedType[0];
	}

	public T find(ID id) {
		if (id != null) {
			return entityManager.find(entityClass, id);
		}
		return null;
	}

	public T find(ID id, LockModeType lockModeType) {
		if (id != null) {
			if (lockModeType != null) {
				return entityManager.find(entityClass, id, lockModeType);
			} else {
				return entityManager.find(entityClass, id);
			}
		}
		return null;
	}

	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findList(criteriaQuery, first, count, filters, orders);
	}

	public Page<T> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findPage(criteriaQuery, pageable);
	}

	public long count(Filter... filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return count(criteriaQuery, filters != null ? Arrays.asList(filters) : null);
	}

	public void persist(T entity) {
		Assert.notNull(entity);
		entityManager.persist(entity);
	}

	public T saveAndGet(T entity) {
		persist(entity);
		return find(getIdentifier(entity));
	}

	public T saveAndReturn(T entity) {
		persist(entity);
		return entity;
	}

	public T merge(T entity) {
		Assert.notNull(entity);
		return entityManager.merge(entity);
	}


	public void remove(T entity) {
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public void refresh(T entity) {
		if (entity != null) {
			entityManager.refresh(entity);
		}
	}

	public void refresh(T entity, LockModeType lockModeType) {
		if (entity != null) {
			if (lockModeType != null) {
				entityManager.refresh(entity, lockModeType);
			} else {
				entityManager.refresh(entity);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ID getIdentifier(T entity) {
		Assert.notNull(entity);
		return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
	}

	public boolean isManaged(T entity) {
		return entityManager.contains(entity);
	}

	public void detach(T entity) {
		entityManager.detach(entity);
	}

	public void lock(T entity, LockModeType lockModeType) {
		if (entity != null && lockModeType != null) {
			entityManager.lock(entity, lockModeType);
		}
	}

	public void clear() {
		entityManager.clear();
	}

	public void flush() {
		entityManager.flush();
	}

	protected List<T> findList(CriteriaQuery<T> criteriaQuery, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, filters);
		addOrders(criteriaQuery, orders);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
			}
		}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (first != null) {
			query.setFirstResult(first);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		if (pageable == null) {
			pageable = new Pageable();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, pageable);
		addOrders(criteriaQuery, pageable);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
			}
		}
		long total = count(criteriaQuery, null);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber()) {
			pageable.setPageNumber(totalPages);
		}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return new Page<T>(query.getResultList(), total, pageable);
	}

	protected Long count(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		Assert.notNull(criteriaQuery);
		Assert.notNull(criteriaQuery.getSelection());
		Assert.notEmpty(criteriaQuery.getRoots());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		addRestrictions(criteriaQuery, filters);

		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		for (Root<?> root : criteriaQuery.getRoots()) {
			Root<?> dest = countCriteriaQuery.from(root.getJavaType());
			dest.alias(getAlias(root));
			copyJoins(root, dest);
		}

		Root<?> countRoot = getRoot(countCriteriaQuery, criteriaQuery.getResultType());
		countCriteriaQuery.select(criteriaBuilder.count(countRoot));

		if (criteriaQuery.getGroupList() != null) {
			countCriteriaQuery.groupBy(criteriaQuery.getGroupList());
		}
		if (criteriaQuery.getGroupRestriction() != null) {
			countCriteriaQuery.having(criteriaQuery.getGroupRestriction());
		}
		if (criteriaQuery.getRestriction() != null) {
			countCriteriaQuery.where(criteriaQuery.getRestriction());
		}
		return entityManager.createQuery(countCriteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	private synchronized String getAlias(Selection<?> selection) {
		if (selection != null) {
			String alias = selection.getAlias();
			if (alias == null) {
				if (aliasCount >= 1000) {
					aliasCount = 0;
				}
				alias = "healthlmGeneratedAlias" + aliasCount++;
				selection.alias(alias);
			}
			return alias;
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
		if (criteriaQuery != null) {
			return getRoot(criteriaQuery, criteriaQuery.getResultType());
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<?> criteriaQuery, Class<T> clazz) {
		if (criteriaQuery != null && criteriaQuery.getRoots() != null && clazz != null) {
			for (Root<?> root : criteriaQuery.getRoots()) {
				if (clazz.equals(root.getJavaType())) {
					return (Root<T>) root.as(clazz);
				}
			}
		}
		return null;
	}

	private void copyJoins(From<?, ?> from, From<?, ?> to) {
		for (Join<?, ?> join : from.getJoins()) {
			Join<?, ?> toJoin = to.join(join.getAttribute().getName(), join.getJoinType());
			toJoin.alias(getAlias(join));
			copyJoins(join, toJoin);
		}
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}

	private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}


	private void addRestrictions(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		if (criteriaQuery == null || filters == null || filters.isEmpty()) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		for (Filter filter : filters) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
				continue;
			}
			if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(filter.getProperty()), (String) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.isNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
			} else if (filter.getOperator() == Filter.Operator.isNotNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addRestrictions(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		if (StringUtils.isNotEmpty(pageable.getSearchProperty()) && StringUtils.isNotEmpty(pageable.getSearchValue())) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(pageable.getSearchProperty()), "%" + pageable.getSearchValue() + "%"));
		}
		if (pageable.getFilters() != null) {
			for (Filter filter : pageable.getFilters()) {
				if (filter == null || StringUtils.isEmpty(filter.getProperty())) {
					continue;
				}
				if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String> get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get(filter.getProperty()), (Number) filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String> get(filter.getProperty()), (String) filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.isNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
				} else if (filter.getOperator() == Filter.Operator.isNotNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
				}
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, List<Order> orders) {
		if (criteriaQuery == null || orders == null || orders.isEmpty()) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty()) {
			orderList.addAll(criteriaQuery.getOrderList());
		}
		for (Order order : orders) {
			if (order.getDirection() == Order.Direction.asc) {
				orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
			} else if (order.getDirection() == Order.Direction.desc) {
				orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
			}
		}
		criteriaQuery.orderBy(orderList);
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null) {
			return;
		}
		Root<T> root = getRoot(criteriaQuery);
		if (root == null) {
			return;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty()) {
			orderList.addAll(criteriaQuery.getOrderList());
		}
		if (StringUtils.isNotEmpty(pageable.getOrderProperty()) && pageable.getOrderDirection() != null) {
			if (pageable.getOrderDirection() == Order.Direction.asc) {
				orderList.add(criteriaBuilder.asc(root.get(pageable.getOrderProperty())));
			} else if (pageable.getOrderDirection() == Order.Direction.desc) {
				orderList.add(criteriaBuilder.desc(root.get(pageable.getOrderProperty())));
			}
		}
		if (pageable.getOrders() != null) {
			for (Order order : pageable.getOrders()) {
				if (order.getDirection() == Order.Direction.asc) {
					orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
				} else if (order.getDirection() == Order.Direction.desc) {
					orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
				}
			}
		}
		criteriaQuery.orderBy(orderList);
	}

}
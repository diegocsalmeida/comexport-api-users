package br.net.comexport.api.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.net.comexport.api.model.User;
import br.net.comexport.api.repository.filter.UserFilter;

public class UsersRepositoryImpl implements UsersRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<User> filter(UserFilter userFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		
		Predicate[] predicates = createRestrictions(userFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<User> query = manager.createQuery(criteria);
		addRestrictionPagination(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(userFilter));
	}

	private Long total(UserFilter userFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<User> root = query.from(User.class);
		
		Predicate[] predicates = createRestrictions(userFilter, builder, root);
		query.where(predicates);
		
		query.select(builder.count(root));
		return manager.createQuery(query).getSingleResult();
	}

	private void addRestrictionPagination(TypedQuery<User> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalItensPerPage = pageable.getPageSize();
		int firstItemPage = currentPage * totalItensPerPage;
		
		query.setFirstResult(firstItemPage);
		query.setMaxResults(totalItensPerPage);
		
	}

	private Predicate[] createRestrictions(UserFilter userFilter, CriteriaBuilder builder, Root<User> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(userFilter.getName())) {
			predicates.add(builder.like(builder.lower(root.get("name")), "%" +userFilter.getName()+ "%"));
		}
		
		if(!StringUtils.isEmpty(userFilter.getEmail())) {
			predicates.add(builder.like(builder.lower(root.get("email")), "%" +userFilter.getEmail()+ "%"));
		}
		
		if(userFilter.getBirthDate() != null) {
			predicates.add(builder.equal(root.get("birthDate"), userFilter.getBirthDate()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}

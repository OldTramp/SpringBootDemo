package com.example.demo.repository;


import com.example.demo.domain.Person;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersonSearch {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> search(String text) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Person.class).get();

        Query query = queryBuilder
                .keyword()
                .onFields("firstName")//, "lastName", "email", "account"
                .matching(text)
                .createQuery();

        // wrap Lucene query in an Hibernate Query object
        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Person.class);

        @SuppressWarnings("unchecked")
        List<Person> results = jpaQuery.getResultList();

        return results;
    }
}

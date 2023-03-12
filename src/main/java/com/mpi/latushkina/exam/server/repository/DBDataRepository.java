//package com.mpi.latushkina.exam.server.repository;
//
//import com.mpi.latushkina.exam.server.models.Measurement;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class DBDataRepository implements IDataRepository {
//    private final EntityManager entityManager;
//
//    @Autowired
//    public DBDataRepository(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @Transactional
//    public void addMeasurement(Measurement measurement) {
//        entityManager.persist(measurement);
//    }
//
//    public List<Measurement> findMeasurements(int startIndex, int endIndex) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Measurement> query = cb.createQuery(Measurement.class);
//        Root<Measurement> root = query.from(Measurement.class);
//        query.select(root).where(cb.between(root.get("id"), startIndex, endIndex));
//        TypedQuery<Measurement> typedQuery = entityManager.createQuery(query);
//        return typedQuery.getResultList();
//    }
//}

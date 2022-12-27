package db2.mappers;

import db2.MyFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public abstract class BaseMapper <T>{
    public List<T> findAll() {
        MyFactory hibernateUtils = MyFactory.getInstance();
        EntityManager entityManager = MyFactory.getEntityManager();
        TypedQuery<T> typedQuery = entityManager.createNamedQuery(getTableName()+".all", getType());
        List<T> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    public void save(T entity) {
        MyFactory hibernateUtils = MyFactory.getInstance();
        EntityManager entityManager = MyFactory.getEntityManager();
        EntityTransaction transaction = MyFactory.getInstance().getTransaction();

        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
            entityManager.close();
        }
    }

    public void edit(T entity) {
        MyFactory hibernateUtils = MyFactory.getInstance();
        EntityManager entityManager = MyFactory.getEntityManager();
        EntityTransaction transaction = MyFactory.getInstance().getTransaction();
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
            entityManager.close();
        }
    }

    public void delete(T entity) {
        MyFactory hibernateUtils = MyFactory.getInstance();
        EntityManager entityManager = MyFactory.getEntityManager();
        EntityTransaction transaction = MyFactory.getInstance().getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
            entityManager.close();
        }
    }

    public List<T> findByField(String parameter, Object field){
        MyFactory hibernateUtils = MyFactory.getInstance();
        EntityManager entityManager = MyFactory.getEntityManager();
        TypedQuery <T> typedQuery = entityManager.createNamedQuery(getTableName()+parameter, getType());
        typedQuery.setParameter(1, field);
        List<T> list = typedQuery.getResultList();
        entityManager.close();
        return list;
    }

    protected abstract Class<T> getType();

    protected abstract String getTableName();
}

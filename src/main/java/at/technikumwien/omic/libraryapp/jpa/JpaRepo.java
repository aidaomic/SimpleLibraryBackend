package at.technikumwien.omic.libraryapp.jpa;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

public class JpaRepo<T, ID> extends SimpleJpaRepository<T, ID> {

    private final EntityManager em;

    public JpaRepo(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        return em.merge(entity);
    }

}

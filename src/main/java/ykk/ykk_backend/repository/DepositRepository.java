package ykk.ykk_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ykk.ykk_backend.entity.DepositEntity;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Long> {
}

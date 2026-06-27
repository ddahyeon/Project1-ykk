package ykk.ykk_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ykk.ykk_backend.common.SavingType;
import ykk.ykk_backend.entity.AccountEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUseridAndSavingtype(String userid, SavingType savingtype);
    List<AccountEntity> findByUserid(String userid);
}


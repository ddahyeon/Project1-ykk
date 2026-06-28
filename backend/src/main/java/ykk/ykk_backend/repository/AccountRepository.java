package ykk.ykk_backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ykk.ykk_backend.common.SavingType;
import ykk.ykk_backend.entity.AccountEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUseridAndSavingtype(String userid, SavingType savingtype);
    List<AccountEntity> findByUserid(String userid);


    @Modifying
    @Transactional
    @Query(value = """
        UPDATE account
        SET
            interest = interest + (amount * interestrate) / 10000,
            amount = CASE
                WHEN savingtype = 3 THEN amount + (amount * interestrate) / 10000
                ELSE amount
            END
    """, nativeQuery = true)
    void updateInterestAll();
}


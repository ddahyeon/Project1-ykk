package ykk.ykk_backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Transactional
    @Query(value = """ 
    UPDATE account
    SET
        interest = interest + (amount * interestrate/ 12 ) / 10000,
            amount = CASE
            WHEN savingtype = 3 THEN amount + (amount * interestrate/12) / 10000
            ELSE amount
    END
    WHERE
        (HOUR(NOW()) * 60 + MINUTE(NOW())) > start_time
        AND (HOUR(NOW()) * 60 + MINUTE(NOW())) < end_time
        AND MOD(((HOUR(NOW()) * 60 + MINUTE(NOW())) - start_time), 2) = 0;
    """, nativeQuery = true)
    void updateInterest();

    @Modifying
    @Transactional
    @Query(value = """
    UPDATE account a0
    JOIN account a1
        ON a0.userid = a1.userid
        AND a0.savingtype = 0
        AND a1.savingtype != 0
    SET
        a0.amount = a0.amount +
        CASE
            WHEN a1.savingtype = 2 THEN
                ((a1.amount + a1.interest) * :rate) / 1000
            ELSE
                (a1.amount + a1.interest)
        END
    WHERE
        (HOUR(NOW()) * 60 + MINUTE(NOW())) >= a1.end_time
    """, nativeQuery = true)
    void transferExpired(@Param("rate") float rate);

    @Modifying
    @Transactional
    @Query(value = """
    DELETE FROM account
        WHERE savingtype <> 0
        AND (HOUR(NOW()) * 60 + MINUTE(NOW())) >= end_time
    """, nativeQuery = true)
    void deleteExpired();
}


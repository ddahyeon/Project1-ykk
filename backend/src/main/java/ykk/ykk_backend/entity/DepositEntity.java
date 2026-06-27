package ykk.ykk_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import ykk.ykk_backend.common.DepositType;
import ykk.ykk_backend.common.SavingType;

@Entity
@Table(name = "depositlog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 아이디
    @Column(nullable = false)
    private String userid;

    // 0 : 입금, 1 : 출금
    @Column(nullable = false)
    private DepositType iotype;

    // 일반, 적금, ..
    @Column(nullable = false)
    private SavingType savingtype;

    // 금액
    @Column(nullable = false)
    private int amount;
}
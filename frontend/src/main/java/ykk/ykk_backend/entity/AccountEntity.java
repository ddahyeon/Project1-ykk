package ykk.ykk_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import ykk.ykk_backend.common.SavingType;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountnum;

    // 회원 아이디
    @Column(nullable = false)
    private String userid;

    @Column(nullable = false)
    private SavingType savingtype;

    @Column(nullable = false)
    private int interest;

    @Column(nullable = false)
    private int interestrate;

    // 금액
    @Column(nullable = false)
    private int amount;
}

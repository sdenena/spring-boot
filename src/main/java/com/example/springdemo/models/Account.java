package com.example.springdemo.models;

import com.example.springdemo.base.entity.BaseEntity;
import com.example.springdemo.dto.AccountDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "cus_account")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name", length = 50)
    private String accountHolderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 20)
    private AccountType accountType = AccountType.CHECKING;

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(Long id, String accountHolderName, AccountType accountType, BigDecimal balance) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public AccountDto toAccountDto() {
        return new AccountDto(id, accountHolderName, accountType, balance);
    }
}

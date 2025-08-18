package com.example.springdemo.services.implementations;

import com.example.springdemo.dto.AccountDto;
import com.example.springdemo.exceptions.CustomException;
import com.example.springdemo.models.Account;
import com.example.springdemo.repositories.AccountRepository;
import com.example.springdemo.services.AccountService;
import com.example.springdemo.utils.Generator;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(Account req) {
        req.setAccountNumber(Generator.generateAccountNumber(accountRepository.findTop() + 1));
        return accountRepository.save(req);
    }

    @Override
    public Account updateAccount(Long id, AccountDto req) {
        Account accountObj = getAccountDetail(id);
        Account updatedAccount = req.updateAccount(accountObj);
        return accountRepository.save(updatedAccount);
    }

    @Override
    public Account getAccountDetail(Long id) {
        return accountRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new CustomException(404, "Account not found"));
    }

    @Override
    public Page<Account> getAccountList(String query, int page, int size) {
        return accountRepository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (query != null) {
                var searchAccountName = cb.like(cb.upper(root.get("accountHolderName")), "%" + query.toUpperCase() + "%");
                var searchAccountNum = cb.like(cb.upper(root.get("accountNumber")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(searchAccountName, searchAccountNum));
            }

            predicates.add(cb.isTrue(root.get("status")));
            Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {
        Account accountObj = getAccountDetail(id);
        accountObj.setStatus(false);

        accountRepository.save(accountObj);
    }
}

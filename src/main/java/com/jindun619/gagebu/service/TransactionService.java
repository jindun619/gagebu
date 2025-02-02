package com.jindun619.gagebu.service;

import com.jindun619.gagebu.entity.Transaction;
import com.jindun619.gagebu.repository.TransactionRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> getAllTransactions(String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return repository.findAll(sort);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return repository.findById(id);
    }

    public boolean updateTransaction(Long id, @Valid Transaction transaction) {
        Optional<Transaction> existingTransaction = repository.findById(id);
        if (existingTransaction.isPresent()) {
            Transaction updatedTransaction = existingTransaction.get();
            updatedTransaction.setContent(transaction.getContent());
            updatedTransaction.setDate(transaction.getDate());
            updatedTransaction.setCategory(transaction.getCategory());
            updatedTransaction.setAmount(transaction.getAmount());
            updatedTransaction.setCurrency(transaction.getCurrency());
            updatedTransaction.setDescription(transaction.getDescription());
            updatedTransaction.setType(transaction.getType());

            repository.save(updatedTransaction);
            return true;
        }
        return false;
    }

    public boolean deleteTransaction(Long id) {
        if (repository.existsById(id)) {  // 해당 ID가 존재하는지 확인
            repository.deleteById(id);    // 존재하면 삭제
            return true;
        }
        return false;  // 존재하지 않으면 false 반환
    }

}

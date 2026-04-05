package com.Rim.Account_Book.service;

import com.Rim.Account_Book.domain.AccountBook;
import com.Rim.Account_Book.domain.CategorySumDto;
import com.Rim.Account_Book.repository.AccountBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;

    @CacheEvict(value = "categoryStats", allEntries = true)
    @Transactional
    public Long saveRecord(AccountBook record) {
        return accountBookRepository.save(record).getId();
    }

    public AccountBook getRecord(Long id) {
        return accountBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 내역이 없습니다. id = " + id));
    }

    public List<AccountBook> getAllRecords() {
        return accountBookRepository.findAll();
    }

    @CacheEvict(value = "categoryStats", allEntries = true)
    @Transactional
    public void updateRecord(Long id, AccountBook updateParam) {
        AccountBook record = accountBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 내역이 없습니다. ID = " + id));

        record.update(updateParam.getType(), updateParam.getAmount(), updateParam.getCategory(), updateParam.getDate(), updateParam.getMemo());
    }

    @CacheEvict(value = "categoryStats", allEntries = true)
    @Transactional
    public void deleteRecord(Long id) {
        AccountBook record = accountBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 내역이 없습니다. ID = " + id));
        accountBookRepository.delete(record);
        }

    public List<CategorySumDto> getCategoryStatistics() {
        return accountBookRepository.findCategorySums();
    }

    public Long getTotalAmountByPeriod(LocalDate startDate, LocalDate endDate) {
        Long total = accountBookRepository.sumAmountByDateBetween(startDate, endDate);
        return total != null ? total : 0L;
    }

    @Cacheable(value = "categoryStats", key = "#startDate.toString() + #endDate.toString() + #sortType")
    public List<CategorySumDto> getCategoryStatsByPeriod(LocalDate startDate, LocalDate endDate, String sortType) {
        List<CategorySumDto> stats = accountBookRepository.findCategorySumsByPeriod(startDate, endDate);

        if (sortType == null) return stats;

        switch (sortType) {
            case "amount_desc":
                stats.sort((a, b) -> Long.compare(b.getTotalAmount(), a.getTotalAmount()));
                break;
            case "amount_asc":
                stats.sort((a, b) -> Long.compare(a.getTotalAmount(), b.getTotalAmount()));
                break;
            case "name_asc":
                stats.sort((a, b) -> String.valueOf(a.getCategory()).compareTo(String.valueOf(b.getCategory())));
                break;
        }
        return stats;
    }

    public List<CategorySumDto> getTodayCategoryStats() {
        LocalDate today = LocalDate.now();
        return accountBookRepository.findCategorySumsByPeriod(today, today);
    }

    public List<AccountBook> searchByMemo(String keyword) {
        return accountBookRepository.findByMemoContaining(keyword);
    }

    public List<AccountBook> searchByCategory(String category) {
        return accountBookRepository.findByCategory(category);
    }

    public List<AccountBook> searchByAmount(Long minAmount, Long maxAmount, String sortType) {
        Sort sort = Sort.by(Sort.Direction.DESC, "amount");

        if ("asc".equalsIgnoreCase(sortType)) {
            sort = Sort.by(Sort.Direction.ASC, "amount");
        }

        return accountBookRepository.findByAmountBetween(minAmount, maxAmount, sort);
    }
}



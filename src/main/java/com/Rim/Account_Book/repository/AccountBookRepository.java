package com.Rim.Account_Book.repository;

import com.Rim.Account_Book.domain.AccountBook;
import com.Rim.Account_Book.domain.CategorySumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.util.List;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    @Query("SELECT new com.Rim.Account_Book.domain.CategorySumDto(a.category, SUM(a.amount)) " +
            "FROM AccountBook a " +
            "GROUP BY a.category")
    List<CategorySumDto> findCategorySums();

    @Query("SELECT SUM(a.amount) FROM AccountBook a " +
            "WHERE a.date BETWEEN :startDate AND :endDate")
    Long sumAmountByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT new com.Rim.Account_Book.domain.CategorySumDto(a.category, SUM(a.amount))" +
            "FROM AccountBook a " +
            "WHERE a.date BETWEEN :startDate AND :endDate " +
            "GROUP BY a.category")
    List<CategorySumDto> findCategorySumsByPeriod(LocalDate startDate, LocalDate endDate);
    List<AccountBook> findByMemoContaining(String keyword);
    List<AccountBook> findByCategory(String category);
    List<AccountBook> findByAmountBetween(Long minAmount, Long maxAmount, Sort sort);
}

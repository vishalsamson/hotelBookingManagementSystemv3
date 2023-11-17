package com.cg.hbm.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.hbm.dto.ReviewDTO;
import com.cg.hbm.entity.Review;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {
}

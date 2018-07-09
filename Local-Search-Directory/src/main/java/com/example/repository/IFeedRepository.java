package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Feed;

@Repository
public interface IFeedRepository extends JpaRepository<Feed, Long> {

}

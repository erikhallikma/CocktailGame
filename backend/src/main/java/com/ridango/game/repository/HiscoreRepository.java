package com.ridango.game.repository;

import com.ridango.game.model.Hiscore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HiscoreRepository extends JpaRepository<Hiscore, Long> {
    List<Hiscore> findTop10ByOrderByScoreDesc();
}

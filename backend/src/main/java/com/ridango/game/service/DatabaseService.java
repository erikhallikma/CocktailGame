package com.ridango.game.service;

import com.ridango.game.model.Hiscore;
import com.ridango.game.model.HiscoreRequest;
import com.ridango.game.repository.HiscoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DatabaseService {

    private final HiscoreRepository hiscoreRepository;

    public void saveHiscore(HiscoreRequest request) {
        Hiscore hiscore = new Hiscore();
        hiscore.setName(request.getName());
        hiscore.setScore(request.getScore());

        hiscoreRepository.save(hiscore);
    }

    @Transactional(readOnly = true)
    public List<Hiscore> getTop10Scores() {
        return hiscoreRepository.findTop10ByOrderByScoreDesc();
    }
}

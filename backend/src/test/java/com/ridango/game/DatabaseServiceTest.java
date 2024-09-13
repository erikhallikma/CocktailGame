package com.ridango.game;

import com.ridango.game.model.Hiscore;
import com.ridango.game.model.HiscoreRequest;
import com.ridango.game.repository.HiscoreRepository;
import com.ridango.game.service.DatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class DatabaseServiceTest {
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private HiscoreRepository hiscoreRepository;

    @BeforeEach
    void setUp() {
        hiscoreRepository.deleteAll();
    }

    @Test
    void saveHiscore() {
        HiscoreRequest request = new HiscoreRequest();
        request.setName("Player One");
        request.setScore(100);

        databaseService.saveHiscore(request);

        List<Hiscore> hiscores = hiscoreRepository.findAll();
        assertEquals(1, hiscores.size());
        assertEquals("Player One", hiscores.get(0).getName());
        assertEquals(100, hiscores.get(0).getScore());
    }

    @Test
    void getTop10Scores() {
        for (int i = 1; i <= 15; i++) {
            HiscoreRequest request = new HiscoreRequest();
            request.setName("Player " + i);
            request.setScore(100 + i);
            databaseService.saveHiscore(request);
        }

        List<Hiscore> top10Scores = databaseService.getTop10Scores();
        assertEquals(10, top10Scores.size());
        assertEquals(115, top10Scores.get(0).getScore());
        assertEquals(106, top10Scores.get(9).getScore());
    }
}

package org.zerock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;

import java.util.stream.IntStream;

@SpringBootTest
@Commit
@Slf4j
class WebBoardRepositoryTests {

    @Autowired
    WebBoardRepository repo;

    @Test
    public void insertBoardDummies() {

        IntStream.range(0, 300).forEach(i -> {

            WebBoard board = new WebBoard();

            board.setTitle("Sample Board Title " + i);
            board.setContent("Content Sample ..." + i + " of Board ");
            board.setWriter("user0" + (i % 10));

            repo.save(board);
        });
    }

    @Test
    public void testList1() {

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = repo.findAll(
                repo.makePredicate(null, null), pageable);

        log.info("PAGE: " + result.getPageable());

        log.info("----------------------");

        log.info("PageNumber: " + result.getPageable().getPageNumber());

        log.info("TotalPages " + result.getTotalPages());

        log.info("" + result.getPageable());

        result.getContent().forEach(board -> log.info("" + board));

    }
    @Test
    void testList2() {

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = repo.findAll(repo.makePredicate("t", "10"), pageable);

        log.info("PAGE : " + result.getPageable());

        log.info("---------------------------------");
        result.getContent().forEach(board -> log.info("" + board));
    }
}


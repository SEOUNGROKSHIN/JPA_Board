package org.zerock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
@Commit
class WebReplyRepositoryTest {

    @Autowired
    WebReplyRepository repo;

    @Test
    void testInsetReplies() {

        Long[] arr = {300L, 299L, 298L};

        Arrays.stream(arr).forEach(num -> {

            WebBoard board = new WebBoard();
            board.setBno(num);

            IntStream.range(0, 10).forEach(i -> {

                WebReply reply = new WebReply();
                reply.setReplyText("REPLY..." + i);
                reply.setReplyer("replyer" + i);
                reply.setBoard(board);

                repo.save(reply);
            });
        });

    }
}
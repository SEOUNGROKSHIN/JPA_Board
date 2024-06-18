package org.zerock.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;

import java.util.List;

@RestController
@RequestMapping("/replies/*")
@Slf4j
public class WebReplyController {

    @Autowired
    private WebReplyRepository replyRepo;

    @Transactional
    @PostMapping("/{bno}")
    public ResponseEntity<WebReply> addReply(
            @PathVariable("bno") Long bno,
            @RequestBody WebReply reply) {

        log.info("addReply.....................");
        log.info("BNO: {}", bno);
        log.info("REPLY: {}", reply);

        WebBoard board = new WebBoard();
        board.setBno(bno);
        reply.setBoard(board);

        replyRepo.save(reply);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private List<WebReply> getListByBoard(WebBoard board) throws RuntimeException {

        log.info("getListByBoard....{}", board);
        return replyRepo.getRepliesOfBoard(board);
    }

    @Transactional
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<WebReply>> remove(
            @PathVariable("bno") Long bno,
            @PathVariable("rno") Long rno) {

        log.info("delete reply : " + rno);

        replyRepo.deleteById(rno);

        WebBoard board = new WebBoard();
        board.setBno(bno);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{bno}")
    public ResponseEntity<List<WebReply>> modify(@PathVariable("bno") Long bno,
                                                 @RequestBody WebReply reply) {

        log.info("modify....................." + reply);

        replyRepo.findById(reply.getRno()).ifPresent(origin -> {

            origin.setReplyText(reply.getReplyText());

            replyRepo.save(origin);
        });

        WebBoard board = new WebBoard();
        board.setBno(bno);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WebReply>> getReplies(
            @PathVariable("bno") Long bno) {

        log.info("get All Replies...............");

        WebBoard board = new WebBoard();
        board.setBno(bno);
        return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
    }
}
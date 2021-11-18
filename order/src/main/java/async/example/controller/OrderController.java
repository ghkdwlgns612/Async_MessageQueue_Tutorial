package async.example.controller;

import async.example.domain.OrderRequestDto;
import async.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/sync/v1")
    public ResponseEntity<String> orderV1(@RequestBody OrderRequestDto orderRequest) { // 단순한 동기 요청입니다.
        boolean result = orderService.orderSync(orderRequest);
        if (result == Boolean.FALSE) {
            return new ResponseEntity<>("주문 실패", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("주문 완료", HttpStatus.OK);
    }

    @PostMapping("/sync/v2")
    public ResponseEntity<String> orderV2(@RequestBody OrderRequestDto orderRequest) { // 결제가 성공하는 것을 보장하는 동기 요청 입니다.
        orderService.orderUntilSucceed(orderRequest);
        return new ResponseEntity<>("주문 완료", HttpStatus.OK);
    }

    @PostMapping("/async/v1")
    public ResponseEntity<String> orderAsync(@RequestBody OrderRequestDto orderRequest) { //비동기 요청입니다.
        orderService.orderAsync(orderRequest);
        return new ResponseEntity<>("주문 요청이 생성되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/async/result/v1")
    public ResponseEntity<String> orderAsyncResult(@RequestBody Integer prodId) { // 비동기 요청 후 처리 결과를 받는 API 입니다.
        orderService.orderAsyncResult(prodId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/kafka/template")
    public ResponseEntity<String> orderKafkaTemplate(@RequestBody OrderRequestDto orderRequest) { //kafkaTemplate를 이용한 요청입니다.
        orderService.orderTemplate(orderRequest);
        return new ResponseEntity<>("주문 요청이 생성되었습니다.", HttpStatus.OK);
    }

}

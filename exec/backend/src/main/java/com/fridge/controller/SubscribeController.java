package com.fridge.controller;

//dummy
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fridge.model.Subscribe;
import com.fridge.model.User;
import com.fridge.model.service.SubscribeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SubscribeController v0.1")
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
	@Autowired
	SubscribeService subscribeservice;

	private static final Logger logger = LoggerFactory.getLogger(SubscribeController.class);

	private static final String MESSAGE = "message";
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Operation(summary = "구독 하기", description = "받은 아이디를 기준으로 구독한다")
	@PostMapping
	public ResponseEntity<Map<String, Object>> insertscribe(@RequestParam("userId") int userId,
			@RequestParam("subscribeId") int subscribeId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		try {
			User user = new User(userId);
			Subscribe subscribe = new Subscribe(user, subscribeId);
			subscribeservice.insertscribe(subscribe);

			resultMap.put(MESSAGE, SUCCESS);
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put(MESSAGE, FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@Operation(summary = "구독 확인", description = "편재 유저가 구독한 목록")
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getscribe(@PathVariable("id") int id) {
		System.out.println(id);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		try {
			List<User> userlist = new LinkedList<User>();
			userlist = subscribeservice.getscribe(id);

			resultMap.put("userlist", userlist);
			resultMap.put(MESSAGE, SUCCESS);
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put(MESSAGE, FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}

	@Operation(summary = "구독 취소", description = "현재 유저가 구독한 유저를 취소한다.")
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deletescribe(@RequestParam("id") int id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		try {
			subscribeservice.deletescribe(id);
			
			resultMap.put(MESSAGE, SUCCESS);
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put(MESSAGE, FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
}

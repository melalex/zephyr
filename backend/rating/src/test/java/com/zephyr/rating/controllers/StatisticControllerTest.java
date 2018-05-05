package com.zephyr.rating.controllers;

import com.zephyr.rating.RatingTestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(RatingTestConfiguration.class)
public class StatisticControllerTest {

}
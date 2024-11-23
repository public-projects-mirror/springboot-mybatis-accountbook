package com.example

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.example.mapper")
class SpringbootMybatisAccountbookApplication

fun main(args: Array<String>) {
    runApplication<SpringbootMybatisAccountbookApplication>(*args)
}

package com.example.bookcopy.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void LombokFucTest(){
        String name =  "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //당연히 같은게 맞음 근데 누가 그사이에 뭐 만지면 달라지나?
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
/*
    book p.73
    assertThat이 뭘까?
    Test용
*/

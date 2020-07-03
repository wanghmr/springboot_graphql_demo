package com.example.graphql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wh
 * @date 2020/7/3
 * Description:
 */
@Data
@AllArgsConstructor
public class Card {
    private Long id;
    private String address;
}

package com.github.tablesheep233.tx.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayMessage implements Serializable {
    private Long orderId;
}

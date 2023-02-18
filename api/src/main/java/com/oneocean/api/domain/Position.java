package com.oneocean.api.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Position {
    private int x;
    private int y;
}

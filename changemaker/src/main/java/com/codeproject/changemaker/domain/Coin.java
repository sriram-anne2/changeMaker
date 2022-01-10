package com.codeproject.changemaker.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class Coin {

    private int pennies;
    private int nickels;
    private int dimes;
    private int quarters;
    private int fiftyCent;


    public Coin() {
        this.pennies = 100;
        this.nickels = 100;
        this.dimes = 100;
        this.quarters = 100;
        this.fiftyCent = 100;
    }
}

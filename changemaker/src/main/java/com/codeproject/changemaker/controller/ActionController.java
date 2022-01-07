package com.codeproject.changemaker.controller;

import com.codeproject.changemaker.domain.Coin;
import com.codeproject.changemaker.exception.ChangeMakerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ActionController {

    // COIN VALUE IN CENTS
    final int pennyValue = 1;
    final int nickelValue = 5;
    final int dimeValue = 10;
    final int quarterValue = 25;

    final ArrayList<Integer> availableBills = new ArrayList<>();


    @Autowired
    Coin currentCoin;

    @GetMapping("/currentCoins")
    @ResponseBody
    public Coin getCurrentCoins() {

        return currentCoin;
    }

    @GetMapping("/maxValue")
    @ResponseBody
    public int getMaxValue() {

        return maxValueOfAvailableCoins(currentCoin);
    }


    @PostMapping("/loadCoins")
    @ResponseBody
    public Coin loadCoins(@RequestBody Coin newCoins) {

        currentCoin.setPennies(newCoins.getPennies());
        currentCoin.setNickels(newCoins.getNickels());
        currentCoin.setDimes(newCoins.getDimes());
        currentCoin.setQuarters(newCoins.getQuarters());

        return currentCoin;
    }

    @PostMapping("/makeChange")
    @ResponseBody
    public Coin makeChange(@RequestParam(required = false) boolean mostChange, @RequestBody String dollarBill) throws ChangeMakerException {

        availableBills.add(1);
        availableBills.add(2);
        availableBills.add(5);
        availableBills.add(10);
        availableBills.add(20);
        availableBills.add(50);
        availableBills.add(100);

        int bill = Integer.parseInt(dollarBill);
        int billVal = bill * 100;

        if (!availableBills.contains(bill)) {
            throw new ChangeMakerException("Input should be from the available bills: 1,2,5,10,20,50 or 100");
        }
        if (billVal > maxValueOfAvailableCoins(currentCoin)) {
            throw new ChangeMakerException("bill amount is greater than number of coins available to make change");
        }

        Coin changeCoin = new Coin();
        if (mostChange) {
            // make most change
        } else {
            changeCoin = makeChange(billVal);
        }
        return changeCoin;
    }


    public Coin makeChange(int amount) {

        Coin changeCoin = new Coin();

        int numQuarters = 0, numDimes = 0 , numNickels = 0, numPennies = 0;
        if (amount > 25) {
            numQuarters = Math.min(amount/25, currentCoin.getQuarters());
            amount = amount - (numQuarters * quarterValue);

        }
        if (amount > 10) {
            numDimes = Math.min(amount/10, currentCoin.getDimes());
            amount = amount - (numDimes * dimeValue);

        }
        if (amount > 5) {
            numNickels = Math.min(amount/5, currentCoin.getNickels());
            amount = amount - (numNickels * nickelValue);

        }
        if (amount > 1) {
            numPennies = Math.min(amount, currentCoin.getPennies());
            amount = amount - (numPennies * pennyValue);

        }

        changeCoin.setQuarters(numQuarters);
        changeCoin.setDimes(numDimes);
        changeCoin.setNickels(numNickels);
        changeCoin.setPennies(numPennies);

        currentCoin.setQuarters(currentCoin.getQuarters() - numQuarters);
        currentCoin.setDimes(currentCoin.getDimes() - numDimes);
        currentCoin.setNickels(currentCoin.getNickels() - numNickels);
        currentCoin.setPennies(currentCoin.getPennies() - numPennies);


        return changeCoin;
    }


    public int maxValueOfAvailableCoins(Coin coin) {

        return (coin.getQuarters() * quarterValue)
                + (coin.getDimes() * dimeValue)
                + (coin.getNickels() * nickelValue)
                + (coin.getPennies() * pennyValue);
    }


}

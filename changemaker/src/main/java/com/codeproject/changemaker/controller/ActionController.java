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
    final int fiftyCentValue = 50;

    final ArrayList<Integer> availableBills = new ArrayList<>();
    ArrayList<Integer> coinVals = new ArrayList<>();

    public ActionController() {
        this.availableBills.add(1);
        this.availableBills.add(2);
        this.availableBills.add(5);
        this.availableBills.add(10);
        this.availableBills.add(20);
        this.availableBills.add(50);
        this.availableBills.add(100);

        this.coinVals.add(50);
        this.coinVals.add(25);
        this.coinVals.add(10);
        this.coinVals.add(5);
        this.coinVals.add(1);

    }



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
        currentCoin.setFiftyCent(newCoins.getFiftyCent());

        return currentCoin;
    }


    @PostMapping("/makeChange")
    @ResponseBody
    public Coin makeChange(@RequestParam(required = false) boolean mostChange, @RequestBody String dollarBill) throws ChangeMakerException {

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

        int numFiftCent = 0, numQuarters = 0, numDimes = 0 , numNickels = 0, numPennies = 0;


//        coinVals.add(50);
//        coinVals.add(25);
//        coinVals.add(10);
//        coinVals.add(5);
//        coinVals.add(1);

        ArrayList<Integer> maxCoins = new ArrayList<>();
        maxCoins.add(currentCoin.getFiftyCent());
        maxCoins.add(currentCoin.getQuarters());
        maxCoins.add(currentCoin.getDimes());
        maxCoins.add(currentCoin.getNickels());
        maxCoins.add(currentCoin.getPennies());

        ArrayList<Integer> numCoins = new ArrayList<>();

        for (int i = 0; i < coinVals.size(); i ++) {
            int numCoin = 0;
            if (amount > coinVals.get(i)) {
                numCoin = Math.min(amount/coinVals.get(i), maxCoins.get(i));
                amount = amount - (numCoin * coinVals.get(i));
            }
            numCoins.add(numCoin);
        }

////        if (amount > 50) {
////            numFiftCent = Math.min(amount/50, currentCoin.getFiftyCent());
////            amount = amount - (numFiftCent * fiftyCentValue);
////        }
////        if (amount > 25) {
////            numQuarters = Math.min(amount/25, currentCoin.getQuarters());
////            amount = amount - (numQuarters * quarterValue);
////
////        }
////        if (amount > 10) {
////            numDimes = Math.min(amount/10, currentCoin.getDimes());
////            amount = amount - (numDimes * dimeValue);
////
////        }
////        if (amount > 5) {
////            numNickels = Math.min(amount/5, currentCoin.getNickels());
////            amount = amount - (numNickels * nickelValue);
////
////        }
////        if (amount > 1) {
////            numPennies = Math.min(amount, currentCoin.getPennies());
////            amount = amount - (numPennies * pennyValue);
////
////        }
//
        changeCoin.setFiftyCent(numCoins.get(0));
        changeCoin.setQuarters(numCoins.get(1));
        changeCoin.setDimes(numCoins.get(2));
        changeCoin.setNickels(numCoins.get(3));
        changeCoin.setPennies(numCoins.get(4));

        currentCoin.setFiftyCent(currentCoin.getFiftyCent() - numCoins.get(0));
        currentCoin.setQuarters(currentCoin.getQuarters() - numCoins.get(1));
        currentCoin.setDimes(currentCoin.getDimes() - numCoins.get(2));
        currentCoin.setNickels(currentCoin.getNickels() - numCoins.get(3));
        currentCoin.setPennies(currentCoin.getPennies() - numCoins.get(4));


        return changeCoin;
    }


    public int maxValueOfAvailableCoins(Coin coin) {

        return (coin.getFiftyCent() * coinVals.get(0)) +
                (coin.getQuarters() * coinVals.get(1))
                + (coin.getDimes() * coinVals.get(2))
                + (coin.getNickels() * coinVals.get(3))
                + (coin.getPennies() * coinVals.get(4));
    }


}

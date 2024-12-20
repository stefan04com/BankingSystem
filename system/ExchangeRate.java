package org.poo.system;

import org.poo.fileio.ExchangeInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class ExchangeRate {
    private String from;
    private String to;
    private double rate;

    public ExchangeRate(final ExchangeInput exchangeInput) {
        this.from = exchangeInput.getFrom();
        this.to = exchangeInput.getTo();
        this.rate = exchangeInput.getRate();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }

    /**
     * Converts an amount from one currency to another using the given exchange rates.
     *
     * @param exchangeRates List of ExchangeRate objects containing currency conversion rates.
     * @param fromCurrency  The source currency.
     * @param toCurrency    The target currency.
     * @param amount        The amount to convert.
     * @return The converted amount in the target currency.
     */
    public static double convertAmount(final ArrayList<ExchangeRate> exchangeRates,
                                       final String fromCurrency,
                                       final String toCurrency,
                                       final double amount) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (ExchangeRate rate : exchangeRates) {
            graph.putIfAbsent(rate.getFrom(), new HashMap<>());
            graph.putIfAbsent(rate.getTo(), new HashMap<>());

            graph.get(rate.getFrom()).put(rate.getTo(), rate.getRate());
            graph.get(rate.getTo()).put(rate.getFrom(), 1 / rate.getRate());
        }

        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, Double> rateMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(fromCurrency);
        rateMap.put(fromCurrency, 1.0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visited.add(current);

            Map<String, Double> neighbors = graph.getOrDefault(current, new HashMap<>());
            for (Map.Entry<String, Double> neighbor : neighbors.entrySet()) {
                String nextCurrency = neighbor.getKey();
                double exchangeRate = neighbor.getValue();

                if (!visited.contains(nextCurrency)) {
                    rateMap.put(nextCurrency, rateMap.get(current) * exchangeRate);
                    if (nextCurrency.equals(toCurrency)) {
                        return amount * rateMap.get(nextCurrency);
                    }
                    queue.add(nextCurrency);
                }
            }
        }
        return 0;
    }
}


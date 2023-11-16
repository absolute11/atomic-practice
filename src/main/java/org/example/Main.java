package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger beautifulWordsCount3 = new AtomicInteger(0);
    private static AtomicInteger beautifulWordsCount4 = new AtomicInteger(0);
    private static AtomicInteger beautifulWordsCount5 = new AtomicInteger(0);

    public static void main(String[] args) {
        generateAndCheckNicknames();
        printResults();
    }
    private static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
    private static boolean isPalindrome(String text) {
        String reversed = new StringBuilder(text).reverse().toString();
        return text.equals(reversed);
    }

    private static boolean isSameLetterWord(String text) {
        char firstLetter = text.charAt(0);
        for (char letter : text.toCharArray()) {
            if (letter != firstLetter) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAscendingOrderWord(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private static void checkPalindromes(String[] texts) {
        for (String text : texts) {
            if (isPalindrome(text)) {
                int length = text.length();
                if (length == 3) beautifulWordsCount3.incrementAndGet();
                else if (length == 4) beautifulWordsCount4.incrementAndGet();
                else if (length == 5) beautifulWordsCount5.incrementAndGet();
            }
        }
    }

    private static void checkSameLetter(String[] texts) {
        for (String text : texts) {
            if (isSameLetterWord(text)) {
                int length = text.length();
                if (length == 3) beautifulWordsCount3.incrementAndGet();
                else if (length == 4) beautifulWordsCount4.incrementAndGet();
                else if (length == 5) beautifulWordsCount5.incrementAndGet();
            }
        }
    }

    private static void checkAscendingOrderWords(String[] texts) {
        for (String text : texts) {
            if (isAscendingOrderWord(text)) {
                int length = text.length();
                if (length == 3) beautifulWordsCount3.incrementAndGet();
                else if (length == 4) beautifulWordsCount4.incrementAndGet();
                else if (length == 5) beautifulWordsCount5.incrementAndGet();
            }
        }
    }
    private static void generateAndCheckNicknames() {
        Random random = new Random();
        String[] texts = new String[100_000];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> checkPalindromes(texts));
        Thread sameLetterThread = new Thread(() -> checkSameLetter(texts));
        Thread ascendingOrderThread = new Thread(() -> checkAscendingOrderWords(texts));

        palindromeThread.start();
        sameLetterThread.start();
        ascendingOrderThread.start();

        try {
            palindromeThread.join();
            sameLetterThread.join();
            ascendingOrderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printResults() {
        System.out.println("Красивых слов с длиной 3: " + beautifulWordsCount3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + beautifulWordsCount4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + beautifulWordsCount5.get() + " шт");
    }
}

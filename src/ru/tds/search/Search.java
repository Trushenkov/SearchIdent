package ru.tds.search;

import java.util.regex.*;
import java.io.*;

/**
 * Класс для поиска идентификаторов из файла с расширением .java
 *
 * @author Trushenkov Dmitry 15ОИТ18
 */
public class Search {
    public static void main(String[] args) throws IOException {
        String string;
        Pattern p = Pattern.compile(".+");
        Matcher matcher123 = p.matcher("");

        Pattern pattern = Pattern.compile("[A-Za-z_]+\\w*");
        Matcher matcher = pattern.matcher("");

        Pattern pComment = Pattern.compile("\\/\\*\\*.+?\\*\\/");
        Matcher mCOMENT = pComment.matcher("");

        Pattern pCONST = Pattern.compile("\\\".+?\\\"");
        Matcher mCONST = pCONST.matcher("");
/**
 * Для всей программ
 */
        try (
                BufferedReader reader = new BufferedReader(new FileReader("ManyFiles.java"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("Вся программа полностью в одну строку.txt"));
        ) {
            while ((string = reader.readLine()) != null) {
                matcher123.reset(string);

                while (matcher123.find()) {
                    writer.write((matcher123.group()));
                }
            }
        }

        /**
         * Для поиска ковычек
         */
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("Ковычки.txt"));
                BufferedReader programm = new BufferedReader(new FileReader("Вся программа полностью в одну строку.txt"))
        ) {
            while ((string = programm.readLine()) != null) {
                mCONST.reset(string);
                while (mCONST.find()) {
                    writer.write(mCONST.group());
                }
            }
        }
/**
 * Для поиска комментриев
 */
        try (
                BufferedWriter writer2 = new BufferedWriter(new FileWriter("Комментарии.txt"));
                BufferedReader proga = new BufferedReader(new FileReader("Вся программа полностью в одну строку.txt"))
        ) {
            while ((string = proga.readLine()) != null) {
                mCOMENT.reset(string);
                while (mCOMENT.find()) {
                    writer2.write(mCOMENT.group());
                }
            }
        }
        /**
         * Поиск коментариев и ковычек в исходном файле с расширением .java и замена на пустую строку
         */
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("ВСе идентификаторы кроме комментов и ковычек.txt"));
                BufferedReader proga = new BufferedReader(new FileReader("Вся программа полностью в одну строку.txt"))
        ) {
            while ((string = proga.readLine()) != null) {
                mCOMENT.reset(string);
                mCONST.reset(string);
                while (mCOMENT.find() && mCONST.find()) {
                    string = string.replaceAll("\\/\\*\\*.+?\\*\\/", "");
                    string = string.replaceAll("\\\".+?\\\"", "");

                }
                matcher.reset(string);
                while (matcher.find()) {
                    writer.write(matcher.group() + "\n");
                }
            }
        }
    }
}


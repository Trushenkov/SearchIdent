package ru.tds.search;

import java.io.*;
import java.util.regex.*;

/**
 * Класс для поиска идентификаторов из файла ManyFiles.java и их записи в отдельный текстовый файл, а также для чтения файла
 * ManyFiles.java и записи этой программы в отдельный текстоввый файл без комментариев и лишних пробельных символов.
 *
 * @author Trushenkov Dmitry 15ОИТ18
 */
public class Search {
    public static void main(String[] args) throws IOException {
        Pattern p = Pattern.compile(".+");
        Matcher matcherForAllprogramm = p.matcher("");
        Pattern spaces = Pattern.compile("\\s+");
        Matcher matcherForSpaces = spaces.matcher("");
        Pattern pattern = Pattern.compile("[A-Za-z_]+\\w*");
        Matcher matcherForIdentificator = pattern.matcher("");
        Pattern pComment = Pattern.compile("\\/\\*\\*.+?\\*\\/");
        Matcher matcherForComments = pComment.matcher("");
        Pattern comments2 = Pattern.compile("\\/\\*.+?\\*\\/");
        Matcher mcomment2 = comments2.matcher("");
        Pattern comments3 = Pattern.compile("\\/\\/.+?\\s{8}");
        Matcher mcomments3 = comments3.matcher("");
        Pattern pCONST = Pattern.compile("\\\".+?\\\"");
        Matcher mCONST = pCONST.matcher("");

        WriteOneString(matcherForAllprogramm);
        SearchCommentsAndReplace(matcherForComments, matcherForAllprogramm, mcomment2, mcomments3);
        SearchIdentificators(matcherForIdentificator, mCONST);
        SearchAndDeleteExtraSpaces(matcherForSpaces, matcherForAllprogramm);

    }

    /**
     * Метод для записи всей прогрыммы в одну строку, используя для этого шаблон регулярных выражений.
     *
     * @param matcherForAllprogramm шаблон регулярного выражения для всей программы полностью
     * @throws IOException исключение
     */
    public static void WriteOneString(Matcher matcherForAllprogramm) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("ManyFiles2.java"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("AllProgrammOneString.txt"))) {
            String string;
            while ((string = reader.readLine()) != null) {
                matcherForAllprogramm.reset(string);
                while (matcherForAllprogramm.find()) {
                    writer.write((matcherForAllprogramm.group()));
                }
            }
        }
    }

    /**
     * Метод для поиска в файле AllProgrammOneString.txt комментариев, используя регулярные выражения, и замена их на пустую строку.
     * Запись программы без комментариев в файл ProgrammWithoutComments.txt.
     *
     * @param matcherForComments шаблон регулярного выражения для поиска комментариев
     * @param matcherForAllprogramm шаблон регулярного выражения для всей программы
     * @param mcom2 шаблон регулярного выражения для поиска комментариев с одной звездочкой
     * @param mcom3 шаблон регулярнго выражеия для поиска комментариев с двойным слешом
     * @throws IOException исключение
     */
    public static void SearchCommentsAndReplace(Matcher matcherForComments, Matcher matcherForAllprogramm, Matcher mcom2, Matcher mcom3) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProgrammWithoutComments.txt"));
             BufferedReader programm = new BufferedReader(new FileReader("AllProgrammOneString.txt"))) {
            String string;
            while ((string = programm.readLine()) != null) {
                matcherForComments.reset(string);
                while (matcherForComments.find()) {
                    string = string.replaceAll("\\/\\*\\*.+?\\*\\/", " ");
                }
                mcom2.reset(string);
                while (mcom2.find()) {
                    string = string.replaceAll("\\/\\*.+?\\*\\/", " ");
                }
                mcom3.reset(string);
                while (mcom3.find()) {
                    string = string.replaceAll("\\/\\/.+?\\s{8}", " ");
                }
                matcherForAllprogramm.reset(string);
                while (matcherForAllprogramm.find()) {
                    writer.write(matcherForAllprogramm.group());
                }
            }
        }
    }

    /**
     * Метод для поиска идентификаторов в файле, содержащем исходную программу в одну строку,
     * игнорируя идентификаторы в комментариях путем замены комментариев на пустую строку.
     *
     * @param matcherForIdentificator шаблон регулярного выражения для поиска идентификаторов
     * @throws IOException исключение
     */
    public static void SearchIdentificators(Matcher matcherForIdentificator, Matcher mCONST) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("AllIdentificatorsWithoutCommentsAndQuotes.txt"));
             BufferedReader prorammToString = new BufferedReader(new FileReader("ProgrammWithoutComments.txt"))) {
            String string;
            while ((string = prorammToString.readLine()) != null) {
                mCONST.reset(string);
                while (mCONST.find()) {
                    string = string.replaceAll("\\\".+?\\\"", " ");
                }
                matcherForIdentificator.reset(string);
                while (matcherForIdentificator.find()) {
                    writer.write(matcherForIdentificator.group() + "\n");
                }
            }
        }

    }

    /**
     * Метод для поиска в файле ProgrammWithoutComments.txt, содержащем программу без комментариев
     * производится поиск и удаление лишних пробельных символов, а также записи в файл
     * ProgrammWithoutCommentsAndExtraSpaces.txt программы без лишних пробельных символов.
     *
     * @param matcherForSpaces      шаблон регулярного выражения для поиска пробелов
     * @param matcherForAllprogramm шаблон регулярного выражения для всей программы
     * @throws IOException исключение
     */
    public static void SearchAndDeleteExtraSpaces(Matcher matcherForSpaces, Matcher matcherForAllprogramm) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("ProgrammWithoutCommentsAndExtraSpaces.java")));
             BufferedReader programm = new BufferedReader(new FileReader("ProgrammWithoutComments.txt"))) {
            String string;
            while ((string = programm.readLine()) != null) {
                matcherForSpaces.reset(string);
                while (matcherForSpaces.find()) {
                    string = string.replaceAll("ManyFiles2", "ProgrammWithoutCommentsAndExtraSpaces");
                    string = string.replaceAll("\\s+", " ");
                }
                matcherForAllprogramm.reset(string);
                while (matcherForAllprogramm.find()) {
                    writer.write(matcherForAllprogramm.group());
                }
            }
        }
    }

}



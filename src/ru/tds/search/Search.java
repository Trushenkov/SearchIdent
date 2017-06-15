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
        String string;
        Pattern p = Pattern.compile(".+");
        Matcher matcherForAllprogramm = p.matcher("");

        Pattern spaces = Pattern.compile("\\s{4}|\\s{8}");
        Matcher matcherForSpaces = spaces.matcher("");

        Pattern pattern = Pattern.compile("[A-Za-z_]+\\w*");
        Matcher matcherForIdentificator = pattern.matcher("");

        Pattern pComment = Pattern.compile("\\/\\*\\*.+?\\*\\/");
        Matcher matcherForComments = pComment.matcher("");

        /**
         * Запись всей прогрыммы в одну строку, используя для этого шаблон регулярных выражений.
         */
        try (
                BufferedReader reader = new BufferedReader(new FileReader("ManyFiles2.java"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("AllProgrammOneString.txt"))
        ) {
            while ((string = reader.readLine()) != null) {
                matcherForAllprogramm.reset(string);

                while (matcherForAllprogramm.find()) {
                    writer.write((matcherForAllprogramm.group()));
                }
            }
        }

        /**
         * Поиск в файле AllProgrammOneString.txt комментариев, используя регулярные выражения,
         * и замена их на пустую строку. Запись программы без комментариев в файл ProgrammWithoutComments.txt.
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ProgrammWithoutComments.txt"));
             BufferedReader programm = new BufferedReader(new FileReader("AllProgrammOneString.txt"))) {
            while ((string = programm.readLine()) != null) {
                matcherForComments.reset(string);
                while (matcherForComments.find()) {
                    string = string.replaceAll("\\/\\*\\*.+?\\*\\/", " ");
                }
                matcherForAllprogramm.reset(string);
                while (matcherForAllprogramm.find()) {
                    writer.write(matcherForAllprogramm.group());
                }
            }
        }
        /**
         * Поиск идентификаторов в файле, содержащем исходную программу в одну строку,
         * игнорируя идентификаторы в комментариях путем замены комментариев на пустую строку.
         */
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("AllIdentificatorsWithoutComments.txt"));
                BufferedReader prorammToString = new BufferedReader(new FileReader("AllProgrammOneString.txt"))
        ) {
            while ((string = prorammToString.readLine()) != null) {
                matcherForComments.reset(string);
                while (matcherForComments.find()) {
                    string = string.replaceAll("\\/\\*\\*.+?\\*\\/", "");
                }
                matcherForIdentificator.reset(string);
                while (matcherForIdentificator.find()) {
                    writer.write(matcherForIdentificator.group() + "\n");
                }
            }
        }
        /**
         * В файле ProgrammWithoutComments.txt, содержащем программу без
         * комментариев производится поиск и удаление лишних пробельных символов.
         * Запись в файл ProgrammWithoutCommentsAndExtraSpaces.txt программы без лишних пробельных символов.
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("ProgrammWithoutCommentsAndExtraSpaces.java")));
             BufferedReader programm = new BufferedReader(new FileReader("ProgrammWithoutComments.txt"))) {
            while ((string = programm.readLine()) != null) {
                matcherForSpaces.reset(string);
                while (matcherForSpaces.find()) {
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



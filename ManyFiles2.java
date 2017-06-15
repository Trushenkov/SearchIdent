package ru.tds.manyfiles;

import java.io.*;

/**
 * Класс для формирования файла int6data.dat, содержащего только шестизначные целые числа, на основе исходного файла с целыми числами.
 *
 * @author Trushenkov Dmitry 15ОИТ18.
 */
public class ManyFiles2 {

    public static void main(String[] args) throws IOException {
        DataOutputStream writer = new DataOutputStream(new FileOutputStream(new File("int6data.dat")));
        DataInputStream reader = new DataInputStream(new FileInputStream(new File("intdata.dat")));
        while (reader.available() > 0) {
            int number = reader.readInt();
            if (isCheck(number)) {
                writer.writeInt(number);
            }
        }
        reader.close();
        writer.close();
    }

    /**
     * Метод для проверки и выбора из исходного файла целых чисел только четырех, пяти и шестихначных чисел.
     *
     * @param number число
     * @return четырехзначное, пятизначное или шестизначное число
     */
    private static boolean isCheck(int number) {
        return number < 1000000 && number > 999;
    }
}
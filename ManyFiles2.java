package ru.tds.manyfiles;

/**
 * Класс, взятый из программы "Много файлов"
 *
 * @author Trushenkov Dmitry 15OIT18
 */

public class ManyFiles2 {

    /*
     * Комментарий .......
     */
    public static void main(String[] args) throws IOException {
        DataOutputStream writer = new DataOutputStream(new FileOutputStream(new File("int6data.dat"))); //объявление writera
        DataInputStream reader = new DataInputStream(new FileInputStream(new File("intdata.dat"))); // объявление readera

    /*
     Еще один комментарий
     */
        while (reader.available() > 0) {
            int number = reader.readInt();
            if (isCheck(number)) {
                writer.writeInt(number);
            }
        }
        reader.close();
        writer.close();
    }

    private static boolean isCheck(int number) {
        return number < 1000000 && number > 999;
    }
}
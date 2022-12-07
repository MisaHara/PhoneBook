package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Phonebook {
    private static final HashMap<String, String> cont = new HashMap<>();

    //savePB - Сохраняет все контакты в файл
    private static void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\General\\Phone.txt"));
        for(Map.Entry<String,String> k: cont.entrySet()){
            writer.write(k.getKey() + " " + k.getValue()+System.lineSeparator());
        }
        writer.close();
        System.out.println("Контакт успешно изменен\n");
    }

    //addPB - Добавить контакт
    private static void addPB(String phone, String name) throws IOException {
        cont.put(phone, name);
        save();

    }

    //delPB - Удалить запись по номеру телефона
    private static void delPB(String phone) throws IOException {
        cont.remove(phone);
        save();
    }
    static final String Optional =
            """
                    \n
                    Выбор действия:
                     (add) добавить данные,
                     (del) удалить данные,
                     (num) найти номера по фамилии,
                     (sur) найти фамилию,
                     (list)список всех контактов,
                     (exit)выход\s
            """;

    //loadPB - загружает БД из текстового файла Phone.txt
    //производит проверку на наличие файла
    public static void loadPB() throws IOException {
        File file = new File("C:\\General\\Phone.txt");
        if (file.exists()){
            BufferedReader reader = new BufferedReader(new FileReader("C:\\General\\Phone.txt"));
            String act;
            while ((act=reader.readLine())!=null) {
                String[] cP = act.split(" ");
                cont.put(cP[0], cP[1]);
            }
            reader.close();
        }
    }

    //PrintPhonebook - выводит на экран все записи БД
    public static void PrintPhonebook(){
        System.out.println("Телефонный справочник: ");
        for(Map.Entry<String,String> k: cont.entrySet()){
            System.out.println(k.getValue()+": "+ k.getKey());
        }
    }

    //FindSurname - производит поиск фамилии по номеру телефона заданному в качестве аргумента
    //возвращает строку
    public static String FindSurname(String number){
        String result = cont.get(number);
        if (result == null) return "Абонент с таким номером не найден";
        return result;
    }

    //FindNumberPhone - производит поиск списка номеров по фамилии заданной в качестве аргумента
    //возвращает массив строк
    public static String[] FindNumberPhone(String surname){
        List<String> result = new ArrayList<>();
        for (Map.Entry entry : cont.entrySet()) {
            if (surname.equalsIgnoreCase((String)entry.getValue())){
                result.add((String)entry.getKey());
            }
        }
        if (result.size() == 0) result.add("Абонент с такой фамилией не найден");
        return result.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException {
        String calledAction;

        loadPB();
        PrintPhonebook();
        System.out.println(Optional);

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        calledAction = bf.readLine();
        while(!calledAction.equals("exit")){
            if(calledAction.equals("add")){
                System.out.println("Введите фамилию:");
                String name = bf.readLine();
                System.out.println("Введите телефон:");
                String phone = bf.readLine();
                addPB(phone, name);
            }else{
                if(calledAction.equals("del")){
                    System.out.println("Введите телефон:");
                    String phone = bf.readLine();
                    delPB(phone);
                } else{
                    if (calledAction.equals("num")){
                        System.out.println("Введите фамилию:");
                        String surname = bf.readLine();
                        String[] numbers = FindNumberPhone(surname);
                        for (String number : numbers) {
                            System.out.println(number);
                        }
                    } else {
                        if (calledAction.equals("sur")) {
                            System.out.println("Введите номер:");
                            String number = bf.readLine();
                            System.out.println(FindSurname(number));
                        } else {
                            if(calledAction.equals("save")){
                                save();
                            } else {
                                if (calledAction.equals("list")) {
                                    PrintPhonebook();
                                }
                            }
                        }
                    }
                }
            }
            //Запрос на следующее действие
            System.out.println(Optional);
            calledAction=bf.readLine();
        }
    }
}
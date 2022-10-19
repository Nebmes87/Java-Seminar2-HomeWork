    /*
    Дана строка sql-запроса "select * from students where ".
    Сформируйте часть WHERE этого запроса, используя StringBuilder.
    Данные для фильтрации приведены ниже в виде json строки.
    Если значение null, то параметр не должен попадать в запрос.
    Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"}
Ответ: "select * from students where name = 'Ivanov' and country = 'Russia' and city = 'Moscow'" для приведенного примера
     */
public class Task1 {

    public StringBuilder stringRestruct(StringBuilder txt) {

        int count = 1;
        for (int i = 0; i < txt.length(); i++) {
            if (txt.charAt(i) == ',') {
                count++;
            }
        }

        StringBuilder[] arrKey = new StringBuilder[count];
        StringBuilder[] arrVal = new StringBuilder[count];
        for (int i = 0; i < arrKey.length; i++) {
            arrKey[i] = new StringBuilder("");
            arrVal[i] = new StringBuilder("");
        }

        int a = 0;

        for (int i = 0; i < txt.length(); i++) {

            arrKey[a].append(txt.charAt(i));
            arrVal[a].append(txt.charAt(i));
            if (txt.charAt(i) == ',') {
                txt.deleteCharAt(i);
                a++;
                continue;
            }

        }
        arrKey[arrKey.length - 1].append(',');
        arrVal[arrVal.length - 1].append(',');
        for (int i = 0; i < arrKey.length; i++) {
            arrKey[i].replace(arrKey[i].indexOf(":"), arrKey[i].lastIndexOf(",") + 1, "");
            arrKey[i].deleteCharAt(arrKey[i].indexOf("\""));
            arrKey[i].deleteCharAt(arrKey[i].lastIndexOf("\""));
            arrVal[i].replace(arrVal[i].indexOf("\""), arrVal[i].lastIndexOf(":") + 2, "'");
            arrVal[i].replace(arrVal[i].lastIndexOf("\""), arrVal[i].lastIndexOf(",") + 1, "'");
        }

        StringBuilder newTxt = new StringBuilder("");
        String check = "'null'";
        for (int i = 0; i < arrKey.length; i++) {
            String temp = String.valueOf(arrVal[i]);
            if (temp.equals(check)) {
                continue;
            }
            newTxt.append(arrKey[i]).append(" = ").append(arrVal[i].append(" and "));
        }
        newTxt.replace(newTxt.lastIndexOf(" and "), newTxt.lastIndexOf(" "), "");

        return newTxt;
    }

    public static void main(String[] args) {
        StringBuilder sql = new StringBuilder("select * from students where ");
        StringBuilder jsonString = new StringBuilder("\"name\":\"Ivanov\", \"country\":\"Russia\", \"city\":\"Moscow\", \"age\":\"null\"");
        Task2 s = new Task2();
        System.out.println(sql.append(s.stringRestruct(jsonString)));
    }
    /*
[{"фамилия":"Иванов","оценка":"5","предмет":"Математика"},{"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},{"фамилия":"Краснов","оценка":"5","предмет":"Физика"}]
Написать метод(ы), который распарсит json и, используя StringBuilder, создаст строки вида: Студент [фамилия] получил [оценка] по предмету [предмет].
Пример вывода:
Студент Иванов получил 5 по предмету Математика.
Студент Петрова получил 4 по предмету Информатика.
Студент Краснов получил 5 по предмету Физика.
     */
    public StringBuilder[] parseString(StringBuilder txt) {

        int count = 1;
        String temp1 = "фамилия";
        String temp2 = "оценка";
        String temp3 = "предмет";

        for (int i = 0; i < txt.length(); i++) {
            if (txt.charAt(i) == '"') {
                txt.deleteCharAt(i);
            }
            if (txt.charAt(i) == ':') {
                txt.replace(i, i + 1, " ");
            }
            if (i < txt.length() - temp1.length()) {
                String check1 = String.valueOf(txt.substring(i, i + temp1.length()));
                if (check1.equals(temp1)) {
                    txt.replace(i, i + temp1.length(), "Студент");
                }
            }
            if (i < txt.length() - temp2.length()) {
                String check2 = String.valueOf(txt.substring(i, i + temp2.length()));
                if (check2.equals(temp2)) {
                    txt.replace(i, i + temp2.length(), " получил");
                }
            }
            if (i < txt.length() - temp3.length()) {
                String check3 = String.valueOf(txt.substring(i, i + temp3.length()));
                if (check3.equals(temp3)) {
                    txt.replace(i, i + temp3.length(), " по Предмету");
                }
            }
            if (txt.charAt(i) == ',' && txt.charAt(i + 1) == '{') {
                count++;
            }
        }

        StringBuilder[] str = new StringBuilder[count];

        for (int i = 0; i < str.length; i++) {
            str[i] = new StringBuilder("");
        }

        int a = 0;
        for (int i = 0; i < txt.length(); i++) {
            str[a].append(txt.charAt(i));
            if (txt.charAt(i) == ',' && txt.charAt(i - 1) == '}') {
//                str[a].deleteCharAt(str[a].lastIndexOf(""));
                str[a].deleteCharAt(str[a].lastIndexOf(","));
                a++;
                continue;
            }
        }

        for (int i = 0; i < str.length; i++) {

            while (str[i].indexOf(",") != -1) {
                str[i].deleteCharAt(str[i].indexOf(","));
            }
            str[i].deleteCharAt(str[i].indexOf("{"));
            str[i].deleteCharAt(str[i].indexOf("}"));
        }
        return str;
    }

    public static void mains(String[] args) {
        StringBuilder jsonString = new StringBuilder("{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"},{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"},{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}");
        Task3 t = new Task3();
        StringBuilder[] arr = t.parseString(jsonString);
        for (StringBuilder item : arr) {
            System.out.println(item);
        }

    }    
}

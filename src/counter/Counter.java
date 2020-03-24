package counter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Counter {
    int chars = 0;
    int words = 0;
    int lines = 0;
    int commentLines = 0;
    int codeLines = 0;
    int blankLines = 0;
    String path = null;

    public Counter(String filePath) throws IOException {
        //temp储存遍历到的当前字符，wordFlag用于判断统计词数
        int temp, wordFlag = -1;
        path = filePath;
        FileReader fileReader = new FileReader(path);
        //按字符遍历文件
        while ((temp = fileReader.read()) != -1){
            //统计字符数
            chars++;
            //统计词数，当遇到字母后遇到非字母时，词数+1
            if ((temp >= 'a' && temp <= 'z') || (temp >= 'A' && temp <= 'Z')) wordFlag = temp;
            else if (temp != wordFlag && wordFlag != -1){
                words++;
                wordFlag = -1;
            }
            //统计行数，当遇到换行符后，行数+1
            if (temp == '\n') lines++;
        }

        //统计注释行、空行和代码行
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        int cflag = 0, starComments = 0;
        String lineTemp ;
        while ((lineTemp = br.readLine()) != null){
//            System.out.println(lineTemp);
            //统计空白行
            Pattern pattern = Pattern.compile("\\S");       //不能含有任何非空白字符
            Matcher matcher = pattern.matcher(lineTemp);
            if (!matcher.find()) blankLines++;
            //统计注释行
            Pattern pattern0 = Pattern.compile("//");       //单行注释，注释行数+1
            Matcher matcher0 = pattern0.matcher(lineTemp);
            if (matcher0.find()) commentLines++;

            Pattern pattern1 = Pattern.compile("/\\*");     //多行注释，直到遇到*/前，注释行数+1
            Pattern pattern2 = Pattern.compile("\\*/");
            Matcher matcher1 = pattern1.matcher(lineTemp);
            Matcher matcher2 = pattern2.matcher(lineTemp);
            if (matcher1.find()) cflag = 1;
            if (cflag == 1) {
                commentLines++;
                starComments++;
            }
            if (matcher2.find()) cflag = 0;

            //统计代码行
            pattern1 = Pattern.compile("\\S{2,}");  //非空白字符在两个或两个以上
            pattern2 = Pattern.compile("^\\s*//");  //不能以任意数量的空白字符接“//”开头
            matcher1 = pattern1.matcher(lineTemp);
            matcher2 = pattern2.matcher(lineTemp);
            if (matcher1.find() && !matcher2.find()) codeLines++;
        }
        codeLines -= starComments;                  //排除跨行注释的干扰
    }

    public void printChars(){
        System.out.println("该文件的字符数为：" + chars);
    }

    public void printWords() {
        System.out.println("该文件的词数为：" + words);
    }

    public void printLines() {
        System.out.println("该文件的行数为：" + lines);
    }

    public void printCommentLines() {
        System.out.println("该文件的注释行数为：" + commentLines);
    }

    public void printCodeLines() {
        System.out.println("该文件的代码行数为：" + codeLines);
    }

    public void printBlankLines() {
        System.out.println("该文件的空白行数为：" + blankLines);
    }
}

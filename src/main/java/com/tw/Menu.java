package com.tw;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Menu {

    private List<Student> studentList = new ArrayList<>();

    public void printDescription() {
        System.out.println("```\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n" +
                "```");
    }

    private void printMenuInfo(String info) {
        System.out.println(",,,\n" + info + ",,,\n");
    }

    public void executeSelection(int selection) {
        switch (selection) {
            case 1:
                executeMenuOne();
                break;
            case 2:
                executeMenuTwo();
                break;
            case 3:
                printMenuInfo("退出系统!\n");
                break;
            default:
                printMenuInfo("请输入正确的选项(1,2,3):\n");
                break;

        }
    }

    private void executeMenuTwo() {
        printMenuInfo("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：\n");

        while (true) {
            String inputStdId = new Scanner(System.in).next();
            if (checkInputStdId(inputStdId)) {
                printInputStdId(inputStdId);
                break;
            } else {
                printMenuInfo("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：\n");
            }
        }
    }

    private void executeMenuOne() {
        printMenuInfo("请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：\n");

        while (true) {
            String inputStudentInfo = new Scanner(System.in).next();
            if (checkInputStdInfo(inputStudentInfo)) {
                addStudent(inputStudentInfo);
                break;
            } else {
                printMenuInfo("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：\n");
            }
        }
    }

    private boolean checkInputStdInfo(String inputStudentInfo) {
        String regex = "^([\\u4e00-\\u9fa5]+|\\w+),\\d+,(([\\u4e00-\\u9fa5]+|\\w+):\\d{1,3},)+$";
        return Pattern.matches(regex, inputStudentInfo + ",");//此处最后加上一个逗号是因为正则表达式(([\u4e00-\u9fa5]+|\w+):\d{1,3},)+这部分匹配多次最后会多一个逗号，故添加一个逗号以正确匹配
    }

    private void addStudent(String inputStudentInfo) {
        String[] divideByComma = inputStudentInfo.split(",");
        String stdName = divideByComma[0];
        String stdId = divideByComma[1];

        Map<String, Double> classAndScore = new HashMap<>();
        for (int i = 2; i < divideByComma.length; i++) {
            String[] divideByColon = divideByComma[i].split(":");
            classAndScore.put(divideByColon[0], Double.valueOf(divideByColon[1]));//0位是学科名，1位是对应科成绩
        }

        studentList.add(new Student(classAndScore, stdName, stdId));
    }

    private boolean checkInputStdId(String inputStdId) {
        String regex = "^(\\d+,)+$";
        return Pattern.matches(regex, inputStdId + ",");//此处最后加上一个逗号是因为正则表达式(\d+,)+这部分匹配多次最后会多一个逗号，故添加一个逗号以正确匹配
    }

    private void printInputStdId(String inputStdId) {
        if (studentList.isEmpty()) {
            System.out.println("还没有学生信息!!请添加后查询!!");
            return;
        }

        //打印姓名-学科-平均分-总分一栏
        System.out.println(",,,\n" +
                "成绩单\n");
        Map<String, Double> classAndScore = studentList.get(0).getClassAndScore();
        StringBuilder title = new StringBuilder();
        title.append("姓名").append("|");
        for (String key : classAndScore.keySet()) {
            title.append(key).append("|");
        }
        System.out.println(title.append("平均分").append("|").append("总分").append("|").toString());

        //打印所选学生详细成绩信息
        System.out.println("========================");
        String[] selectedStdIdArr = inputStdId.split(",");
        printSelectedStdDetail(selectedStdIdArr);
        System.out.println("========================");

        //打印全班总分信息
        System.out.println("全班总分平均数:" + studentList.stream().mapToDouble(Student::calculateTotalScore).average().getAsDouble());
        System.out.println("全班总分中位数:" + calculateMedian());
        System.out.println(",,,\n");
    }

    private void printSelectedStdDetail(String[] selectedStdIdArr) {
        boolean isExist = false;
        for (String selectedStdId : selectedStdIdArr) {
            for (Student std : studentList) {
                if (std.getStdId().equals(selectedStdId)) {
                    System.out.println(std.toString());
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                System.out.println("学号" + selectedStdId + "未找到成绩信息!!");
            }
            isExist = false;
        }
    }

    private double calculateMedian() {
        List<Double> scoreListSorted = studentList.stream()
                .mapToDouble(Student::calculateTotalScore)
                .sorted()
                .boxed()
                .collect(Collectors.toList());

        if (scoreListSorted.size() % 2 == 0) {
            int medianPosition = scoreListSorted.size() / 2;
            return (scoreListSorted.get(medianPosition) + scoreListSorted.get(medianPosition - 1)) / 2;
        } else {
            int medianPosition = scoreListSorted.size() / 2;
            return scoreListSorted.get(medianPosition);
        }

    }


}

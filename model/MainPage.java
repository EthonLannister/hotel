package edu.nju.hostelworld.model;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainPage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        String userType = "";

        while (!loggedIn) {
            System.out.println("请选择您的身份（1-用户，2-管理员）：");
            userType = scanner.nextLine();

            if (userType.equals("1") || userType.equals("2")) {
                loggedIn = true;
            } else {
                System.out.println("无效的选择！");
            }
        }

        while (true) {
            System.out.println("请输入您的ID：");
            String userId = scanner.nextLine();
            System.out.println("请输入您的密码：");
            String password = scanner.nextLine();

            // 读取用户信息文件进行验证
            String userCredentialsFile = (userType.equals("1")) ? "user_credentials.txt" : "admin_credentials.txt";
            boolean userFound = false;

            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(userCredentialsFile));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(" ");
                    String id = data[0];
                    String userPassword = data[1];

                    if (userId.equals(id) && password.equals(userPassword)) {
                        userFound = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (userFound) {
                System.out.println("欢迎，" + ((userType.equals("1")) ? "用户" : "管理员") + " " + userId + "！");
                if (userType.equals("1")) {
                    showUserMenu(scanner);
                } else {
                    showAdminMenu(scanner);
                }
                break; // 登录成功，跳出循环
            } else {
                System.out.println("无效的ID或密码，请重试。");
            }
        }
    }

    public static void showUserMenu(Scanner scanner) {
        while (true) {
            System.out.println("请选择操作：");
            System.out.println("1. 酒店房间");
            System.out.println("2. 个人信息");
            System.out.println("3. 退出");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("进入酒店房间页面。");
                    break;
                case 2:
                    System.out.println("进入个人信息页面。");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("输入无效，请重新选择。");
            }
        }
    }

    public static void showAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("请选择操作：");
            System.out.println("1. 酒店房间");
            System.out.println("2. 个人信息");
            System.out.println("3. 用户信息");
            System.out.println("4. 退出");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("进入酒店房间页面。");
                    break;
                case 2:
                    System.out.println("进入个人信息页面。");
                    break;
                case 3:
                    System.out.println("进入用户信息页面。");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("输入无效，请重新选择。");
            }
        }
    }
}

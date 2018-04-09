
import java.util.Scanner;

public class MD4 {

    public static boolean safeCheck(int[] maxForEachProc, int reserve, int[] givenResources, int processAmount) {
        int item[] = new int[processAmount];
        int work = reserve;
        int finished = 0;
        boolean endWhile = true;
        for (int i = 0; i < processAmount; i++) {
            if (givenResources[i] == -1) {
                finished++;
                item[i] = -1;
            } else {
                item[i] = maxForEachProc[i] - givenResources[i];
            }
        }
        while (endWhile && finished != processAmount) {
            endWhile = false;
            for (int i = 0; i < item.length; i++) {
                if (item[i] <= work && item[i] != -1) {
                    work += givenResources[i];
                    item[i] = -1;
                    endWhile = true;
                    finished++;
                }
            }
        }
        return finished == processAmount;
    }

    public static int checkReserve(int[] givenResources) {
        int value = 0;
        for (int i = 0; i < givenResources.length; i++) {
            value += givenResources[i];
        }
        return value;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int hivs = 0, processAmount = 0;//сколько дали изначально на систему, сколько процессов
        int systemLoad = 0;// загруженность системы
        System.out.println("Enter amount of system resources :");
        if (sc.hasNextInt()) {
            hivs = sc.nextInt();
        } else {
            System.out.println("Wrong value! ");
        }
        System.out.println("Enter amount of processes :");
        if (sc.hasNextInt()) {
            processAmount = sc.nextInt();
        } else {
            System.out.println("Wrong value! ");
        }
        System.out.println("Enter amount of each max process values :");
        int[] maxForEachProc = new int[processAmount];// максимум для завершения процесса
        if (sc.hasNextInt()) {
            for (int i = 0; i < maxForEachProc.length; i++) {
                maxForEachProc[i] = sc.nextInt();
                systemLoad += maxForEachProc[i];// считаем сумму нагрузки системы изначально выделенных ресурсов
            }
        } else {
            System.out.println("Wrong value! ");
        }
        int[] givenResources = new int[processAmount];//сколько дали ресурсов на каждый процесс
        int reserve = hivs - checkReserve(givenResources);//сколько ресов осталось для манипуляции.
        sc.nextLine();
        while (true) {
            System.out.println();
            System.out.println("Commands :\n Request <Process> <Resource>\n Free <Process> <Resource>\n End");

            String command = sc.nextLine();
            String[] s = command.split("\\s");// взяли массив, разбили его по пробелам и работаем с массивом string, цифры нужно Integer.parseInt()
            if (s.length <= 3) {
                if (s[0].equals("End")) {
                    System.out.println("Pjos Ahuel");
                    return;
                }
                final int pars1 = Integer.parseInt(s[1]);
                final int pars2 = Integer.parseInt(s[2]);
                if (s[0].equals("Request")) {
                    if (pars1 <= processAmount && pars2 <= reserve) {
                        if (pars2 + givenResources[pars1] <= maxForEachProc[pars1]) {
                            givenResources[pars1] += pars2;
                            reserve -= pars2;
                            System.out.println();
                        } else {
                            System.out.println("Wrong value! ");
                        }
                    }
                    if (!safeCheck(maxForEachProc, reserve, givenResources, processAmount)) {
                        System.out.println("Всади это число себе в задницу, оно не катит. Ансейф, епта!");
                        givenResources[pars1] -= pars2;
                        reserve += pars2;

                    } else if (givenResources[pars1] == maxForEachProc[pars1]) {
                        reserve += givenResources[pars1];
                        givenResources[pars1] = -1;
                        System.out.println("Ну ладно, в этот раз ты посадил меня на сейвовую бутылку!");
                    }

                    System.out.println("Понеслась : " + reserve + "< " + hivs + " >");
                    System.out.println("Processes : ");
                    for (int i = 0; i < processAmount; i++) {
                        System.out.print(givenResources[i] + "< " + maxForEachProc[i] + " > ; ");//выписываю все процессы и их максимум
                    }
                }
                if (s[0].equals("Free")) {
                    if (pars1 <= processAmount && givenResources[pars1] - pars2 >= 0) {
                        givenResources[pars1] -= pars2;
                        reserve += pars2;
                        System.out.println("System : " + reserve + "< " + hivs + " >");
                        System.out.println("Processes : ");
                        for (int i = 0; i < processAmount; i++) {
                            System.out.print(givenResources[i] + "< " + maxForEachProc[i] + " > ; ");//выписываю все процессы и их максимумы
                        }
                        System.out.println();
                    } else {
                        System.out.println("Wrong value! ");//2441
                    }
                }
            } else {
                System.out.println("Wrong value!");
            }
        }
    }//main end
}//class end

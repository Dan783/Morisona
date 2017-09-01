import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Dan on 12.05.2017.
 */
public class Morison {
    private ArrayList<ArrayList<Long>> secondaryTable = new ArrayList<>();
    private ArrayList<Long> list1 = new ArrayList<>();
    private ArrayList<Long> list2 = new ArrayList<>();
    private ArrayList<Long> list3 = new ArrayList<>();
    private ArrayList<Long> list4 = new ArrayList<>();
    private long lastU;
    private long D;
    private long index = 0;

    public void setD(int d) {
        this.D = d;
    }

    private void begining() {
        list1.add((long) -1);
        list2.add((long) 0);
        list3.add((long) 1);
        list4.add((long) 1);
    }

    private void showMainTable() {
        System.out.print("        ");
        for (int i = 0; i < list1.size(); i++) {
            System.out.printf("%-7s ", list1.get(i));
        }
        System.out.println();
        for (int i = 0; i < list1.size(); i++) {
            System.out.print("---------");
        }
        System.out.println();
        System.out.printf("%-7s ", "a ");
        for (int i = 0; i < list2.size(); i++) {
            System.out.printf("%-7s ", list2.get(i));
        }
        System.out.println();
        System.out.printf("%-7s ", "p ");
        for (int i = 0; i < list3.size(); i++) {
            System.out.printf("%-7s ", list3.get(i));
        }
        System.out.println();
        System.out.printf("%-7s ", "p^2 ");
        for (int i = 0; i < list4.size(); i++) {
            System.out.printf("%-7s ", list4.get(i));
        }
        System.out.println();
    }

    private void bufFirstIndex() {
        long V1 = 1;
        long alpha1 = (int) Math.sqrt(this.D);
        long a1 = alpha1;
        long U1 = a1 * V1;
        long p1 = list3.get(0) * a1;
        long p1Square = (long) Math.pow(p1, 2);
        if (p1Square > this.D - 300) {
            p1Square = p1Square - D;
        }
        listsAdd(this.index, a1, p1, p1Square);
        this.lastU = U1;
        this.index++;

    }

    private void count() {
        long bufV = Math.abs(list4.get(list4.size() - 1));
        System.out.println(" V "+bufV);
        long bufa = (long) (Math.sqrt(this.D) + this.lastU) / bufV;
        System.out.println("alpha "+(Math.sqrt(this.D) + this.lastU) / bufV);
        System.out.println(" a "+bufa);
        long bufU = bufa * bufV - this.lastU;
        System.out.println(" U "+bufU);
        long bufp;
        if (list3.size() >= 2) {
            bufp = bufa * list3.get(list3.size() - 1) + list3.get(list3.size() - 2);
        } else {
            bufp = bufa * list3.get(list3.size() - 1);
        }
        long bufp1Square = (long) (Math.pow(bufp, 2) % this.D);
        if (bufp1Square > this.D - 500) {
            bufp1Square = bufp1Square - D;
        }
        listsAdd(this.index, bufa, bufp, bufp1Square);
        this.lastU = bufU;
        index++;
    }

    private void listsAdd(long one, long two, long three, long four) {
        list1.add(one);
        list2.add(two);
        list3.add(three);
        list4.add(four);
    }

    public ArrayList<Long> divideNumber(long number) {
        ArrayList<Long> bufList1 = new ArrayList<>();
        if (number < 0) {
            bufList1.add((long) 1);
        } else {
            bufList1.add((long) 0);
        }
        long buf1 = Math.abs(number);
        long index = 2;
        while (true) {
            if (index > Math.abs(number)) {
                break;
            }
            if (buf1 % index == 0) {
                bufList1.add(index);
                buf1 = buf1 / index;
                index = 1;
            }
            index++;
        }
        return bufList1;
    }

    private void checkToDoNext() throws IOException {
        ArrayList<Long> listOfEndNumber = new ArrayList<>();
        ArrayList<Long> buf;
        ArrayList<Long> bufNumbers = new ArrayList<>();
        long max = Math.abs(list4.get(0));
        for (int i = 1; i < list4.size(); i++) {
            if (Math.abs(list4.get(i)) > max) {
                max = Math.abs(list4.get(i));
            }
        }
        PrimeTable table = new PrimeTable();
        buf = table.primeRow(max);
        listOfEndNumber.add((long) -1);
        for (int i = 0; i < buf.size(); i++) {
            listOfEndNumber.add(buf.get(i));
        }

        for (int i = 0; i < listOfEndNumber.size(); i++) {
            System.out.printf("%-3s ", listOfEndNumber.get(i));
        }
        System.out.println();
        for (int i = 0; i < listOfEndNumber.size(); i++) {
            System.out.print("----");
        }
        System.out.println();
        for (int i = 1; i < list4.size(); i++) {
            buf = new ArrayList<>();
            bufNumbers = new ArrayList<>();
            long amount;
            buf = divideNumber(list4.get(i));
            //System.out.println(buf);
            bufNumbers.add(buf.get(0));
            for (int j = 1; j < listOfEndNumber.size(); j++) {
                amount = 0;
                for (int k = 1; k < buf.size(); k++) {
                    if (Math.abs(listOfEndNumber.get(j)) == Math.abs(buf.get(k))) {
                        amount++;
                    }
                }
                if (amount > 0) {
                    bufNumbers.add(amount);
                } else {
                    bufNumbers.add((long) 0);
                }
            }

            for (int j = 0; j < bufNumbers.size(); j++) {
                System.out.printf("%-3s ", bufNumbers.get(j));
            }
            secondaryTable.add(bufNumbers);
            System.out.println("  << " + list4.get(i) + "  << " + list3.get(i) + " index " + i);
        }
        ArrayList<Long> buf2;
        ArrayList<Long> buf3;
        boolean goNext = true;
        for (int i = 0; i < secondaryTable.size(); i++) {
            for (int j = 0; j < secondaryTable.size(); j++) {
                if (i == j) {
                    j++;
                }
                buf2 = secondaryTable.get(i);
                buf3 = secondaryTable.get(j);
                boolean check = true;
                ArrayList<Long> buf4 = addLists(buf2, buf3);
                for (int k = 0; k < buf4.size(); k++) {
                    if (buf4.get(k) % 2 != 0) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    for (int m = 0; m < listOfEndNumber.size(); m++) {
                        System.out.print("----");
                    }
                    System.out.println();
                    for (int z = 0; z < buf4.size(); z++) {
                        System.out.printf("%-3s ", buf4.get(z));
                    }
                    System.out.println(" index1 = " + i + " index2 = " + j);
                    long t = (list3.get(i + 1) * list3.get(j + 1)) % this.D;

                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("t = " + t);
                    System.out.print("Enter S: ");
                    AlgorithmEvk algor = new AlgorithmEvk((int) D, (int) t);
                    int algorResult = algor.start((int) D, (int) (t - Integer.parseInt(reader.readLine())));
                    System.out.println("(" + D + ", " + t + ") = " + algorResult);
                    System.out.println(D + "/" + algorResult + " = " + (D / algorResult));
                    System.out.println(D + " = " + (D / algorResult) + " * " + (D / (D / algorResult)));


                    goNext = false;
                    break;
                }
            }
            if (!goNext) {
                break;
            }
        }

    }

    private ArrayList<Long> addLists(ArrayList<Long> one, ArrayList<Long> two) {
        ArrayList<Long> buf = new ArrayList<>();
        for (int i = 0; i < one.size(); i++) {
            buf.add(one.get(i) + two.get(i));
        }
        return buf;
    }


    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader((System.in)));
        System.out.print("Enter the number: ");
        setD(Integer.parseInt(reader.readLine()));
        System.out.println();
        System.out.print("Ho many: ");
        int howMany = Integer.parseInt(reader.readLine());
        begining();
        bufFirstIndex();
        for (int i = 0; i < howMany - 1; i++) {
            count();
        }
        showMainTable();
        System.out.println();
        System.out.println();
        checkToDoNext();
    }

    public static void main(String args[]) throws IOException {
        Morison mor = new Morison();
        mor.start();
        int buf = 54;
        for(int i = 2;i<=36;i++){
            buf*=54;
        }
        System.out.println(buf);
    }
}

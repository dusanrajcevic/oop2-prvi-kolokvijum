package com.company;

import result.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    // OVDE UNETI KOD
    public static void inputOddNumbers(ArrayList<Integer> lista) {
        Scanner citac = new Scanner(System.in);
        System.out.println("Unesi neparne brojeve (stop/prekid za kraj)");
        String broj = citac.nextLine();
        while (!broj.toLowerCase().equals("stop") && !broj.toLowerCase().equals("prekid")) {
            int ceoBroj = Integer.parseInt(broj);
            if (ceoBroj % 2 == 0)
                System.out.println("Paran broj nece biti dodat u listu");
            else
                lista.add(ceoBroj);
            broj = citac.nextLine();
        }
    }

    public static void fibonacciSequence(ArrayList<Integer> lista, int broj) {
        if (broj >= 1)
            lista.add(0);
        if (broj > 1)
            lista.add(1);
        for (int i = 2; i < broj; i++) {
            int sledeci = lista.get(i-1) + lista.get(i-2);
            lista.add(sledeci);
        }
    }

    public static int lastOdd(ArrayList<Integer> lista) {
        int neparan = -1;
        for (int broj: lista)
            if (broj % 2 != 0)
                neparan = broj;
        return neparan;
    }

    public static int maxMod5(ArrayList<Integer> lista) {
        int max = -1;
        for (int broj: lista)
            if (broj % 5 == 0 && broj > max)
                max = broj;
        return max;
    }

    public static int sumEven(ArrayList<Integer> lista) {
        int suma = 0;
        for(int broj: lista)
            if (broj % 2 == 0)
                suma += broj;
        return suma;
    }

    public static ArrayList<Integer> listModN(ArrayList<Integer> lista, int n) {
        ArrayList<Integer> novaLista = new ArrayList<>();
        for(int broj: lista)
            if (broj % n == 0)
                novaLista.add(broj);
        Collections.sort(novaLista);
        Collections.reverse(novaLista);
        return novaLista;
    }

    public static void printList(ArrayList<Integer> lista, int n) {
        int i;
        for (i = 0; i < lista.size() - 1; i++) {
            System.out.print(lista.get(i) + ", ");
            if ((i+1) % n == 0)
                System.out.println();
        }
        System.out.println(lista.get(i) + "");
    }
    // DOVDE UNOSITI KOD

    public static void main(String[] args) {
      result.Test rezultat = new Test("Ime i prezime");
      System.out.println(rezultat);
    }
}

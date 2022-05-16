package result;

import com.company.Main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Test {
    protected interface ITestService {
        boolean check();
        boolean test();
    }
    private static class TestParentService {
        protected String internalMessageHeader;
        protected String internalMethodName;
        protected String internalMessage;
        protected double internalBrojPoena = 0;
        protected List<Integer> lista;
        public TestParentService(String methodName) {
            this.internalMessageHeader = "--------------------------\nMETODA";
            this.internalMethodName = methodName;
            this.internalMessage = "";
            this.internalBrojPoena = 0;
            this.lista = new ArrayList<>();
        }
        public String wrongMethod() {
            return internalMessageHeader + " " +
                    internalMethodName + " " +
                    "NE POSTOJI ILI NIJE PRAVILNO DEFINISANA!\n" +
                    "Poena: 0\n";
        }
        public String passedMethod() {
            return internalMessageHeader + " " +
                    internalMethodName + ":\n" +
                    internalMessage;
        }
    }
    private class TestInputOddNumberService extends TestParentService implements ITestService {
        private List<Integer> novaLista;
        private final InputStream sysInBackup = System.in; // backup System.in to restore it later
        private ByteArrayInputStream in;
        private final PrintStream sysOutBackup = System.out; // backup System.out to restore it later
        private ByteArrayOutputStream outContent;
        public TestInputOddNumberService() {
            super("inputOddNumbers");
            test();
        }
        private void initialize(String brojevi)
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            in = new ByteArrayInputStream(brojevi.getBytes());
            System.setIn(in);

            lista = new ArrayList<>();
            novaLista = new ArrayList<>();
            Main.class.getMethod(internalMethodName, ArrayList.class).invoke(null, lista);
            System.setOut(sysOutBackup);
            System.setIn(sysInBackup);
        }
        @Override
        public boolean check() {
            if (novaLista.size() != lista.size())
                return false;
            for (int i = 0; i < novaLista.size(); i++)
                if (!lista.get(i).equals(novaLista.get(i)))
                    return false;
            return true;
        }
        @Override
        public boolean test() {
            try {
                try {
                    initialize("2\nstop");
                    if (lista.size() == 0) {
                        internalBrojPoena += 0.75;
                        internalMessage += "(+0.75 poena) Ispravno radi kada se unese broj 2 i ispod toga stop.\n";
                    }
                    else
                        internalMessage += "(-0.75 poena) Ne radi kada se unese broj 2 i ispod toga stop.\n";
                } catch (InvocationTargetException e) {
                    internalMessage += "(-0.75 poena) Ne radi kada se unese broj 2 i ispod toga stop.\n";
                }

                try {
                    initialize("3\nprekid");
                    Collections.addAll(novaLista, 3);
                    if (check()) {
                        internalBrojPoena += 0.75;
                        internalMessage += "(+0.75 poena) Ispravno radi kada se unese broj 3 i ispod toga prekid.\n";
                    } else
                        internalMessage += "(-0.75 poena) Ne radi kada se unese broj 3 i ispod toga prekid.\n";
                } catch (InvocationTargetException e) {
                    internalMessage += "(-0.75 poena) Ne radi kada se unese broj 3 i ispod toga prekid.\n";
                }

                try {
                    initialize("2\n4\n6\n0\n-2\n20\n30\nSTOP");
                    Collections.addAll(novaLista, 2, 4, 6, 0, -2, 20, 30);
                    if (lista.size() == 0) {
                        internalMessage += "(+0.75 poena) Ispravno radi kada se unosi 2, 4, 6, 0, -2, 20, 30 i na kraju STOP.\n";
                        internalBrojPoena += 0.75;
                    }
                    else
                        internalMessage += "(-0.75 poena) Ne radi kada se unosi 2, 4, 6, 0, -2, 20, 30 i na kraju STOP.\n";
                } catch (InvocationTargetException e) {
                    internalMessage += "(-0.75 poena) Ne radi kada se unosi 2, 4, 6, 0, -2, 20, 30 i na kraju STOP.\n";
                }

                try {
                    initialize("3\n2\n5\n10\n7\n9\n1\n8\n23\nPREKID");
                    Collections.addAll(novaLista, 3, 5, 7, 9, 1, 23);
                    if (check()) {
                        internalMessage += "(+0.75 poena) Ispravno radi kada se unosi 3, 2, 5, 10, 7, 9, 1, 8, 23 i na kraju PREKID.\n";
                        internalBrojPoena += 0.75;
                    } else
                        internalMessage += "(-0.75 poena) Ne radi kada se unosi 3, 2, 5, 10, 7, 9, 1, 8, 23 i na kraju PREKID.\n";
                } catch (InvocationTargetException e) {
                    internalMessage += "(-0.75 poena) Ne radi kada se unosi 3, 2, 5, 10, 7, 9, 1, 8, 23 i na kraju PREKID.\n";
                }

                internalMessage += "Poena: " + internalBrojPoena + " od 3\n";
                brojPoena += internalBrojPoena;
                message += passedMethod();
            } catch (Exception e) {
                message += wrongMethod();
            } finally {
                System.setOut(sysOutBackup);
                System.setIn(sysInBackup);
            }
            return internalBrojPoena > 0;
        }
    }
    private class TestFibonacciSequenceService extends TestParentService implements ITestService {
        private List<Integer> novaLista;
        public TestFibonacciSequenceService() {
            super("fibonacciSequence");
            test();
        }
        private void initialize(int number)
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            lista = new ArrayList<>();
            novaLista = new ArrayList<>();
            Main.class.getMethod(internalMethodName, ArrayList.class, int.class).invoke(null, lista, number);
        }
        public boolean check() {
            if (lista.size() != novaLista.size())
                return false;
            for (int i = 0; i < novaLista.size(); i++)
                if (!lista.get(i).equals(novaLista.get(i)))
                    return false;
            return true;
        }
        public boolean test() {
            try {
                initialize(0);
                if (lista.size() == 0) {
                    internalMessage += "(+0.5 poena) Ispravno radi kada se prosledi 0 kao drugi argument.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne radi kada se prosledi 0 kao drugi argument.\n";

                initialize(1);
                if (lista.size() == 1 && lista.get(0) == 0) {
                    internalMessage += "(+0.5 poena) Ispravno radi kada se prosledi 1 kao drugi argument.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne radi kada se prosledi 1 kao drugi argument.\n";

                initialize(2);
                if (lista.size() == 2 && lista.get(0) == 0 && lista.get(1) == 1) {
                    internalMessage += "(+0.5 poena) Ispravno radi kada se prosledi 2 kao drugi argument.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "- Ne radi kada se prosledi 2 kao drugi argument.\n";

                // Ovo mora da radi da bi se dobio poen
                initialize(15);
                Collections.addAll(novaLista, 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377);
                if (check()) {
                    internalMessage += "(+1.5 poena) Ispravno radi kada se prosledi 15 kao drugi argument.\n";
                    internalBrojPoena += 1.5;
                } else {
                    internalBrojPoena = 0;
                    internalMessage = "(-3 poena) Ne radi kada se prosledi 15 kao drugi argument.\n";
                }
                internalMessage += "Poena: " + internalBrojPoena + " od 3\n";
                message += passedMethod();
                brojPoena += internalBrojPoena;
            }  catch (Exception e) {
                message += wrongMethod();
            }
            return internalBrojPoena > 0;
        }
    }
    private class TestLastOddService extends TestParentService implements ITestService {
        private List<Integer> novaLista;
        public TestLastOddService() {
            super("lastOdd");
            test();
        }
        private int initialize(int... nums)
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            lista = new ArrayList<>();
            for (int num: nums) lista.add(num);
            return (int)Main.class.getMethod(internalMethodName, ArrayList.class).invoke(null, lista);
        }
        public boolean check() {
            return true;
        }
        public boolean test() {
            try {
                int rezultat = initialize();

                if (rezultat == -1) {
                    internalMessage += "(+0.5 poena) Ispravno vraca -1 kada se prosledi prazna lista.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca -1 kada se prosledi prazna lista.\n";

                rezultat = initialize(2, 4, 6, 8, 10);
                if (rezultat == -1) {
                    internalMessage += "(+0.5 poena) Ispravno vraca -1 kada se prosledi lista brojeva [2, 4, 6, 8, 10].\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca -1 kada se prosledi lista brojeva [2, 4, 6, 8, 10].\n";

                rezultat = initialize(3);
                if (rezultat == 3) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 3 kada se prosledi lista koja sadrzi samo 3.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca 3 kada se prosledi lista koja sadrzi samo 3.\n";

                rezultat = initialize(2, 4, 6, 1, 8, 10);
                if (rezultat == 1) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 1 kada se prosledi lista koja sadrzi [2, 4, 6, 1, 8, 10].\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca 1 kada se prosledi lista koja sadrzi [2, 4, 6, 1, 8, 10].\n";

                rezultat = initialize(5, 4, 6, 1, 3, 10);
                if (rezultat == 3) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 3 kada se prosledi lista koja sadrzi [5, 4, 6, 1, 3, 10].\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca 3 kada se prosledi lista koja sadrzi [5, 4, 6, 1, 3, 10].\n";

                // Ovo mora da radi da bi se dobio poen
                rezultat = initialize(1, 7, 9, 3, 5, 9);
                if (rezultat == 9) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 9 kada se prosledi lista koja sadrzi [1, 7, 9, 3, 5, 9].\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalBrojPoena = 0;
                    internalMessage = "(-3 poena) Ne vraca 9 kada se prosledi lista koja sadrzi [1, 7, 9, 3, 5, 9].\n";
                }
                internalMessage += "Poena: " + internalBrojPoena + " od 3\n";
                message += passedMethod();
                brojPoena += internalBrojPoena;
            }  catch (Exception e) {
                message += wrongMethod();
            }
            return internalBrojPoena > 0;
        }
    }
    private class TestMaxMod5Service extends TestParentService implements ITestService {
        private List<Integer> novaLista;
        public TestMaxMod5Service() {
            super("maxMod5");
            test();
        }
        private int initialize(int... nums)
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            lista = new ArrayList<>();
            for (int num: nums) lista.add(num);
            return (int)Main.class.getMethod(internalMethodName, ArrayList.class).invoke(null, lista);
        }
        public boolean check() {
            return true;
        }
        public boolean test() {
            try {
                int rezultat = initialize();
                if (rezultat == -1) {
                    internalMessage += "(+0.5 poena) Ispravno vraca -1 kada se prosledi prazna lista.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca -1 kada se prosledi prazna lista.\n";

                rezultat = initialize(2, 4, 6, 8, 12);
                if (rezultat == -1) {
                    internalMessage += "(+0.5 poena) Ispravno vraca -1 kada se prosledi lista koja sadrzi [2, 4, 6, 8, 12].\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca -1 kada se prosledi lista koja sadrzi [2, 4, 6, 8, 12].\n";

                rezultat = initialize(25);
                if (rezultat == 25) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 25 kada se prosledi lista koja sadrzi samo 25.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca 25 kada se prosledi lista koja sadrzi samo 25.\n";

                rezultat = initialize(2, 4, 6, 1, 8, 10);
                if (rezultat == 10) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 10 kada se prosledi lista koja sadrzi [2, 4, 6, 1, 8, 10].\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca 10 kada se prosledi lista koja sadrzi [2, 4, 6, 1, 8, 10].\n";

                rezultat = initialize(12, 4, 6, 1, 3, 15);
                if (rezultat == 15) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 15 kada se prosledi lista koja sadrzi [12, 4, 6, 1, 3, 15].\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca 15 kada se prosledi lista koja sadrzi [12, 4, 6, 1, 3, 15].\n";

                // Ovo mora da radi da bi se dobio poen.
                rezultat = initialize(5, 10, 15, 20, 25, 30);
                if (rezultat == 30) {
                    internalBrojPoena += 0.5;
                    internalMessage += "(+0.5 poena) Ispravno vraca 30 kada se prosledi lista koja sadrzi [5, 10, 15, 20, 25, 30].\n";
                } else {
                    internalBrojPoena = 0;
                    internalMessage = "(-3 poena) Ne vraca 30 kada se prosledi lista koja sadrzi [5, 10, 15, 20, 25, 30].\n";
                }
                internalMessage += "Poena: " + internalBrojPoena + " od 3\n";
                message += passedMethod();
                brojPoena += internalBrojPoena;
            }  catch (Exception e) {
                message += wrongMethod();
            }
            return internalBrojPoena > 0;
        }
    }
    private class TestSumEvenService extends TestParentService implements ITestService {
        private List<Integer> novaLista;
        public TestSumEvenService() {
            super("sumEven");
            test();
        }
        private int initialize(int... nums)
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            lista = new ArrayList<>();
            for (int num: nums) lista.add(num);
            return (int)Main.class.getMethod(internalMethodName, ArrayList.class).invoke(null, lista);
        }
        public boolean check() {
            return true;
        }
        public boolean test() {
            try {
                int rezultat = initialize();
                if (rezultat == 0) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 0 kada se prosledi prazna lista.\n";
                    internalBrojPoena += 0.5;
                } else
                    internalMessage += "(-0.5 poena) Ne vraca 0 kada se prosledi prazna lista.\n";

                rezultat = initialize(25);
                if (rezultat == 0) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 0 kada se prosledi lista koja sadrzi samo broj 25.\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.5 poena) Ne vraca 0 kada se prosledi lista koja sadrzi samo broj 25.\n";
                }

                rezultat = initialize(1, 3, 5, 7, 9, 11);
                if (rezultat == 0) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 0 kada se prosledi lista koja sadrzi [1, 3, 5, 7, 9, 11].\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.5 poena) Ne vraca 0 kada se prosledi lista koja sadrzi [1, 3, 5, 7, 9, 11].\n";
                }

                rezultat = initialize(3, 2, 4, 6, 1, 8, 12);
                if (rezultat == 32) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 32 kada se prosledi lista koja sadrzi [3, 2, 4, 6, 1, 8, 12].\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.5 poena) Ne vraca 32 kada se prosledi lista koja sadrzi [3, 2, 4, 6, 1, 8, 12].\n";
                }

                rezultat = initialize(2, 3, 4, 5, 2, 7);
                if (rezultat == 8) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 8 kada se prosledi lista koja sadrzi [2, 3, 4, 5, 2, 7].\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.5 poena) Ne vraca 8 kada se prosledi lista koja sadrzi [2, 3, 4, 5, 2, 7].\n";
                }

                // Ovo mora da radi da bi se dobio poen
                rezultat = initialize(2, 4, 6, 8, 10);
                if (rezultat == 30) {
                    internalMessage += "(+0.5 poena) Ispravno vraca 30 kada se prosledi lista koja sadrzi [2, 4, 6, 8, 10].\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalBrojPoena = 0;
                    internalMessage = "(-3 poena) Ne vraca 30 kada se prosledi lista koja sadrzi [2, 4, 6, 8, 10].\n";
                }
                internalMessage += "Poena: " + internalBrojPoena + " od 3\n";
                message += passedMethod();
                brojPoena += internalBrojPoena;
            }  catch (Exception e) {
                message += wrongMethod();
            }
            return internalBrojPoena > 0;
        }
    }
    private class TestListModNService extends TestParentService implements ITestService {
        private List<Integer> novaLista;
        private List<Integer> testLista;
        public TestListModNService() {
            super("listModN");
            test();
        }
        private List<Integer> initialize(int number, int... numbers)
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            lista = new ArrayList<>();
            for (int num: numbers) lista.add(num);
            novaLista = new ArrayList<>();
            testLista = new ArrayList<>();
            return (List<Integer>)Main.class.getMethod(internalMethodName, ArrayList.class, int.class).invoke(null, lista, number);
        }
        @Override
        public boolean check() {
            if (novaLista.size() != testLista.size())
                return false;
            for (int i = 0; i < testLista.size(); i++)
                if (!novaLista.get(i).equals(testLista.get(i)))
                    return false;
            return true;
        }
        public boolean checkPartial() {
            if (novaLista.size() != testLista.size())
                return false;
            for (int i = 0; i < testLista.size(); i++) {
                boolean pronasao = false;
                for (int j = 0; j < novaLista.size(); j++)
                    if (testLista.get(i).equals(novaLista.get(j))) {
                        pronasao = true;
                        break;
                    }
                if (!pronasao)
                    return false;
            }
            return true;
        }
        @Override
        public boolean test() {
            try {
                boolean tacno = false;

                novaLista = initialize(3);
                if (novaLista.size() == 0) {
                    internalMessage += "(+0.5 poena) Ispravno vraca praznu listu kada je prosledjena lista prazna.\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.5 poena) Ne vraca praznu listu kada je prosledjena lista prazna.\n";
                }

                novaLista = initialize(3, 25);
                if (novaLista.size() == 0) {
                    internalMessage += "(+0.5 poena) Ispravno vraca praznu listu kada je prvi argument lista koja sadrzi samo [25], a drugi argument broj 3.\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.5 poena) Ne vraca praznu listu kada je prvi argument lista koja sadrzi samo [25], a drugi argument broj 3.\n";
                }

                if (novaLista.size() == 0) {
                    internalMessage += "(+0.5 poena) Ispravno vraca praznu listu kada je prvi argument lista koja sadrzi brojeve [1, 2, 4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 19], a drugi argument broj 3.\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.5 poena) Ne vraca praznu listu kada je prvi argument lista koja sadrzi brojeve [1, 2, 4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 19], a drugi argument broj 3.\n";
                }

                novaLista = initialize(3, 1, 3, 5, 7, 9, 12, 15, 3, 9, 6, 14, 2);
                Collections.addAll(testLista, 15, 12, 9, 9, 6, 3, 3);
                if(check()) {
                    internalMessage += "(+0.75 poena) Ispravno vraca sortiranu listu brojeva [15, 12, 9, 9, 6, 3, 3] kada je prvi argument [1, 3, 5, 7, 9, 12, 15, 3, 9, 6, 14, 2], a drugi argument broj 3.\n";
                    internalBrojPoena += 0.75;
                } else if (checkPartial()){
                    internalMessage += "(+0.75-0.25p) Vraca nesortiranu listu brojeva [3, 9, 12, 3, 9, 6] kada je prvi argument [1, 3, 5, 7, 9, 12, 15, 3, 9, 6, 14, 2], a drugi argument broj 3.\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-0.75 poena) Ne vraca sortiranu listu brojeva kada je prvi argument [1, 3, 5, 7, 9, 12, 15, 3, 9, 6, 14, 2], a drugi argument broj 3.\n";
                }

                novaLista = initialize(4, 4, 8, 12, 16, 20, 24);
                Collections.addAll(testLista, 24, 20, 16, 12, 8, 4);
                if(check()) {
                    internalMessage += "(+0.75 poena) Ispravno vraca sortiranu listu brojeva [24, 20, 16, 12, 8, 4] kada je prvi argument [4, 8, 12, 16, 20, 24], a drugi argument broj 4.\n";
                    internalBrojPoena += 0.75;
                } else if (checkPartial()){
                    internalMessage += "(+0.75-0.25p) Vraca nesortiranu listu brojeva [4, 8, 12, 16, 20, 24] kada je prvi argument [4, 8, 12, 16, 20, 24], a drugi argument broj 4.\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage += "(-3 poena) Ne vraca sortiranu listu brojeva kada je prvi argument [4, 8, 12, 16, 20, 24], a drugi argument broj 4.\n";
                    internalBrojPoena = 0;
                }

                internalMessage += "Poena: " + internalBrojPoena + " od 3\n";
                message += passedMethod();
                brojPoena += internalBrojPoena;
            }  catch (Exception e) {
                message += wrongMethod();
            }
            return internalBrojPoena > 0;
        }
    }
    private class TestPrintListService extends TestParentService implements ITestService {
        private InputStream sysInBackup = System.in; // backup System.in to restore it later
        private ByteArrayInputStream in;
        private PrintStream sysOutBackup = System.out; // backup System.out to restore it later
        private ByteArrayOutputStream outContent;
        public TestPrintListService() {
            super("printList");
            outContent = new ByteArrayOutputStream();
            test();
        }
        private void initialize(int... numbers)
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            lista = new ArrayList<>();
            outContent = new ByteArrayOutputStream();
            for (int num: numbers) lista.add(num);
        }
        @Override
        public boolean check() {
            String[] test = outContent.toString().split(",");
            if (test.length != lista.size() || test[test.length-1].contains(",")) {
                return false;
            }
            for (int i = 0; i < test.length; i++) {
                if (i > 0 && i % 5 == 0 && !(test[i].contains("\r") || test[i].contains("\n"))) {
                    boolean rez = test[i].contains("\n");
                    return false;
                }
            }
            return true;
        }
        public boolean partialCheck() {
            String[] test = outContent.toString().split(",");
            return test.length == lista.size() && !test[test.length-1].contains(",");
        }
        public boolean partialCheckWithComa() {
            String[] test = outContent.toString().split(",");
            return test.length-1 == lista.size();
        }
        @Override
        public boolean test() {
            try {
                initialize(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
                System.setOut(new PrintStream(outContent));
                Main.class.getMethod(internalMethodName, ArrayList.class, int.class).invoke(null, lista, 5);
                if (check()) {
                    internalMessage += "(+1 poen) Ispravan prikaz brojeva (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21).\n";
                    internalBrojPoena += 1;
                } else if (partialCheck()) {
                    internalMessage += "(+1-0.5p) Svi brojevi su prikazani u jednom redu (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21).\n";
                    internalBrojPoena += 0.5;
                } else if (partialCheckWithComa()) {
                    internalMessage += "(+1-0.75p) Iza poslednjeg broja u listi (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21) stoji zarez.\n";
                    internalBrojPoena += 0.25;
                } else {
                    internalMessage += "(-1 poen) Brojevi liste nisu ispravno prikazani.\n";
                }
                System.setOut(sysOutBackup);

                initialize(1, 2, 3);
                System.setOut(new PrintStream(outContent));
                Main.class.getMethod(internalMethodName, ArrayList.class, int.class).invoke(null, lista, 5);
                if (check()) {
                    internalMessage += "(+1 poen) Ispravan prikaz brojeva (1, 2, 3).\n";
                    internalBrojPoena += 1;
                } else if (partialCheckWithComa()) {
                    internalMessage += "(+1-0.5p) Brojevi (1, 2, 3) su ispravno prikazani ali iza 3 stoji zarez.\n";
                    internalBrojPoena += 0.5;
                } else {
                    internalMessage = "(-2 poena) Brojevi liste nisu ispravno prikazani.\n";
                    internalBrojPoena = 0;
                }
                System.setOut(sysOutBackup);

                internalMessage += "Poena: " + internalBrojPoena + " od 2\n";
                brojPoena += internalBrojPoena;
                message += passedMethod();
            } catch (Exception e) {
                message += wrongMethod();
            } finally {
                System.setOut(sysOutBackup);
            }
            return internalBrojPoena > 0;
        }
    }
    private double brojPoena;
    private String message, imePrezime;
    public Test(String imePrezime) {
        this.imePrezime = imePrezime;
        try {
            message = "";
            brojPoena = 0;
            ITestService t = testInputOddNumbers();
            t = testFibonacciSequence();
            t = testLastOdd();
            t = testMaxMod5();
            t = testSumEven();
            t = testListModN();
            t = testPrintList();
        } catch (Exception e) {
            brojPoena = 0;
        }
    }
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return message +
                "==============================\n" +
                imePrezime + "\n" +
                "OSTVARILI STE UKUPNO " + df.format(brojPoena) + " POENA.\n" +
                "==============================";
    }

    public ITestService testInputOddNumbers() {
        return new TestInputOddNumberService();
    }

    public ITestService testFibonacciSequence() {
        return new TestFibonacciSequenceService();
    }

    public ITestService testLastOdd() {
        return new TestLastOddService();
    }

    public ITestService testMaxMod5() {
        return new TestMaxMod5Service();
    }

    public ITestService testSumEven() {
        return new TestSumEvenService();
    }

    public ITestService testListModN() {
        return new TestListModNService();
    }

    public ITestService testPrintList() {
        return new TestPrintListService();
    }
}

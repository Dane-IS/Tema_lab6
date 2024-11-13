package Problema_firma;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainApp {

    public static List<Angajat> citire() {
        try {
            File file=new File("src/main/resources/angajati.json");
            ObjectMapper mapper=new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            List<Angajat> angajati = mapper.readValue(file, new TypeReference<List<Angajat>>(){});
            return angajati;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<Angajat> angajati = citire();
        System.out.println(angajati);

        int opt;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\nMeniu");
            System.out.println("1.Afișarea listei de angajați");
            System.out.println("2.Afișarea angajaților care au salariul peste 2500 RON");
            System.out.println("3.Crearea unei liste cu angajații din luna aprilie, a anului trecut, care au funcție de conducere");
            System.out.println("4.Afișarea angajaților care nu au funcție de conducere în ordine descrescătoare a salariilor");
            System.out.println("5.Extragerea din lista de angajați a unei liste de String-uri care conține numele angajaților scrise cu majuscule");
            System.out.println("6.Afișarea salariilor mai mici de 3000 de RON folosind stream-uri, expresii lambda, referințe la metode şi metoda map(). ");
            System.out.println("7.Afișarea datelor primului angajat al firmei. Se va determina minimul din stream furnizând comparatorul adecvat printr-o expresie Lambda. ");
            System.out.println("8.Afișarea de statistici referitoare la salariul angajaților. Se va afișa salariul mediu salariul minim şi salariul maxim.");
            System.out.println("9.Afișarea unor mesaje care indică dacă printre angajați există cel puțin un Ion");
            System.out.println("10.Afișarea numărului de persoane care s-au angajat în vara anului precedent. Se va utiliza metoda count() din interfaţa Stream.");
            System.out.println("Alege o optiune:");
            opt = scanner.nextInt();

            switch (opt) {
                case 1:
                    angajati.forEach(System.out::println);
                    break;

                case 2:
                    Predicate<Angajat> salarPeste2500 = angajat -> angajat.getSalar() > 2500;

                    List<Angajat> angajatiSalarMare = angajati.stream().filter(salarPeste2500).toList();
                    angajatiSalarMare.forEach(System.out::println);
                    break;

                case 3:
                    int anulTrecut = LocalDate.now().getYear() - 1;
                    List<Angajat> angajatiConducere = angajati.stream()
                            .filter(angajat -> angajat.getData_angajare().getYear() == anulTrecut &&
                                    angajat.getData_angajare().getMonth() == Month.APRIL &&
                                    (angajat.getPost().toLowerCase().contains("sef") || angajat.getPost().toLowerCase().contains("director"))).collect(Collectors.toList());
                    System.out.println("Angajatii din aprilie anul trecut cu functie de conducere:");
                    angajatiConducere.forEach(System.out::println);
                    break;

                case 4:
                    List<Angajat> angajatiFaraConducere = angajati.stream().filter(angajat -> !angajat.getPost().toLowerCase().contains("sef") && !angajat.getPost().toLowerCase().contains("director")).sorted((a, b) -> Float.compare(b.getSalar(), a.getSalar())).collect(Collectors.toList());
                    System.out.println("Angajatii fara functie de conducere, ordonati descrescator dupa salariu:");
                    angajatiFaraConducere.forEach(System.out::println);
                    break;

                case 5:
                    List<String> numeMajuscule = angajati.stream().map(angajat -> angajat.getNume().toUpperCase()).collect(Collectors.toList());
                    System.out.println("Numele angajatilor cu majuscule:");
                    numeMajuscule.forEach(System.out::println);
                    break;

                case 6:
                    List<Float> salarii = angajati.stream().map(Angajat::getSalar).filter(salar -> salar < 3000).collect(Collectors.toList());
                    System.out.println("Salarii mai mici de 3000 RON:");
                    salarii.forEach(System.out::println);
                    break;

                case 7:
                    angajati.stream().min((a, b) -> a.getData_angajare().compareTo(b.getData_angajare())).ifPresentOrElse(angajat -> System.out.println("Data primului angajat: " + angajat.getData_angajare()), () -> System.out.println("Nu există angajați în firmă."));
                    break;

                case 8:
                    DoubleSummaryStatistics statistici = angajati.stream().collect(Collectors.summarizingDouble(Angajat::getSalar));
                    System.out.println("Salariul mediu: " + statistici.getAverage());
                    System.out.println("Salariul minim: " + statistici.getMin());
                    System.out.println("Salariul maxim: " + statistici.getMax());
                    break;

                case 9:
                    angajati.stream().map(Angajat::getNume).filter(nume -> nume.equalsIgnoreCase("Ion")).findAny().ifPresentOrElse(nume -> System.out.println("Firma are cel puțin un Ion angajat"), () -> System.out.println("Firma nu are nici un Ion angajat"));
                    break;

                case 10:
                    int anultrecut = LocalDate.now().getYear() - 1;
                    long numarAngajati = angajati.stream()
                            .filter(angajat -> angajat.getData_angajare().getYear() == anultrecut &&
                                    (angajat.getData_angajare().getMonth() == Month.JUNE || angajat.getData_angajare().getMonth() == Month.JULY || angajat.getData_angajare().getMonth() == Month.AUGUST)).count();
                    System.out.println("Numarul de angajati angajati in vara anului precedent: " + numarAngajati);
                    break;
            }
        }while (opt != 0) ;
    }
}

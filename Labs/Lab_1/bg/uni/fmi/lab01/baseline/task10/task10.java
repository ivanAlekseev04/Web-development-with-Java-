package bg.uni.fmi.lab01.baseline.task10;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class task10 {
    public static List<FlightLeg> extractLegs(Map<String, List<FlightLeg>> flights, String airportName) {
        var legs = flights.values()
                .stream()
                .flatMap(flight -> flight.stream())
                .filter(flight -> flight.getFromAirport().equals(airportName) || flight.getToAirport().equals(airportName))
                .toList();

        return legs;
    }

    public static void main(String[] args) {
        String a1 = "9H-VCA";
        String a2 = "9H-VGA";
        String a3 = "9H-VCJ";

        List<FlightLeg> l1 = List.of(new FlightLeg("AAAA", "BBBB", LocalDate.now()),
                new FlightLeg("AA1A", "AAAA", LocalDate.now()),
                new FlightLeg("GGGG", "BBBB", LocalDate.now()));

        Map<String, List<FlightLeg>> input = new HashMap<>(Map.ofEntries(
                Map.entry(a1, l1),
                Map.entry(a2, l1),
                Map.entry(a3, l1)
        ));

        extractLegs(input, "BBBB").stream().forEach(System.out::println);
    }
}

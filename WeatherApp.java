import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {

    private static JSONObject getWeatherData() throws IOException {
        String apiUrl = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
        URL url = new URL(apiUrl);
        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        
        scanner.close();
        return new JSONObject(response.toString());
    }

    private static double getTemperatureByDate(JSONObject data, String date) {
        JSONArray list = data.getJSONArray("list");
        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            if (item.getString("dt_txt").equals(date)) {
                JSONObject main = item.getJSONObject("main");
                return main.getDouble("temp");
            }
        }
        return -1.0;
    }

    private static double getWindSpeedByDate(JSONObject data, String date) {
        JSONArray list = data.getJSONArray("list");
        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            if (item.getString("dt_txt").equals(date)) {
                JSONObject wind = item.getJSONObject("wind");
                return wind.getDouble("speed");
            }
        }
        return -1.0;
    }

    private static double getPressureByDate(JSONObject data, String date) {
        JSONArray list = data.getJSONArray("list");
        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);
            if (item.getString("dt_txt").equals(date)) {
                JSONObject main = item.getJSONObject("main");
                return main.getDouble("pressure");
            }
        }
        return -1.0;
    }

    public static void main(String[] args) {
        try {
            JSONObject data = getWeatherData();
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("1. Get weather");
                System.out.println("2. Get Wind Speed");
                System.out.println("3. Get Pressure");
                System.out.println("0. Exit");
                
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter the date (yyyy-mm-dd hh:00:00): ");
                        String date = scanner.nextLine();
                        double temperature = getTemperatureByDate(data, date);
                        if (temperature != -1.0) {
                            System.out.printf("Temperature on %s: %.2f K%n", date, temperature);
                        } else {
                            System.out.println("Temperature data not found for the given date.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter the date (yyyy-mm-dd hh:00:00): ");
                        date = scanner.nextLine();
                        double windSpeed = getWindSpeedByDate(data, date);
                        if (windSpeed != -1.0) {
                            System.out.printf("Wind Speed on %s: %.2f m/s%n", date, windSpeed);
                        } else {
                            System.out.println("Wind speed data not found for the given date.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter the date (yyyy-mm-dd hh:00:00): ");
                        date = scanner.nextLine();
                        double pressure = getPressureByDate(data, date);
                        if (pressure != -1.0) {
                            System.out.printf("Pressure on %s: %.2f hPa%n", date, pressure);
                        } else {
                            System.out.println("Pressure data not found for the given date.");
                        }
                        break;
                    case 0:
                        System.out.println("Exiting the program.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while fetching weather data. Please try again later.");
        }
    }
}
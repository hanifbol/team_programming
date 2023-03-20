import java.util.*;

import static java.time.LocalDateTime.now;

public class RestoBungar {
    public static void main(String[] args) {
        // Initiate scanner
        Scanner input = new Scanner(System.in);

        // Greeting
        int currentHour = now().getHour();
        String period = "";
        if (currentHour >= 3 && currentHour < 9) {
            period = "pagi";
        } else if (currentHour < 15) {
            period = "siang";
        } else if (currentHour < 18) {
            period = "sore";
        } else {
            period = "malam";
        }
        System.out.printf("Selamat %s...\n\n", period);

        // Customer data input
        int customerCount = 0;
        boolean expectInput = true;
        while (expectInput) {
            System.out.print("Pesanan untuk berapa orang: ");
            try {
                customerCount = Integer.parseInt(String.valueOf(input.nextLine()));
                if (customerCount < 1) {
                    System.out.println("Harap masukkan angka yang benar!\n");
                } else {
                    expectInput = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Harap masukkan angka yang benar!\n");
            }
        }

        System.out.print("Pesanan atas nama: ");
        String customerName = input.nextLine();

        ///// Price List /////
        List<Map> menus = List.of(
                Map.of("name","Nasi Goreng Spesial","price",9999.99),
                Map.of("name","Ayam Bakar Spesial","price",12345.67),
                Map.of("name","Steak Sirloin Spesial","price",21108.40),
                Map.of("name","Kwetiaw Siram Spesial","price",13579.13),
                Map.of("name","Kambing Guling Spesial","price",98765.43)
        );
        System.out.println("\nMenu Spesial hari ini");
        System.out.println("=====================");
        int idx = 1;
        for (Map menu: menus) {
            System.out.printf("%d. %-30s@ Rp. %8.2f %n", idx, menu.get("name"), menu.get("price"));
            idx++;
        }

        ///// Order /////
        System.out.println("\nPesanan Anda (batas pesanan 0-10 porsi):");
        List<Integer> orders = new ArrayList();
        idx = 1;
        for (Map menu: menus) {
            expectInput = true;
            while (expectInput) {
                System.out.printf("%d. %-25s= ", idx, menu.get("name"));
                try {
                    int order = Integer.parseInt(String.valueOf(input.nextLine()));
                    if (order >= 0 && order <= 10) {
                        orders.add(order);
                        idx++;
                        expectInput = false;
                    } else {
                        System.out.println("Harap masukkan angka 0 s.d. 10!\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Harap masukkan angka yang benar!\n");
                }
            }
        }

        ///// Bill /////
        // Total Price
        System.out.println("\n\nSelamat menikmati makanan anda...\n");
        System.out.println("Pembelian:\n");
        double totalPrice = 0;
        for (int i = 1; i <= menus.size(); i++) {
            double menuPrice = (double)menus.get(i-1).get("price");
            double itemPrice = menuPrice * orders.get(i-1);
            totalPrice += itemPrice;
            String plus = (i < menus.size()) ? "" : " +";
            System.out.printf(
                    "%d. %-25s %2d porsi * Rp. %8.2f  =  Rp. %15.2f%s%n",
                    i,
                    menus.get(i-1).get("name"),
                    orders.get(i-1),
                    menuPrice,
                    itemPrice,
                    plus
            );
        }
        System.out.println("=".repeat(78));
        System.out.printf("%-54s=  Rp. %15.2f%n", "Total Pembelian", totalPrice);

        // Discount
        double discount = totalPrice / 10;
        System.out.printf("%-54s=  Rp. %15.2f -%n", "Disc. 10% (Masa Promosi)", discount);
        System.out.println("=".repeat(78));
        double finalPrice = totalPrice - discount;
        System.out.printf("%-54s=  Rp. %15.2f%n", "Total Pembelian setelah disc 10%", finalPrice);

        // Personal price
        double personalPrice = finalPrice / customerCount;
        String personalStatement = String.format("Pembelian per orang (untuk %d orang)", customerCount);
        System.out.printf("%-54s=  Rp. %15.2f%n%n", personalStatement, personalPrice);

        System.out.printf("%25sTerima kasih atas kunjungan Anda...%n", " ");
        System.out.printf("%25s...tekan ENTER untuk keluar...", " ");
        if (input.hasNextLine()) {
            System.exit(0);
        }
    }
}

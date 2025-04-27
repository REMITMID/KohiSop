import java.util.*;

public class KohiSop {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Item[] menuMinuman = {
            new Minuman("A1", "Caffe Latte", 46),
            new Minuman("A2", "Cappuccino", 46),
            new Minuman("E1", "Caffe Americano", 37),
            new Minuman("E2", "Caffe Mocha", 55),
            new Minuman("E3", "Caramel Macchiato", 59),
            new Minuman("E4", "asian Dolce Latte", 55),
            new Minuman("E5", "Double Shots Iced shaken Espresso", 50),
            new Minuman("B1", "Freshly Brewed Coffe", 23),
            new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50),
            new Minuman("B3", "Cold Brew", 44)
        };

        Item[] menuMakanan = {
            new Makanan("M1", "Petemania Pizza", 112),
            new Makanan("M2", "Mie Rebus Super Mario", 35),
            new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial", 72),
            new Makanan("M4", "Soto Kambing Iga Guling", 124),
            new Makanan("S1", "Singkong Bakar A La Carte", 37),
            new Makanan("S2", "Ubi Cilembu Bakar Arang", 58),
            new Makanan("S3", "Tempe Mendoan", 18),
            new Makanan("S4", "Tahu Bakso Ekstra Telur", 28)
        };

        SystemKohi.displayMenu("MENU MINUMAN", menuMinuman);
        SystemKohi.displayMenu("MENU MAKANAN", menuMakanan);

        Item[] pesanan = new Item[20];
        int pesananCount = 0;
        int[] jenisMinuman = {0}, jenisMakanan = {0};

        pesananCount = SystemKohi.inputPesanan(input, menuMinuman, menuMakanan, pesanan, pesananCount, jenisMinuman, jenisMakanan);

        Item[] unik = new Item[pesananCount];
        int[] kuantitas = new int[pesananCount];
        int uniqueCount = 0;

        for (int i = 0; i < pesananCount; i++) {
            Item item = pesanan[i];
            boolean found = false;

            for (int j = 0; j < uniqueCount; j++) {
                if (unik[j].getkodeItem().equals(item.getkodeItem())) {
                    kuantitas[j]++;
                    found = true;
                    break;
                }
            }

            if (!found) {
                unik[uniqueCount] = item;
                kuantitas[uniqueCount] = 1;
                uniqueCount++;
            }
        }

        double totalHarga = 0, totalPajak = 0;
        for (int i = 0; i < uniqueCount; i++) {
            double harga = unik[i].gethargaItem();
            int qty = kuantitas[i];
            totalHarga += harga * qty;
            totalPajak += unik[i].pajak() * qty;
        }

        double totalBayar = totalHarga + totalPajak;
        MetodePembayaran bayar = SystemKohi.pilihMetodePembayaran(input, totalBayar);
        double totalDiskon = totalBayar - bayar.diskonItem(totalBayar);
        double totalSetelahDiskon = bayar.diskonItem(totalBayar);

        String[] LMataUang = new String[1];
        IMataUang konversi = SystemKohi.pilihMataUang(input, LMataUang);

        double bayarKonversi = konversi.konversi(totalSetelahDiskon);
        double SblmPajak = konversi.konversi(totalHarga);

        SystemKohi.displayKuitansi(unik, kuantitas, uniqueCount, totalHarga, totalPajak, totalBayar, totalDiskon, totalSetelahDiskon, bayarKonversi, SblmPajak, LMataUang[0]);
    }
}
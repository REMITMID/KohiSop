import java.util.*;

public class KohiSopPaymentSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Item> menuMinuman = new ArrayList<Item>();
        menuMinuman.add(new Minuman("A1", "Caffe Latte", 46));
        menuMinuman.add(new Minuman("A2", "Cappuccino", 46));
        menuMinuman.add(new Minuman("E1", "Caffe Americano", 37));
        menuMinuman.add(new Minuman("E2", "Caffe Mocha", 55));
        menuMinuman.add(new Minuman("E3", "Caramel Macchiato", 59));
        menuMinuman.add(new Minuman("E4", "asian Dolce Latte", 55));
        menuMinuman.add(new Minuman("E5", "Double Shots Iced shaken Espresso", 50));
        menuMinuman.add(new Minuman("B1", "Freshly Brewed Coffe", 23));
        menuMinuman.add(new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50));
        menuMinuman.add(new Minuman("B3", "Cold Brew", 44));

        ArrayList<Item> menuMakanan = new ArrayList<Item>();
        menuMakanan.add(new Makanan("M1", "Petemania Pizza", 112));
        menuMakanan.add(new Makanan("M2", "Mie Rebus Super Mario", 35));
        menuMakanan.add(new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial", 72));
        menuMakanan.add(new Makanan("M4", "Soto Kambing Iga Guling", 124));
        menuMakanan.add(new Makanan("S1", "Singkong Bakar A La Carte", 37));
        menuMakanan.add(new Makanan("S2", "Ubi Cilembu Bakar Arang", 58));
        menuMakanan.add(new Makanan("S3", "Tempe Mendoan", 18));
        menuMakanan.add(new Makanan("S4", "Tahu Bakso Ekstra Telur", 28));
        
        S.clear();
        displayMenu("MENU MINUMAN", menuMinuman);
        displayMenu("MENU MAKANAN", menuMakanan);

        ArrayList<Item> pesanan = new ArrayList<>();

        int jenisMinuman = 0, jenisMakanan = 0;

        while (true) {
            System.out.println("Silahkan masukkan kode pesanan (ketik CC untuk membatalkan pesanan) : ");
            String kode = input.nextLine().toUpperCase();
            if(kode.equals("CC")) System.exit(0);
            if (kode.equals("N")) break;

            Item item = findItem(menuMinuman, kode);
            boolean iniMinuman = true;
            if (item == null) {
                item = findItem(menuMakanan, kode);
                iniMinuman = false;
            }
            if (item == null) {
                System.out.println("Kode tidak valid!!! Coba lagi");
                continue;
            }
            if (iniMinuman && jenisMinuman >=5) {
                System.out.println("Maksimal 5 jenis minuman");
                continue;
            }
            else if(!iniMinuman && jenisMakanan >=5) {
                System.out.println("Maksimal 5 jenis makanan");
                continue;
            }

            System.out.println("Masukkan jumlah pesanan (maksimal 3 minuman, 2 makanan) ketik 'S' untuk mengganti pesanan : ");
            while (true) {
                String jumlahInput = input.nextLine().toUpperCase();
                if (jumlahInput.equals("0") || jumlahInput.equals("S")) break;
                if (jumlahInput.equals("CC")) System.exit(0);

                try {
                    int jumlah = jumlahInput.isEmpty() ? 1 : Integer.parseInt(jumlahInput);
                    if ((menuMinuman.contains(item) && jumlah > 3) || (menuMakanan.contains(item) && jumlah > 2)) {
                        System.out.println("Jumlah melebihi batas!!! Masukkan ulang.");
                    }
                    else if (jumlah < 0) {
                        System.out.println("Error, Jumlah tidak valid!!! Masukkan ulang.");
                    }
                    else {
                        for (int i=0; i<jumlah; i++) 
                            pesanan.add(item);
                            if (iniMinuman) jenisMinuman++;
                            else jenisMakanan++;
                            displayPesananSementara(pesanan);
                            break;
                    }
                    } catch (NumberFormatException e) {
                        System.out.println("Error, Input tidak valid!!! Masukkan angka");
                    }
                }
                System.out.println("ketik N untuk ke tahap pembayaran");
            }

            ArrayList<Item> unik = new ArrayList<>();
            ArrayList<Integer> kuantitas = new ArrayList<>();

            for (Item i : pesanan) {
                if (!unik.contains(i)) {
                    unik.add(i);
                    int hitung = 0;
                    for (Item j : pesanan) {
                        if (i.getkodeItem().equals(j.getkodeItem())) hitung++;
                    }
                    kuantitas.add(hitung);
                }
            }
            System.out.println("\n========== KUITANSI PEMBAYARAN ==========");
            double totalHarga = 0, totalPajak = 0;

            System.out.println("Kategori: Makanan");
            for (int i = 0; i < unik.size(); i++) {
                Item item = unik.get(i);
                if (item instanceof Makanan) {
                    int qty = kuantitas.get(i);
                    double subTotal = item.gethargaItem() * qty;
                    double pajak = item.pajak()*qty;
                    totalHarga += subTotal;
                    totalPajak += pajak;
                    System.out.printf("%s | %-40s | %,3d x %d = Rp %,3f | Pajak: Rp %,3.0f\n", item.getkodeItem(), item.getnamaItem(), item.gethargaItem(), qty, subTotal, pajak);
                }
            }

            System.out.println("Kategori: Minuman");
            for (int i = 0; i < unik.size(); i++) {
                Item item = unik.get(i);
                if (item instanceof Minuman) {
                    int qty = kuantitas.get(i);
                    double subTotal = item.gethargaItem() * qty;
                    double pajak = item.pajak() * qty;
                    totalHarga += subTotal;
                    totalPajak += pajak;
                    System.out.printf("%s | %-40s | %,3d x %d = Rp %,3f | Pajak: Rp %,3.0f\n", item.getkodeItem(), item.getnamaItem(), item.gethargaItem(), qty, subTotal, pajak);
                }
            }

            double totalBayar = totalHarga + totalPajak;
            System.out.printf("\nTotal sebelum pajak : Rp %,3.0f\n", totalHarga);
            System.out.printf("Total pajak: Rp %,3.0f\n", totalPajak);
            System.out.printf("Total sebelum diskon: Rp %,3.0f\n", totalBayar);

            MetodePembayaran bayar = null;
            
            while (true) {
                System.out.println("\nPilih metode pembayaran (Masukkan angka): \n1.Tunai \n2.QRIS \n3.eMoney");
                int metode = input.nextInt();
                switch(metode) {
                case 1 :
                System.out.println("Pembayaran menggunakan Tunai");
                bayar = new MetodePembayaran(0) {
                    public double diskonItem(double totalBayar) {
                        return totalBayar;
                    }
                    public boolean saldoCukup(double totalBayar) {
                        return true;
                    }
                };
                break;
                case 2 :
                System.out.println("Pembayaran menggunakan Qris");
                System.out.println("masukkan saldo anda");
                bayar = new Qris(input.nextDouble());
                break;
                case 3 :
                System.out.println("Pembayaran menggunakan eMoney");
                System.out.println("masukkan saldo anda");
                bayar = new Emoney(input.nextDouble());
                break;
                default :
                System.out.println("Pilihan tidak valid. Coba lagi.");
                continue;
            }

            if (!bayar.saldoCukup(totalBayar)) {
                System.out.println("Saldo anda tidak cukup!");
                System.out.println("\n1. Menggunakan metode Tunai \n2. Menggunakan metode lain atau top up");
                metode = input.nextInt();
                if (metode == 1) {
                    bayar = new MetodePembayaran(0) {
                        public double diskonItem(double totalBayar) {
                            return totalBayar;
                        }
                        public boolean saldoCukup(double totalBayar) {
                            return true;
                        }
                    };
                    break;
                }
                else continue;
            }
            else break;
        }
        
        double totalDiskon = totalBayar - bayar.diskonItem(totalBayar);
        double totalSetelahDiskon = bayar.diskonItem(totalBayar);
        System.out.printf("Diskon: Rp %,3.0f\n", totalDiskon);
        System.out.printf("Total setelah diskon: Rp %3.0f\n", totalSetelahDiskon);

        System.out.println("\nPilih mata uang: \n1. IDR\n2. USD\n3. JPY\n4. MYR\n5. EUR");
        int mataUang = input.nextInt();
        IMataUang konversi;

        switch (mataUang) {
            case 2: konversi = new USD(); break;
            case 3: konversi = new JPY(); break;
            case 4: konversi = new MYR(); break;
            case 5: konversi = new EUR(); break;
            default : konversi = saldo -> saldo;
        }

        double bayarKonversi = konversi.konversi(totalSetelahDiskon);
        System.out.printf("Total tagihan dalam mata uang yang dpilih: %.2f\n", bayarKonversi);

        System.out.println("Terima kasih telah berkunjung!");
        input.close();
    }



    public static Item findItem(List<? extends Item> menu, String kode) {
        for (Item item : menu) {
            if (item.getkodeItem().equals(kode))
            return item;
        }
        return null;
    }

    public static void displayPesananSementara(List<Item> pesanan) {
        if (pesanan.isEmpty()) {
            System.out.println("Belum ada pesanan.");
            return;
        }

        System.out.println("\nDaftar Pesanan Sementara :");
        System.out.println("============================================================");
        System.out.printf("%-5s | %-40s | %-8s%n", "Kode", "Nama Item", "Kuantitas");
        System.out.println("------------------------------------------------------------");

        ArrayList<String> PesanS = new ArrayList<String>();

        for (Item item : pesanan) {
            String kode = item.getkodeItem();

            if (!PesanS.contains(kode)) {
                int hitung = 0;
                for (Item i: pesanan) {
                    if (i.getkodeItem().equals(kode)) {
                        hitung++;
                    }
                }
                System.out.printf("%-5s | %-40s | %-8s%n", kode, item.getnamaItem(), hitung);
                PesanS.add(kode);
            }
        }
        System.out.println("============================================================");
    }

    public static void displayMenu(String title, List<? extends Item> menu) {
        System.out.println("============================================================");
        System.out.printf("%36s%n", title);
        System.out.println("============================================================");
        System.out.printf("%-5s | %-40s | %-8s%n", "Kode", "Nama Item", "Harga");
        System.out.println("------------------------------------------------------------");

        for (Item item : menu) {
            System.out.println(item);
        }
        System.out.println("============================================================\n");
    }
}
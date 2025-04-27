import java.util.Scanner;

public class SystemKohi {
    public static void displayMenu(String title, Item[] menu) {
        System.out.println("============================================================");
        System.out.printf("%36s%n", title);
        System.out.println("============================================================");
        System.out.printf("%-5s | %-40s | %-8s%n", "Kode", "Nama Item", "Harga");
        System.out.println("------------------------------------------------------------");
        for (Item item : menu) System.out.println(item);
        System.out.println("============================================================\n");
    }

    public static Item findItem(Item[] menu, String kode) {
        for (Item item : menu) {
            if (item.getkodeItem().equalsIgnoreCase(kode)) return item;
        }
        return null;
    }

    public static int inputPesanan(Scanner input, Item[] menuMinuman, Item[] menuMakanan, Item[] pesanan,
    int pesananCount, int[] jenisMinuman, int[] jenisMakanan) {
    while (true) {
        System.out.println("Masukkan kode pesanan (ketik CC untuk batal, N untuk lanjut): ");
        String kode = input.nextLine().toUpperCase();

        if (kode.equals("CC")) System.exit(0);
        if (kode.equals("N")) break;

        Item item = SystemKohi.findItem(menuMinuman, kode);
        boolean iniMinuman = true;
        try {
            if (item == null) {
                item = SystemKohi.findItem(menuMakanan, kode);
                iniMinuman = false;
            }

            if (item == null) {
                throw new IllegalArgumentException("Kode tidak valid.");
            }

            if (iniMinuman && jenisMinuman[0] >= 5) {
                throw new IllegalStateException("Maksimal 5 jenis minuman.");
            }

            if (!iniMinuman && jenisMakanan[0] >= 5) {
                throw new IllegalStateException("Maksimal 5 jenis makanan.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            continue;
        }

        System.out.println("Masukkan jumlah pesanan (maks 3 minuman, 2 makanan) atau 'S' untuk skip:");
        while (true) {
            String jumlahInput = input.nextLine().toUpperCase();
            if (jumlahInput.equals("S") || jumlahInput.equals("0")) break;
            if (jumlahInput.equals("CC")) System.exit(0);

            try {
                int jumlah = Integer.parseInt(jumlahInput);
                if (jumlah < 0 || (iniMinuman && jumlah > 3) || (!iniMinuman && jumlah > 2)) {
                    System.out.println("Jumlah tidak valid. Ulangi.");
                } else {
                    for (int i = 0; i < jumlah; i++) {
                        if (pesananCount < pesanan.length) pesanan[pesananCount++] = item;
                    }
                    if (iniMinuman) jenisMinuman[0]++;
                    else jenisMakanan[0]++;
                    SystemKohi.displayPesananSementara(pesanan, pesananCount);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
        System.out.println("Ketik N untuk lanjut ke pembayaran.");
    }
    return pesananCount;
    }

    public static void displayPesananSementara(Item[] pesanan, int pesananCount) {
        if (pesananCount == 0) {
            System.out.println("Belum ada pesanan.");
            return;
        }
        System.out.println("\nDaftar Pesanan Sementara:");
        System.out.println("============================================================");
        System.out.printf("%-5s | %-40s | %-8s%n", "Kode", "Nama Item", "Kuantitas");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < pesananCount; i++) {
            Item item = pesanan[i];
            boolean sudahDitampilkan = false;

            for (int j = 0; j < i; j++) {
                if (pesanan[j].getkodeItem().equals(item.getkodeItem())) {
                    sudahDitampilkan = true;
                    break;
                }
            }

            if (!sudahDitampilkan) {
                int jumlah = 0;
                for (int j = 0; j < pesananCount; j++) {
                    if (pesanan[j].getkodeItem().equals(item.getkodeItem())) jumlah++;
                }
                System.out.printf("%-5s | %-40s | %-8d%n", item.getkodeItem(), item.getnamaItem(), jumlah);
            }
        }
        System.out.println("============================================================");
    }
    
        public static MetodePembayaran pilihMetodePembayaran(Scanner input, double totalBayar) {
            MetodePembayaran bayar = null;
            while (true) {
                System.out.println("\n=========================================");
                System.out.println("         PILIH METODE PEMBAYARAN         ");
                System.out.println("=========================================");
                System.out.println("1. Tunai\n2. QRIS   (diskon 5%)\n3. eMoney (diskon 7%, biaya admin 20 IDR)");
                System.out.print("Masukkan pilihan (1-3): ");
    
                int metode = input.nextInt();
                switch (metode) {
                    case 1:
                        bayar = new MetodePembayaran(0) {
                            public double diskonItem(double total) { return total; }
                            public boolean saldoCukup(double total) { return true; }
                        };
                        break;
                    case 2:
                        System.out.print("Masukkan saldo QRIS: ");
                        bayar = new Qris(input.nextDouble());
                        break;
                    case 3:
                        System.out.print("Masukkan saldo eMoney: ");
                        bayar = new Emoney(input.nextDouble());
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
                        continue;
                }
    
                if (!bayar.saldoCukup(totalBayar)) {
                    System.out.println("\nSaldo tidak cukup. Silakan pilih metode lain.");
                } else break;
            }
            return bayar;
        }
    
        public static IMataUang pilihMataUang(Scanner input, String[] label) {
            System.out.println("\n==========================================");
            System.out.println("         PILIH MATA UANG PEMBAYARAN     ");
            System.out.println("==========================================");
            System.out.println("1. IDR\n2. USD\n3. JPY\n4. MYR\n5. EUR");
            System.out.print("Masukkan kode mata uang (1-5): ");
            int pilihan = input.nextInt();
    
            switch (pilihan) {
                case 2: 
                label[0] = "USD"; 
                return new USD();
                case 3: 
                label[0] = "JPY"; 
                return new JPY();
                case 4: 
                label[0] = "MYR"; 
                return new MYR();
                case 5: 
                label[0] = "EUR"; 
                return new EUR();
                default: 
                label[0] = "IDR"; 
                return saldo -> saldo;
            }
        }

    public static void displayKuitansi(Item[] unik, int[] kuantitas, int uniqueCount, double totalHarga, double totalPajak,
    double totalBayar, double totalDiskon, double totalSetelahDiskon, double bayarKonversi, double SblmPajak, String LMataUang) {
        System.out.println("\n==============================================================================================");
        System.out.println("                                     KUITANSI PEMBAYARAN         ");
        System.out.println("==============================================================================================\n");

        boolean adaMakanan = false;
        boolean adaMinuman = false;

        for (int i = 0; i < uniqueCount; i++) {
            if (unik[i] instanceof Makanan) adaMakanan = true;
            else if (unik[i] instanceof Minuman) adaMinuman = true;
        }

        if (adaMakanan) {
            System.out.println(">>> KATEGORI: MAKANAN");
            System.out.println("Kode   | Nama Item                           | Harga     x Qty = Subtotal      | Pajak");
            System.out.println("----------------------------------------------------------------------------------------------");
            for (int i = 0; i < uniqueCount; i++) {
                Item item = unik[i];
                if (item instanceof Makanan) {
                    int qty = kuantitas[i];
                    double harga = item.gethargaItem();
                    double subTotal = harga * qty;
                    double pajak = item.pajak() * qty;
                    System.out.printf("%-6s | %-35s | Rp %,7.0f x %2d = Rp %,10.0f | Rp %,7.0f\n",
                            item.getkodeItem(), item.getnamaItem(), harga, qty, subTotal, pajak);
                }
            }
            System.out.println();
        }

        if (adaMinuman) {
            System.out.println(">>> KATEGORI: MINUMAN");
            System.out.println("Kode   | Nama Item                           | Harga     x Qty = Subtotal      | Pajak");
            System.out.println("----------------------------------------------------------------------------------------------");
            for (int i = 0; i < uniqueCount; i++) {
                Item item = unik[i];
                if (item instanceof Minuman) {
                    int qty = kuantitas[i];
                    double harga = item.gethargaItem();
                    double subTotal = harga * qty;
                    double pajak = item.pajak() * qty;
                    System.out.printf("%-6s | %-35s | Rp %,7.0f x %2d = Rp %,10.0f | Rp %,7.0f\n",
                            item.getkodeItem(), item.getnamaItem(), harga, qty, subTotal, pajak);
                }
            }
            System.out.println();
        }

        System.out.printf("Total Sebelum Pajak          : Rp %,15.0f\n", totalHarga);
        System.out.printf("Total Sebelum Pajak (%s)    : %s %,14.0f\n", LMataUang, LMataUang, SblmPajak);
        System.out.printf("Total Pajak                  : Rp %,15.0f\n", totalPajak);
        System.out.printf("Total sebelum diskon         : Rp %,15.0f\n", totalBayar);
        System.out.printf("Diskon                       : Rp %,15.0f\n", totalDiskon);
        System.out.printf("TOTAL DIBAYAR                : Rp %,15.0f\n", totalSetelahDiskon);
        System.out.printf("Total Tagihan (%s)          : %s %,14.0f\n", LMataUang, LMataUang, bayarKonversi);
        System.out.println("============================================================================================\n");
        System.out.println("                                   TERIMA KASIH TELAH BERKUNJUNG!        ");
        System.out.println("============================================================================================\n");
    }
}
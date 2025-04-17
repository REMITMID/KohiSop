public class Emoney extends MetodePembayaran {
    public Emoney (double saldo) {
        super(saldo);
    }

    public double diskonItem (double totalHarga) {
        return totalHarga * 0.93 - 20;
    }

    public boolean saldoCukup (double totalHarga) {
        return saldo >= diskonItem(totalHarga);
    }
}
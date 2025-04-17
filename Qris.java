public class Qris extends MetodePembayaran{
    public Qris (double saldo) {
        super (saldo);
    }

    @Override
    public double diskonItem (double totalHarga) {
    return totalHarga * 0.95;
    }
    
    @Override
    public boolean saldoCukup (double totalHarga) {
        return saldo >= diskonItem(totalHarga);
    }
}
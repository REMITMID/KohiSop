public abstract class MetodePembayaran {
    protected double saldo;

    public MetodePembayaran(double saldo) {
        this.saldo = saldo;
    }
    
    public abstract double diskonItem (double totalHarga);
    public abstract boolean saldoCukup (double totalHarga);

}
public class EUR implements IMataUang {
    @Override
    public double konversi (double amount) {
        return amount / 14;
    }
}
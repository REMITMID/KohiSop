public class MYR implements IMataUang {
    @Override
    public double konversi (double amount) {
        return amount / 4;
    }
}
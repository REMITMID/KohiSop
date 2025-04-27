public class JPY implements IMataUang {
    @Override
    public double konversi (double amount) {
        return amount * 10;
    }
}
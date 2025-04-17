public class USD implements IMataUang {
    @Override
    public double konversi (double amount) {
        return amount / 15;
    }
}
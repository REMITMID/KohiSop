class Makanan extends Item {
    Makanan (String kodeItem, String namaItem, int hargaItem){
        super(kodeItem, namaItem, hargaItem);
    }

    @Override
    public double pajak() {
        if (hargaItem > 50) {
            return hargaItem * 0.08;
        }
        else {
            return hargaItem * 0.11;
        }
    }
}
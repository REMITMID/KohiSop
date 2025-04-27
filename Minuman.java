class Minuman extends Item {
    Minuman (String kodeItem, String namaItem, int hargaItem){
        super(kodeItem, namaItem, hargaItem);
    }

    @Override
    public double pajak() {
        if (hargaItem < 50) {
            return 0;
        }
        else if (hargaItem <= 55) {
            return hargaItem * 0.08;
        }
        else {
            return hargaItem * 0.11;
        }
    }
}
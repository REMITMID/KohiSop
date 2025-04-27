public abstract class Item {
    protected String kodeItem;
    protected String namaItem;
    protected int hargaItem;

    Item (String kodeItem, String namaItem, int hargaItem) {
        this.kodeItem = kodeItem;
        this.namaItem = namaItem;
        this.hargaItem = hargaItem;
    }

    public String getkodeItem () {
        return kodeItem;
    }

    public String getnamaItem() {
        return namaItem;
    }

    public int gethargaItem() {
        return hargaItem;
    }

    public abstract double pajak();

    @Override
    public String toString () {
        return String.format("%-5s | %-40s | Rp %,3d", kodeItem, namaItem, hargaItem);
    }
}
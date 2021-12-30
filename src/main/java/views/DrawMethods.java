package views;

public enum DrawMethods {

    DESSUS("Vue du dessus"),FACE("Vue de face"),DROITE("Vue de droite"), TRANCHE("Vue en tranche");

    private String name;

    DrawMethods(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*private Comparator<Polygon> comparator;
    private Supplier<double[]> xGetter;
    private Supplier<double[]> yGetter;*/

    /*drawMethods(Comparator<Polygon> comparator,
    Supplier<double[]> xGetter, Supplier<double[]> yGetter) {
        this.comparator = comparator;
        this.xGetter = xGetter;
        this.yGetter = yGetter;
    }*/
}

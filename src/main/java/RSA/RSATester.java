package RSA;

public class RSATester {

    public static void main(String[] args) {

        RSA.encodeDecode("Hello World");
        System.out.println();
        RSA.encodeDecode("Green Park is a London Underground station on the north side of Green Park, with entrances on both sides of Piccadilly. It is in fare zone 1 and is a busy interchange between the Jubilee, Piccadilly and Victoria lines, used by over 39 million passengers in 2017.");
        System.out.println();
        RSA.encodeDecode("Brie Larson Oscar-díjas amerikai színésznő, énekesnő, filmrendező és aktivista. A kaliforniai Sacramentóban született 1989. október 1-jén. Otthon folytatta iskolai tanulmányait, majd színészetet kezdett tanulni az American Conservatory Theaterben, ahová minden idők legfiatalabb diákjaként vették fel.");
        System.out.println();

        RSA.printRandomKeys();
    }
}

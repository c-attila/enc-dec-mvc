import rsa.RSA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSATest {

    @Test
    public void emptyStringShouldStayEmpty() {
        assertEquals("", RSA.encodeDecode(""));
    }

    @Test
    public void testTexts() {
        assertEquals("Hello World", RSA.encodeDecode("Hello World"));
        System.out.println();
        assertEquals("Green Park is a London Underground station on the north side of Green Park, with entrances on both sides of Piccadilly. It is in fare zone 1 and is a busy interchange between the Jubilee, Piccadilly and Victoria lines, used by over 39 million passengers in 2017.",
                RSA.encodeDecode("Green Park is a London Underground station on the north side of Green Park, with entrances on both sides of Piccadilly. It is in fare zone 1 and is a busy interchange between the Jubilee, Piccadilly and Victoria lines, used by over 39 million passengers in 2017."));
        System.out.println();
        assertEquals("Brie Larson Oscar-díjas amerikai színésznő, énekesnő, filmrendező és aktivista. A kaliforniai Sacramentóban született 1989. október 1-jén. Otthon folytatta iskolai tanulmányait, majd színészetet kezdett tanulni az American Conservatory Theaterben, ahová minden idők legfiatalabb diákjaként vették fel.",
                RSA.encodeDecode("Brie Larson Oscar-díjas amerikai színésznő, énekesnő, filmrendező és aktivista. A kaliforniai Sacramentóban született 1989. október 1-jén. Otthon folytatta iskolai tanulmányait, majd színészetet kezdett tanulni az American Conservatory Theaterben, ahová minden idők legfiatalabb diákjaként vették fel."));
        System.out.println();
    }
}

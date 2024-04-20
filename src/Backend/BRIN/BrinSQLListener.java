package Backend.BRIN;

import Backend.BRIN.BrinIndex;
import antlr4.PostgreSQLParserBaseListener;

public class BrinSQLListener extends PostgreSQLParserBaseListener {
    private BrinIndex brinIndex;

    public BrinSQLListener(BrinIndex brinIndex) {
        this.brinIndex = brinIndex;
    }

}

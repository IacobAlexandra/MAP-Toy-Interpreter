package interpreter.model.exp;

//  (and, or – both operands must be booleans)
public enum LogicOp {
    AND("and"), OR("or");
    private final String symbol;

    LogicOp(String symbol) {this.symbol = symbol;}
}

package tech.Demo.Code.action;

import tech.Demo.Code.common.Constants;
import tech.Demo.Code.io.Reader;
import tech.Demo.Code.io.impl.UserInputReader;

import static tech.Demo.Code.io.impl.ConsoleWriter.write;

public class Bakery {
    private Reader reader = UserInputReader.getInstance();
    private OrderProc orderProc;
    private boolean open;

    public void open() {
        this.open = true;
        orderProc = new OrderProc(this);

        while (open) {
            write(Constants.ORDER_TEXT);
            write(orderProc.process(reader.readValue()));
            write(Constants.LINE);
        }
    }

    public void close() {
        this.open = false;
    }
}

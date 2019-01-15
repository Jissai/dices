package ant.dice;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class SymbolResolver {

    public Collection<Symbol> resolve(Collection<Symbol> symbols) {
        LinkedList<Symbol> modifiableList = new LinkedList<>(symbols);

        for (Iterator<Symbol> it = modifiableList.iterator(); it.hasNext(); it.next()) {
            System.out.println();
        }

        return modifiableList;
    }

}
